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

import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import org.jspecify.annotations.NullMarked;

/**
 * Provides utilities for adapting values to {@link TranslationArgument}s using mapping functions corresponding to the specified type.
 *
 * @see TranslationArgument
 * @see TranslationArgumentAdaptationPolicy
 * @since 0.1.0
 */
// Design Note:
// このクラスはリフレクション操作に慣れていない開発者でも手軽に扱えることを目的としているため、実装が複雑になるような拡張はしない。
// 複雑な実装が必要な場合はTranslationArgumentAdaptationPolicyを実装する。
@NullMarked
public sealed interface TranslationArgumentAdapter permits TranslationArgumentAdapterImpl {

    /**
     * Returns the standard {@code TranslationArgumentAdapter} instance with common type mappings.
     *
     * <p>Supported types:</p>
     * <ul>
     * <li>TranslationArgumentLike</li>
     * <li>TranslationArgument</li>
     * <li>ComponentLike</li>
     * <li>Component</li>
     * <li>Number</li>
     * <li>int</li>
     * <li>Integer</li>
     * <li>long</li>
     * <li>Long</li>
     * <li>float</li>
     * <li>Float</li>
     * <li>double</li>
     * <li>Double</li>
     * <li>boolean</li>
     * <li>Boolean</li>
     * <li>String</li>
     * <li>char</li>
     * <li>Character</li>
     * </ul>
     *
     * @return the standard adapter instance with predefined mappings
     * @since 0.1.0
     */
    static TranslationArgumentAdapter standard() {
        return StandardArgumentAdapter.STANDARD;
    }

    /**
     * Returns a new builder for constructing a translation argument adapter.
     *
     * @return a new builder
     * @since 0.1.0
     */
    static TranslationArgumentAdapter.Builder builder() {
        return new TranslationArgumentAdapterBuilder();
    }

    /**
     * Returns a new builder for initialized with the current adapter's mappings.
     *
     * @return a new builder
     * @since 0.1.0
     */
    TranslationArgumentAdapter.Builder toBuilder();

    /**
     * Adapts the specified value into a {@link TranslationArgument} according to its declared type.
     *
     * @param type  the type of the value to adapt
     * @param value the value to adapt
     * @return the resolved {@link TranslationArgument}
     * @throws IllegalArgumentException if no adapter is registered for the specified type
     * @since 0.1.0
     */
    TranslationArgument adapt(Type type, Object value) throws IllegalArgumentException;

    /**
     * Checks if this adapter supports adaptation for the specified type.
     *
     * @param type the type of the value to adapt
     * @return {@code true} if an adapter is registered for the specified type; {@code false} otherwise
     * @since 0.1.0
     */
    boolean supports(Type type);

    /**
     * A builder for creating immutable translation argument adapter.
     *
     * @since 0.1.0
     */
    sealed interface Builder permits TranslationArgumentAdapterBuilder {

        /**
         * Registers a custom function to adapt a value of the specified type token into a {@link TranslationArgument}.
         *
         * @param <T> the source value type
         * @param type the {@link TypeToken} representing the value type (useful for generic types)
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        <T> Builder argument(TypeToken<T> type, Function<T, TranslationArgument> adapter);

        /**
         * Registers a custom function to adapt a value of the specified class into a {@link TranslationArgument}.
         *
         * @param <T>     the source value type
         * @param type    the class of the value type
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        <T> Builder argument(Class<T> type, Function<T, TranslationArgument> adapter);

        /**
         * Registers a custom function to adapt a value of the specified type token into a {@link ComponentLike} first, then wraps it as a {@link TranslationArgument}.
         *
         * @param <T>     the source value type
         * @param type    the type token of the value type (for generic types)
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        default <T> Builder component(final TypeToken<T> type, final Function<T, ComponentLike> adapter) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(adapter, "adapter");
            return this.argument(type, adapter.andThen(TranslationArgument::component));
        }

        /**
         * Registers a custom function to adapt a value of the specified class into a {@link ComponentLike} first, then wraps it as a {@link TranslationArgument}.
         *
         * @param <T>     the source value type
         * @param type    the class of the value type
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        default <T> Builder component(final Class<T> type, final Function<T, ComponentLike> adapter) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(adapter, "adapter");
            return this.argument(type, adapter.andThen(TranslationArgument::component));
        }

        /**
         * Registers a custom function to adapt a value of the specified type token into a {@code String} first, then wraps it as a {@link TranslationArgument}.
         *
         * @param <T>     the source value type
         * @param type    the type token of the value type (for generic types)
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        default <T> Builder string(final TypeToken<T> type, final Function<T, String> adapter) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(adapter, "adapter");
            return this.component(type, adapter.andThen(Component::text));
        }

        /**
         * Registers a custom function to adapt a value of the specified class into a {@code String} first, then wraps it as a {@link TranslationArgument}.
         *
         * @param <T>     the source value type
         * @param type    the class of the value type
         * @param adapter the mapping function that converts the value into a {@link TranslationArgument}
         * @return this builder
         * @since 0.1.0
         */
        default <T> Builder string(final Class<T> type, final Function<T, String> adapter) {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(adapter, "adapter");
            return this.component(type, adapter.andThen(Component::text));
        }

        /**
         * Returns an immutable translation argument adapter.
         *
         * @return a translation argument adapter
         * @since 0.1.0
         */
        TranslationArgumentAdapter build();
    }
}
