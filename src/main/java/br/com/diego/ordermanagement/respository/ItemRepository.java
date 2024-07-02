package br.com.diego.ordermanagement.respository;


import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.QItem;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>, QuerydslPredicateExecutor<Item>, QuerydslBinderCustomizer<QItem> {

    @Override
    default void customize(QuerydslBindings bindings, QItem root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
