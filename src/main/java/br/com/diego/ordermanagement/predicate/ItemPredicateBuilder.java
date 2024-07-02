package br.com.diego.ordermanagement.predicate;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.predicate.abstracts.AbstractPredicate;
import br.com.diego.ordermanagement.predicate.abstracts.AbstractPredicateBuilder;

public class ItemPredicateBuilder extends AbstractPredicateBuilder<Item> {

    @Override
    protected AbstractPredicate<Item> createPredicate(SearchCriteria search) {
        return new ItemPredicate(search);
    }
}
