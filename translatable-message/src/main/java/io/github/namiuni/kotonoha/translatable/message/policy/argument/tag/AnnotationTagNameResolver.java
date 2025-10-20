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

import io.github.namiuni.kotonoha.annotations.Name;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import java.lang.reflect.Parameter;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jspecify.annotations.NullMarked;

@NullMarked
record AnnotationTagNameResolver() implements TagNameResolver {

    static final AnnotationTagNameResolver INSTANCE = new AnnotationTagNameResolver();

    @Override
    @SuppressWarnings("PatternValidation")
    public @TagPattern String resolve(final Parameter parameter) {
        final Name annotation = parameter.getAnnotation(Name.class);
        return annotation.value();
    }

    @Override
    public boolean supports(final Parameter parameter) throws KotonohaValidationException {
        return parameter.isAnnotationPresent(Name.class);
    }
}
