package br.com.diego.ordermanagement.predicate.abstracts;

import br.com.diego.ordermanagement.exceptions.BadRequestException;
import br.com.diego.ordermanagement.predicate.SearchCriteria;
import com.querydsl.core.types.dsl.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public abstract class AbstractPredicate<T> {

    private SearchCriteria criteria;

    public abstract Class<T> getEntityClass();

    protected abstract String getPathMetadata();

    public BooleanExpression getPredicate() {
        PathBuilder<T> entityPath = new PathBuilder<>(getEntityClass(), getPathMetadata());
        if (isNumeric(criteria.getKey())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            return switch (criteria.getOperation()) {
                case ":" -> path.eq(value);
                case ">" -> path.gt(value);
                case "<" -> path.lt(value);
                default -> throw new BadRequestException("Invalid operation: " + criteria.getOperation());
            };
        } else if (isBoolean(criteria.getKey())) {
            BooleanPath path = entityPath.getBoolean(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.eq(Boolean.parseBoolean(criteria.getValue().toString()));
            }
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    private boolean isNumeric(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        try {
            Class<?> fieldType = getEntityClass().getDeclaredField(fieldName).getType();
            return fieldType.equals(Integer.class) || fieldType.equals(int.class) ||
                    fieldType.equals(Long.class) || fieldType.equals(long.class) ||
                    fieldType.equals(BigDecimal.class) || fieldType.equals(Double.class) ||
                    fieldType.equals(double.class) || fieldType.equals(Float.class) ||
                    fieldType.equals(float.class);
        } catch (NoSuchFieldException e) {
            throw new BadRequestException(String.format("Field %s not found for class %s, error: %s", fieldName, getEntityClass().getName(), e.getMessage()));
        }
    }


    private boolean isBoolean(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        try {
            Class<?> fieldType = getEntityClass()
                    .getDeclaredField(fieldName)
                    .getType();
            return fieldType.equals(Boolean.class) || fieldType.equals(boolean.class);
        } catch (NoSuchFieldException e) {
            throw new BadRequestException(String.format("Field %s not found for class %s, error: %s", fieldName, getEntityClass().getName(), e));
        }
    }
}
