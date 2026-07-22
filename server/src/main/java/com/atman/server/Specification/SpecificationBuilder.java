package com.atman.server.Specification;

import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecificationBuilder<T> {
    private final SpecificationService<T> specificationService;

    public Specification<T> build(ConnectionRequestDTO dto) {

        Specification<T> spec = Specification.where((Specification<T>) null);

        if(dto == null) {
            return spec;
        }

        // 1. Search Logic
        if(dto.getSearch() != null && dto.getSearch()
                                         .getField() != null && dto.getSearch()
                                                                   .getValue() != null) {
            spec = spec.and(specificationService.contains(dto.getSearch()
                                                             .getField(), dto.getSearch()
                                                                             .getValue()));
        }

        // 2. Filter Logic
        if(dto.getFilter() != null && dto.getFilter()
                                         .getField() != null && dto.getFilter()
                                                                   .getValue() != null) {
            spec = spec.and(specificationService.filter(dto.getFilter()
                                                           .getField(), dto.getFilter()
                                                                           .getValue()));
        }

        // 3. Sort Logic (Safely parsing Direction & reassigning spec)
        if(dto.getSort() != null && dto.getSort()
                                       .getField() != null) {
            Sort.Direction direction = Sort.Direction.DESC; // Fallback default

            if(dto.getSort()
                  .getValue() != null) {
                try {
                    // Converts incoming String (e.g., "ASC" or "desc") into Spring's Sort.Direction
                    direction = Sort.Direction.valueOf(dto.getSort()
                                                          .getValue()
                                                          .toUpperCase());
                } catch(IllegalArgumentException e) {
                    // Falls back to DESC if an invalid string is passed
                    direction = Sort.Direction.DESC;
                }
            }
            spec = spec.and(specificationService.sortBy(dto.getSort()
                                                           .getField(), direction));
        }

        // 4. Cursor Pagination Logic (After / Before)
        if(dto.getAfter() != null && !dto.getAfter().isBlank()) {
            spec = spec.and(specificationService.cursorAfter(dto.getAfter()));
        } else if(dto.getBefore() != null && !dto.getBefore().isBlank()) {
            spec = spec.and(specificationService.cursorBefore(dto.getBefore()));
        }

        return spec;
    }

    /**
     * Builds specification with search and filters only, omitting cursors and sorting.
     * Used for accurately calculating the total count of matching records.
     */
    public Specification<T> buildWithoutCursors(ConnectionRequestDTO dto) {
        Specification<T> spec = Specification.where((Specification<T>) null);

        if(dto == null) {
            return spec;
        }

        if(dto.getSearch() != null && dto.getSearch().getField() != null && dto.getSearch().getValue() != null) {
            spec = spec.and(specificationService.contains(dto.getSearch().getField(), dto.getSearch().getValue()));
        }

        if(dto.getFilter() != null && dto.getFilter().getField() != null && dto.getFilter().getValue() != null) {
            spec = spec.and(specificationService.filter(dto.getFilter().getField(), dto.getFilter().getValue()));
        }

        return spec;
    }
}