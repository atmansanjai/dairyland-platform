package com.atman.server.Specification;

import com.atman.server.Specification.DTO.CursorPayload;
import com.atman.server.Specification.DTO.MapDTO;
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
    public Specification<T> filter(List<MapDTO> filters) {
        return (root, query, cb) -> {
            if (filters == null || filters.isEmpty()) {
                return cb.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            for (MapDTO f : filters) {
                if (f.getKey() == null || f.getValue() == null || f.getValue().isEmpty()) {
                    continue;
                }
                String fieldName = f.getKey();
                String value = f.getValue();

                Class<?> fieldType = root.get(fieldName).getJavaType();
                if (Number.class.isAssignableFrom(fieldType)) {
                    predicates.add(cb.equal(root.get(fieldName), Double.parseDouble(value)));
                } else if (fieldType == Boolean.class) {
                    predicates.add(cb.equal(root.get(fieldName), Boolean.parseBoolean(value)));
                } else if (fieldType == UUID.class) {
                    predicates.add(cb.equal(root.get(fieldName), UUID.fromString(value)));
                } else {
                    predicates.add(cb.equal(root.get(fieldName), value));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Specification<T> sortBy(List<MapDTO> sorts) {
        return (root, query, cb) -> {
            List<Order> orders = new ArrayList<>();
            if (sorts != null && !sorts.isEmpty()) {
                for (MapDTO s : sorts) {
                    if (s.getKey() != null && !s.getKey().isEmpty()) {
                        Sort.Direction direction = Sort.Direction.DESC;
                        if (s.getValue() != null) {
                            try {
                                direction = Sort.Direction.valueOf(s.getValue().toUpperCase());
                            } catch (IllegalArgumentException ignored) {}
                        }
                        if (direction == Sort.Direction.ASC) {
                            orders.add(cb.asc(root.get(s.getKey())));
                        } else {
                            orders.add(cb.desc(root.get(s.getKey())));
                        }
                    }
                }
            }

            // Fallback or secondary sort default to createdAt DESC, id DESC if none provided
            if (orders.isEmpty()) {
                orders.add(cb.desc(root.get("createdAt")));
                orders.add(cb.desc(root.get("id")));
            } else {
                // Ensure unique secondary tie-breaker if id isn't already the last sort
                boolean hasIdSort = orders.stream().anyMatch(o -> o.getExpression().toString().contains("id"));
                if (!hasIdSort) {
                    orders.add(cb.desc(root.get("id")));
                }
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

            Predicate fieldPredicate = isAscending
                    ? cb.greaterThan(root.get(fieldName), typedValue)
                    : cb.lessThan(root.get(fieldName), typedValue);

            Predicate fieldEquals = cb.equal(root.get(fieldName), typedValue);
            Predicate idPredicate = isAscending
                    ? cb.greaterThan(root.get("id").as(UUID.class), cursorId)
                    : cb.lessThan(root.get("id").as(UUID.class), cursorId);

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

            Predicate fieldPredicate = isAscending
                    ? cb.lessThan(root.get(fieldName), typedValue)
                    : cb.greaterThan(root.get(fieldName), typedValue);

            Predicate fieldEquals = cb.equal(root.get(fieldName), typedValue);
            Predicate idPredicate = isAscending
                    ? cb.lessThan(root.get("id").as(UUID.class), cursorId)
                    : cb.greaterThan(root.get("id").as(UUID.class), cursorId);

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
        if (targetType.equals(java.time.LocalDateTime.class)) return java.time.LocalDateTime.parse(rawValue);
        if (targetType.equals(java.time.LocalDate.class)) return java.time.LocalDate.parse(rawValue);
        return rawValue;
    }

    private boolean isAscendingSort(jakarta.persistence.criteria.CriteriaQuery<?> query, String fieldName) {
        if (query.getOrderList() != null && !query.getOrderList().isEmpty()) {
            for (Order order : query.getOrderList()) {
                if (order.getExpression().toString().contains(fieldName)) {
                    return order.isAscending();
                }
            }
        }
        return false;
    }
}