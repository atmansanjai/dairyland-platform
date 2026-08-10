package com.atman.server.Specification;

import com.atman.server.Specification.DTO.ConnectionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecificationBuilder<T> {
    private final SpecificationService<T> specificationService;

    public Specification<T> spec() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public Specification<T> build(ConnectionRequestDTO dto) {
        Specification<T> spec = spec();

        if (dto == null) {
            return spec.and(specificationService.sortBy(null));
        }

        // 1. Search Logic
        if (dto.getSearch() != null && dto.getSearch().getKey() != null && dto.getSearch().getValue() != null) {
            spec = spec.and(specificationService.contains(dto.getSearch().getKey(), dto.getSearch().getValue()));
        }

        // 2. Multiple Filter Logic
        if (dto.getFilter() != null && !dto.getFilter().isEmpty()) {
            spec = spec.and(specificationService.filter(dto.getFilter()));
        }

        // 3. Multiple Sort Logic
        spec = spec.and(specificationService.sortBy(dto.getSort()));

        // 4. Cursor Pagination Logic (After / Before)
        if (dto.getAfter() != null && !dto.getAfter().isBlank()) {
            spec = spec.and(specificationService.cursorAfter(dto.getAfter()));
        } else if (dto.getBefore() != null && !dto.getBefore().isBlank()) {
            spec = spec.and(specificationService.cursorBefore(dto.getBefore()));
        }

        return spec;
    }

    public Specification<T> buildWithoutCursors(ConnectionRequestDTO dto) {
        Specification<T> spec = spec();

        if (dto == null) {
            return spec;
        }

        if (dto.getSearch() != null && dto.getSearch().getKey() != null && dto.getSearch().getValue() != null) {
            spec = spec.and(specificationService.contains(dto.getSearch().getKey(), dto.getSearch().getValue()));
        }

        if (dto.getFilter() != null && !dto.getFilter().isEmpty()) {
            spec = spec.and(specificationService.filter(dto.getFilter()));
        }

        return spec;
    }
}