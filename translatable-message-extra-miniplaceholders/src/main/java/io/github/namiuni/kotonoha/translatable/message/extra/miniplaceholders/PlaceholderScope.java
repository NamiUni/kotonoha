package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import io.github.miniplaceholders.api.MiniPlaceholders;
import java.util.function.Supplier;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jspecify.annotations.NullMarked;

/**
 * Defines the scope of MiniPlaceholders.
 *
 * @see WithPlaceholders
 * @see MiniPlaceholders
 * @since 0.1.0
 */
@NullMarked
public enum PlaceholderScope {

    /**
     * Global placeholders.
     *
     * @see MiniPlaceholders#globalPlaceholders()
     * @since 0.1.0
     */
    GLOBAL(MiniPlaceholdersIntegration::global),

    /**
     * Audience placeholders.
     *
     * @see MiniPlaceholders#audiencePlaceholders()
     * @since 0.1.0
     */
    AUDIENCE(MiniPlaceholdersIntegration::audience),

    /**
     * Audience and global placeholders.
     *
     * @see MiniPlaceholders#audienceGlobalPlaceholders()
     * @since 0.1.0
     */
    AUDIENCE_GLOBAL(MiniPlaceholdersIntegration::audienceGlobal),

    /**
     * Relational placeholders.
     *
     * @see MiniPlaceholders#relationalPlaceholders()
     * @since 0.1.0
     */
    RELATIONAL(MiniPlaceholdersIntegration::relational),

    /**
     * Relational and global placeholders.
     *
     * @see MiniPlaceholders#relationalGlobalPlaceholders()
     * @since 0.1.0
     */
    RELATIONAL_GLOBAL(MiniPlaceholdersIntegration::relationalGlobal);

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
