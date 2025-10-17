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
package io.github.namiuni.kotonoha.translation.utility;

import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;
import net.kyori.adventure.text.TranslationArgument;
import org.jspecify.annotations.NullMarked;

@NullMarked
record TranslationArgumentAdapterImpl(Map<Type, Function<?, TranslationArgument>> adapters) implements TranslationArgumentAdapter {

    TranslationArgumentAdapterImpl(final Map<Type, Function<?, TranslationArgument>> adapters) {
        this.adapters = Map.copyOf(adapters);
    }

    @SuppressWarnings("unchecked")
    public TranslationArgument adapt(final Type type, final Object value) {
        final Function<?, TranslationArgument> adapter = this.adapters.get(type);

        if (adapter == null) {
            final String message = "No adapter registered for type: %s";
            throw new IllegalArgumentException(message.formatted(GenericTypeReflector.getTypeName(type)));
        }

        return ((Function<Object, TranslationArgument>) adapter).apply(value);
    }

    public boolean supports(final Type type) {
        return this.adapters.containsKey(type);
    }

    @Override
    public Builder toBuilder() {
        return new TranslationArgumentAdapterBuilder(this.adapters);
    }
}
