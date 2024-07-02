package br.com.diego.ordermanagement.predicate.abstracts;

import br.com.diego.ordermanagement.predicate.SearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPredicateBuilder<T> {
    private List<SearchCriteria> params;

    protected AbstractPredicateBuilder() {
        params = new ArrayList<>();
    }

    protected abstract AbstractPredicate<T> createPredicate(SearchCriteria search);

    public AbstractPredicateBuilder<T> with(
            String key, String operation, Object value) {

        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }

        List<BooleanExpression> predicates = params.stream().map(param -> {
            AbstractPredicate<T> predicate = createPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).toList();

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
