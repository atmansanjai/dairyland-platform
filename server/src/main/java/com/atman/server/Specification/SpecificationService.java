package com.atman.server.Specification;

import com.atman.server.Specification.DTO.MapDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SpecificationService<T> {
    Specification<T> contains(String fieldName, String value);

    Specification<T> filter(List<MapDTO> filters);

    Specification<T> sortBy(List<MapDTO> sorts);

    Specification<T> cursorAfter(String cursor);

    Specification<T> cursorBefore(String cursor);
}