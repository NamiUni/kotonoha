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
package io.github.namiuni.kotonoha.translatable.message.policy.argument.tag;

import java.lang.reflect.Parameter;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jspecify.annotations.NullMarked;

/**
 * Defines a strategy for resolving the name of a {@link net.kyori.adventure.text.minimessage.tag.Tag} used in
 * {@link net.kyori.adventure.text.minimessage.MiniMessage} formats.
 *
 * @see io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy
 * @see net.kyori.adventure.text.minimessage.MiniMessage
 * @see net.kyori.adventure.text.minimessage.tag.Tag
 * @see net.kyori.adventure.text.minimessage.translation.Argument
 * @since 0.1.0
 */
@NullMarked
public sealed interface TagNameResolver permits AnnotationTagNameResolver, CustomTagNameResolver, AnnotationOrParameterNameTagNameResolver {

    /**
     * Returns a resolver that retrieves the tag name from {@link io.github.namiuni.kotonoha.annotations.Name} annotation.
     *
     * @return an annotation-based name resolver
     * @see net.kyori.adventure.text.minimessage.translation.Argument
     * @see io.github.namiuni.kotonoha.annotations.Name
     * @since 0.1.0
     */
    static TagNameResolver annotationNameResolver() {
        return AnnotationTagNameResolver.INSTANCE;
    }

    /**
     * Returns a resolver that first attempts to retrieve the tag name from the {@link io.github.namiuni.kotonoha.annotations.Name} annotation.
     * If no annotation exists, parameter names converted to snake case.
     * <p>
     * API Note: retrieving the actual parameter names requires that the declaring class be compiled with the {@code -parameters} compiler option
     *
     * @return an annotation or parameter name resolver
     * @see net.kyori.adventure.text.minimessage.translation.Argument
     * @see io.github.namiuni.kotonoha.annotations.Name
     * @see Parameter#isNamePresent()
     * @see Parameter#getName()
     * @since 0.1.0
     */
    static TagNameResolver annotationOrParameterNameResolver() {
        return AnnotationOrParameterNameTagNameResolver.INSTANCE;
    }

    /**
     * Resolves the tag name of a translation argument.
     *
     * @param parameter the parameter
     * @return the resolved argument name
     * @see net.kyori.adventure.text.minimessage.translation.Argument
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
