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
        Specification<T> spec = specificationBuilder.build(dto);

        // Determine primary sort field for cursor encoding (defaults to "createdAt")
        String sortField = "createdAt";
        if (dto != null && dto.getSort() != null && !dto.getSort().isEmpty()) {
            if (dto.getSort().get(0) != null && dto.getSort().get(0).getKey() != null) {
                sortField = dto.getSort().get(0).getKey();
            }
        }

        int limit = 20;
        if (dto != null) {
            if (dto.getFirst() != null && dto.getFirst() > 0) {
                limit = dto.getFirst();
            } else if (dto.getLast() != null && dto.getLast() > 0) {
                limit = dto.getLast();
            }
        }

        Pageable pageable = PageRequest.of(0, limit + 1);
        List<T> results = repository.findAll(spec, pageable).getContent();

        boolean hasMore = results.size() > limit;
        if (hasMore) {
            results = results.subList(0, limit);
        }

        String finalSortField = sortField;
        List<EdgeDTO<T>> edges = results.stream()
                                        .map(entity -> {
                                            Object sortFieldValue = extractFieldValue(entity, finalSortField);
                                            UUID entityId = (UUID) extractFieldValue(entity, "id");
                                            String cursor = cursorService.encode(finalSortField, sortFieldValue, entityId);
                                            return new EdgeDTO<>(entity, cursor);
                                        })
                                        .collect(Collectors.toList());

        String startCursor = edges.isEmpty() ? null : edges.get(0).getCursor();
        String endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();

        boolean hasNextPage = false;
        boolean hasPreviousPage = false;

        if (dto != null) {
            if (dto.getAfter() != null && !dto.getAfter().isBlank()) {
                hasPreviousPage = true;
                hasNextPage = hasMore;
            } else if (dto.getBefore() != null && !dto.getBefore().isBlank()) {
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

        Specification<T> countSpec = specificationBuilder.buildWithoutCursors(dto);
        int totalCount = (int) repository.count(countSpec);

        return ConnectionResponseDTO.<T>builder()
                                    .edges(edges)
                                    .pageInfo(pageInfo)
                                    .totalCount(totalCount)
                                    .build();
    }

    private Object extractFieldValue(Object entity, String fieldName) {
        try {
            var field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            try {
                var field = entity.getClass().getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(entity);
            } catch (Exception ex) {
                return null;
            }
        }
    }
}