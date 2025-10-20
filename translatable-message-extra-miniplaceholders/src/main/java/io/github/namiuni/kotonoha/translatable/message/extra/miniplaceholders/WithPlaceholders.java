package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jspecify.annotations.NullMarked;

/**
 * Declares a {@code MiniPlaceholders} scope associated with the annotated element.
 *
 * @see PlaceholderScope
 * @see MiniPlaceholdersArgumentPolicy
 * @see io.github.miniplaceholders.api.MiniPlaceholders
 * @since 0.1.0
 */
@NullMarked
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithPlaceholders {

    /**
     * The scope of placeholders.
     *
     * @return the placeholder scope to use
     * @see PlaceholderScope
     * @since 0.1.0
     */
    PlaceholderScope value();
}
