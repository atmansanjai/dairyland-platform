package com.atman.server.Specification;

import com.atman.server.Specification.DTO.CursorPayload;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaSpecificationService<T> implements SpecificationService<T> {

    private final CursorService cursorService;
    private final EntityManager entityManager;

    @Override
    public Specification<T> contains(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        };
    }

    @Override
    public Specification<T> filter(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isEmpty()) {
                return cb.conjunction();
            }
            Class<?> fieldType = root.get(fieldName).getJavaType();
            if (Number.class.isAssignableFrom(fieldType)) {
                return cb.equal(root.get(fieldName), Double.parseDouble(value));
            }
            if (fieldType == Boolean.class) {
                return cb.equal(root.get(fieldName), Boolean.parseBoolean(value));
            }
            if (fieldType == UUID.class) {
                return cb.equal(root.get(fieldName), UUID.fromString(value));
            }
            return cb.equal(root.get(fieldName), value);
        };
    }

    @Override
    public Specification<T> sortBy(String field, Sort.Direction direction) {
        return (root, query, cb) -> {
            List<Order> orders = new ArrayList<>();
            if (field != null && !field.isEmpty() && direction != null) {
                if (direction == Sort.Direction.ASC) {
                    orders.add(cb.asc(root.get(field)));
                    orders.add(cb.asc(root.get("id"))); // Secondary sort matches primary direction
                } else {
                    orders.add(cb.desc(root.get(field)));
                    orders.add(cb.desc(root.get("id")));
                }
            } else {
                orders.add(cb.desc(root.get("id")));
            }
            query.orderBy(orders);
            return cb.conjunction();
        };
    }

    @Override
    public Specification<T> cursorAfter(String cursor) {
        return (root, query, cb) -> {
            CursorPayload payload = cursorService.decode(cursor);
            if (payload == null || payload.getValue() == null || payload.getId() == null) {
                return cb.conjunction();
            }

            String fieldName = payload.getFieldName();
            Class<?> fieldType = root.get(fieldName).getJavaType();
            Comparable typedValue = parseValue(payload.getValue(), fieldType);
            UUID cursorId = payload.getId();

            boolean isAscending = isAscendingSort(query, fieldName);

            Predicate fieldPredicate;
            if (isAscending) {
                fieldPredicate = cb.greaterThan(root.get(fieldName), typedValue);
            } else {
                fieldPredicate = cb.lessThan(root.get(fieldName), typedValue);
            }

            Predicate fieldEquals = cb.equal(root.get(fieldName), typedValue);

            Predicate idPredicate;
            if (isAscending) {
                idPredicate = cb.greaterThan(root.get("id").as(String.class), cursorId.toString());
            } else {
                idPredicate = cb.lessThan(root.get("id").as(String.class), cursorId.toString());
            }

            Predicate tieBreaker = cb.and(fieldEquals, idPredicate);

            return cb.or(fieldPredicate, tieBreaker);
        };
    }

    @Override
    public Specification<T> cursorBefore(String cursor) {
        return (root, query, cb) -> {
            CursorPayload payload = cursorService.decode(cursor);
            if (payload == null || payload.getValue() == null || payload.getId() == null) {
                return cb.conjunction();
            }

            String fieldName = payload.getFieldName();
            Class<?> fieldType = root.get(fieldName).getJavaType();
            Comparable typedValue = parseValue(payload.getValue(), fieldType);
            UUID cursorId = payload.getId();

            boolean isAscending = isAscendingSort(query, fieldName);

            Predicate fieldPredicate;
            if (isAscending) {
                fieldPredicate = cb.lessThan(root.get(fieldName), typedValue);
            } else {
                fieldPredicate = cb.greaterThan(root.get(fieldName), typedValue);
            }

            Predicate fieldEquals = cb.equal(root.get(fieldName), typedValue);

            Predicate idPredicate;
            if (isAscending) {
                idPredicate = cb.lessThan(root.get("id").as(String.class), cursorId.toString());
            } else {
                idPredicate = cb.greaterThan(root.get("id").as(String.class), cursorId.toString());
            }

            Predicate tieBreaker = cb.and(fieldEquals, idPredicate);

            return cb.or(fieldPredicate, tieBreaker);
        };
    }

    @SuppressWarnings("unchecked")
    private Comparable parseValue(String rawValue, Class<?> targetType) {
        if (targetType.equals(String.class)) return rawValue;
        if (targetType.equals(BigDecimal.class)) return new BigDecimal(rawValue);
        if (targetType.equals(UUID.class)) return UUID.fromString(rawValue);
        if (targetType.equals(Long.class) || targetType.equals(long.class)) return Long.valueOf(rawValue);
        if (targetType.equals(Integer.class) || targetType.equals(int.class)) return Integer.valueOf(rawValue);
        if (targetType.equals(Double.class) || targetType.equals(double.class)) return Double.valueOf(rawValue);
        return rawValue;
    }

    private boolean isAscendingSort(jakarta.persistence.criteria.CriteriaQuery<?> query, String fieldName) {
        if (query.getOrderList() != null) {
            for (Order order : query.getOrderList()) {
                if (order.getExpression().toString().contains(fieldName)) {
                    return order.isAscending();
                }
            }
        }
        return false;
    }
}