package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import java.util.function.Supplier;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jspecify.annotations.NullMarked;

/**
 * Defines the scope of MiniPlaceholders.
 *
 * @see WithPlaceholders
 * @see io.github.miniplaceholders.api.MiniPlaceholders
 * @since 0.1.0
 */
@NullMarked
public enum PlaceholderScope {

    /**
     * Global placeholders.
     *
     * @see io.github.miniplaceholders.api.MiniPlaceholders#globalPlaceholders()
     * @since 0.1.0
     */
    GLOBAL(io.github.miniplaceholders.api.MiniPlaceholders::globalPlaceholders),

    /**
     * Audience placeholders.
     *
     * @see io.github.miniplaceholders.api.MiniPlaceholders#audiencePlaceholders()
     * @since 0.1.0
     */
    AUDIENCE(io.github.miniplaceholders.api.MiniPlaceholders::audiencePlaceholders),

    /**
     * Audience and global placeholders.
     *
     * @see io.github.miniplaceholders.api.MiniPlaceholders#audienceGlobalPlaceholders()
     * @since 0.1.0
     */
    AUDIENCE_GLOBAL(io.github.miniplaceholders.api.MiniPlaceholders::audienceGlobalPlaceholders),

    /**
     * Relational placeholders.
     *
     * @see io.github.miniplaceholders.api.MiniPlaceholders#relationalPlaceholders()
     * @since 0.1.0
     */
    RELATIONAL(io.github.miniplaceholders.api.MiniPlaceholders::relationalPlaceholders),

    /**
     * Relational and global placeholders.
     *
     * @see io.github.miniplaceholders.api.MiniPlaceholders#relationalGlobalPlaceholders()
     * @since 0.1.0
     */
    RELATIONAL_GLOBAL(io.github.miniplaceholders.api.MiniPlaceholders::relationalGlobalPlaceholders);

    private final Supplier<TagResolver> supplier;

    PlaceholderScope(final Supplier<TagResolver> supplier) {
        this.supplier = supplier;
    }

    /**
     * Returns the placeholder for this placeholder scope.
     *
     * @return a placeholder
     * @since 0.1.0
     */
    public TagResolver placeholders() {
        return this.supplier.get();
    }
}
