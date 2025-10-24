/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2025 Namiu (Unitarou)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
