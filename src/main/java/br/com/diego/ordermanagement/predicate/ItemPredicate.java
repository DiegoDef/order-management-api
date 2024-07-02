package br.com.diego.ordermanagement.predicate;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.predicate.abstracts.AbstractPredicate;

public class ItemPredicate extends AbstractPredicate<Item> {

    public ItemPredicate(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Class<Item> getEntityClass() {
        return Item.class;
    }

    @Override
    protected String getPathMetadata() {
        return "item";
    }
}
