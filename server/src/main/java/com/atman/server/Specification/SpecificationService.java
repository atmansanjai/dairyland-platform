package com.atman.server.Specification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationService<T> {
    Specification<T> contains(String fieldName, String value);

    Specification<T> filter(String fieldName, String value);

    Specification<T> sortBy(String field, Sort.Direction direction);

     Specification<T> cursorAfter(String cursor);

     Specification<T> cursorBefore(String cursor);
}
