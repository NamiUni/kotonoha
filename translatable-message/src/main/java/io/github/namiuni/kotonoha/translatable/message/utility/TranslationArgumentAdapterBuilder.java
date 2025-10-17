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
package io.github.namiuni.kotonoha.translatable.message.utility;

import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.kyori.adventure.text.TranslationArgument;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class TranslationArgumentAdapterBuilder implements TranslationArgumentAdapter.Builder {

    private final Map<Type, Function<?, TranslationArgument>> adapters = new HashMap<>();

    TranslationArgumentAdapterBuilder() {
    }

    TranslationArgumentAdapterBuilder(final Map<Type, Function<?, TranslationArgument>> adapters) {
        Objects.requireNonNull(adapters, "adapter");
        this.adapters.putAll(adapters);
    }

    private TranslationArgumentAdapter.Builder register(final Type type, final Function<?, TranslationArgument> adapter) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(adapter, "adapter");
        this.adapters.put(type, adapter);
        return this;
    }

    @Override
    public <T> TranslationArgumentAdapter.Builder argument(final Class<T> type, final Function<T, TranslationArgument> adapter) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(adapter, "adapter");
        return this.register(type, adapter);
    }

    @Override
    public <T> TranslationArgumentAdapter.Builder argument(final TypeToken<T> type, final Function<T, TranslationArgument> adapter) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(adapter, "adapter");
        return this.register(type.getType(), adapter);
    }

    @Override
    public TranslationArgumentAdapter build() {
        return new TranslationArgumentAdapterImpl(this.adapters);
    }
}
