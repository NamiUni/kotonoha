/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2026 Namiu (うにたろう)
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
import java.util.concurrent.atomic.AtomicReference;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.util.TriState;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class MiniPlaceholdersIntegration {

    private static final AtomicReference<TriState> MINI_PLACEHOLDERS_LOADED = new AtomicReference<>(TriState.NOT_SET);

    private MiniPlaceholdersIntegration() {
    }

    static boolean miniPlaceholdersLoaded() {
        if (MINI_PLACEHOLDERS_LOADED.get() == TriState.NOT_SET) {
            try {
                Class.forName("io.github.miniplaceholders.api.MiniPlaceholders");
                MINI_PLACEHOLDERS_LOADED.compareAndSet(TriState.NOT_SET, TriState.TRUE);
            } catch (final ClassNotFoundException ignored) {
                MINI_PLACEHOLDERS_LOADED.compareAndSet(TriState.NOT_SET, TriState.FALSE);
            }
        }
        return MINI_PLACEHOLDERS_LOADED.get() == TriState.TRUE;
    }

    static TagResolver global() {
        return MiniPlaceholders.globalPlaceholders();
    }

    static TagResolver audience() {
        return MiniPlaceholders.audiencePlaceholders();
    }

    static TagResolver audienceGlobal() {
        return MiniPlaceholders.audienceGlobalPlaceholders();
    }

    static TagResolver relational() {
        return MiniPlaceholders.relationalPlaceholders();
    }

    static TagResolver relationalGlobal() {
        return MiniPlaceholders.relationalGlobalPlaceholders();
    }
}
