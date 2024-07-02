package br.com.diego.ordermanagement.predicate.utils;

import br.com.diego.ordermanagement.predicate.abstracts.AbstractPredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FilterUils {

    public BooleanExpression createFilter(AbstractPredicateBuilder<?> builder, String search) {
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        return builder.build();
    }
}
