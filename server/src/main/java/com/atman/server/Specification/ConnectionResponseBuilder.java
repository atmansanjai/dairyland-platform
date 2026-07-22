package com.atman.server.Specification;

import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import com.atman.server.Specification.DTO.ConnectionResponseDTO;
import com.atman.server.Specification.DTO.EdgeDTO;
import com.atman.server.Specification.DTO.PageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConnectionResponseBuilder {

    private final CursorService cursorService;

    public <T> ConnectionResponseDTO<T> build(JpaSpecificationExecutor<T> repository, SpecificationBuilder<T> specificationBuilder, ConnectionRequestDTO dto) {
        // 1. Build the dynamic specification (search, filter, sort, and cursors)
        Specification<T> spec = specificationBuilder.build(dto);

        // 2. Determine sort field (defaults to "id" if not provided)
        String sortField = "id";
        if(dto != null && dto.getSort() != null && dto.getSort()
                                                      .getField() != null) {
            sortField = dto.getSort()
                           .getField();
        }

        // 3. Determine limit dynamically from Relay DTO ('first' or 'last', fallback to 20)
        int limit = 20;
        if(dto != null) {
            if(dto.getFirst() > 0) {
                limit = dto.getFirst();
            } else if(dto.getLast() > 0) {
                limit = dto.getLast();
            }
        }

        // Fetch limit + 1 items to accurately check for next/previous pages
        Pageable pageable = PageRequest.of(0, limit + 1);
        List<T> results = repository.findAll(spec, pageable)
                                    .getContent();

        boolean hasMore = results.size() > limit;
        if(hasMore) {
            results = results.subList(0, limit); // Trim back to requested limit
        }

        // 4. Map entities to EdgeDTOs with generated cursors
        String finalSortField = sortField;
        List<EdgeDTO<T>> edges = results.stream()
                                        .map(entity -> {
                                            Object sortFieldValue = extractFieldValue(entity, finalSortField);
                                            UUID entityId = (UUID) extractFieldValue(entity, "id");
                                            String cursor = cursorService.encode(finalSortField, sortFieldValue, entityId);
                                            return new EdgeDTO<>(entity, cursor);
                                        })
                                        .collect(Collectors.toList());

        // 5. Build PageInfoDTO flags
        String startCursor = edges.isEmpty() ? null : edges.get(0)
                                                           .getCursor();
        String endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1)
                                                         .getCursor();

        boolean hasNextPage = false;
        boolean hasPreviousPage = false;

        if(dto != null) {
            if(dto.getAfter() != null && !dto.getAfter()
                                             .isBlank()) {
                hasPreviousPage = true;
                hasNextPage = hasMore;
            } else if(dto.getBefore() != null && !dto.getBefore()
                                                     .isBlank()) {
                hasPreviousPage = hasMore;
                hasNextPage = true;
            } else {
                hasNextPage = hasMore;
            }
        }

        PageInfoDTO pageInfo = PageInfoDTO.builder()
                                          .startCursor(startCursor)
                                          .endCursor(endCursor)
                                          .hasPreviousPage(hasPreviousPage)
                                          .hasNextPage(hasNextPage)
                                          .build();

        // 6. Calculate total count using specification without cursor bounds
        Specification<T> countSpec = specificationBuilder.buildWithoutCursors(dto);
        int totalCount = (int) repository.count(countSpec);

        return ConnectionResponseDTO.<T>builder()
                                    .edges(edges)
                                    .pageInfo(pageInfo)
                                    .totalCount(totalCount)
                                    .build();
    }

    /**
     * Reflection helper to extract field values from any entity object safely.
     */
    private Object extractFieldValue(Object entity, String fieldName) {
        try {
            var field = entity.getClass()
                              .getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch(Exception e) {
            try {
                var field = entity.getClass()
                                  .getSuperclass()
                                  .getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(entity);
            } catch(Exception ex) {
                return null;
            }
        }
    }
}