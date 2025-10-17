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
package io.github.namiuni.kotonoha.translation.policy.argument.name;

import java.lang.reflect.Parameter;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jspecify.annotations.NullMarked;

/**
 * Defines a strategy for resolving the name of an argument used in
 * {@link net.kyori.adventure.text.minimessage.MiniMessage} formats that rely on argument naming.
 * <p>
 * The resolved name is typically used as a tag key in
 * a {@link net.kyori.adventure.text.minimessage.MiniMessage} format string.
 *
 * @see net.kyori.adventure.text.minimessage.MiniMessage
 * @see net.kyori.adventure.text.minimessage.translation.Argument
 * @see net.kyori.adventure.text.minimessage.tag.Tag
 * @since 0.1.0
 */
@NullMarked
public sealed interface ArgumentNameResolver permits AnnotationArgumentNameResolver, CustomArgumentNameResolver, AnnotationOrParameterNameArgumentNameResolver {

    /**
     * Returns a resolver that retrieves the argument name exclusively from {@link io.github.namiuni.kotonoha.annotations.Name} annotation.
     *
     * @return an annotation-based name resolver
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @see net.kyori.adventure.text.minimessage.translation.Argument
     * @see net.kyori.adventure.text.minimessage.tag.Tag
     * @see io.github.namiuni.kotonoha.annotations.Name
     * @since 0.1.0
     */
    static ArgumentNameResolver annotationNameResolver() {
        return AnnotationArgumentNameResolver.INSTANCE;
    }

    /**
     * Returns a resolver that first attempts to retrieve the argument name from the {@link io.github.namiuni.kotonoha.annotations.Name} annotation.
     * If no annotation exists, it returns the name provided by the compiler for the parameter, converted to snake case.
     *
     * @return an annotation or parameter name resolver
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @see net.kyori.adventure.text.minimessage.translation.Argument
     * @see net.kyori.adventure.text.minimessage.tag.Tag
     * @see io.github.namiuni.kotonoha.annotations.Name
     * @see java.lang.reflect.Parameter
     * @since 0.1.0
     */
    static ArgumentNameResolver annotationOrParameterNameResolver() {
        return AnnotationOrParameterNameArgumentNameResolver.INSTANCE;
    }

    /**
     * Resolves and returns the name of a translation argument,
     * typically used as a tag key in a MiniMessage format string.
     * <p>
     * The returned string is typically expected to be a valid tag key pattern for MiniMessage.
     *
     * @param parameter the parameter
     * @return the resolved argument name (non-null)
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @see net.kyori.adventure.text.minimessage.translation.Argument
     * @see net.kyori.adventure.text.minimessage.tag.Tag
     * @see net.kyori.adventure.text.TranslatableComponent
     * @since 0.1.0
     */
    @TagPattern
    String resolve(Parameter parameter);

    /**
     * Determines whether this resolver can extract a valid argument name from the given parameter.
     *
     * @param parameter the parameter
     * @return {@code true} if a name can be resolved, {@code false} otherwise
     * @since 0.1.0
     */
    boolean supports(Parameter parameter);
}
