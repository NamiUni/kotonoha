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
package io.github.namiuni.kotonoha.translatable.message;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.tag.TagNameResolver;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@NullMarked
@DisplayName("ArgumentNameResolver tests")
final class TagNameResolverTest {

    @Test
    @DisplayName("Should get annotation name resolver")
    void testAnnotationNameResolver() {
        final TagNameResolver resolver = TagNameResolver.annotationNameResolver();
        assertNotNull(resolver);
    }

    @Test
    @DisplayName("Should get annotation or parameter name resolver")
    void testAnnotationOrParameterNameResolver() {
        final TagNameResolver resolver = TagNameResolver.annotationOrParameterNameResolver();
        assertNotNull(resolver);
    }

    @Test
    @DisplayName("Annotation name resolver should be singleton")
    void testAnnotationNameResolverSingleton() {
        final TagNameResolver resolver1 = TagNameResolver.annotationNameResolver();
        final TagNameResolver resolver2 = TagNameResolver.annotationNameResolver();
        assertSame(resolver1, resolver2);
    }

    @Test
    @DisplayName("Annotation or parameter name resolver should be singleton")
    void testAnnotationOrParameterNameResolverSingleton() {
        final TagNameResolver resolver1 = TagNameResolver.annotationOrParameterNameResolver();
        final TagNameResolver resolver2 = TagNameResolver.annotationOrParameterNameResolver();
        assertSame(resolver1, resolver2);
    }
}
