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
package io.github.namiuni.kotonoha.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import io.github.namiuni.kotonoha.translation.policy.argument.name.ArgumentNameResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArgumentNameResolver tests")
class ArgumentNameResolverTests {

    @Test
    @DisplayName("Should get annotation name resolver")
    void testAnnotationNameResolver() {
        final ArgumentNameResolver resolver = ArgumentNameResolver.annotationNameResolver();
        assertNotNull(resolver);
    }

    @Test
    @DisplayName("Should get annotation or parameter name resolver")
    void testAnnotationOrParameterNameResolver() {
        final ArgumentNameResolver resolver = ArgumentNameResolver.annotationOrParameterNameResolver();
        assertNotNull(resolver);
    }

    @Test
    @DisplayName("Annotation name resolver should be singleton")
    void testAnnotationNameResolverSingleton() {
        final ArgumentNameResolver resolver1 = ArgumentNameResolver.annotationNameResolver();
        final ArgumentNameResolver resolver2 = ArgumentNameResolver.annotationNameResolver();
        assertSame(resolver1, resolver2);
    }

    @Test
    @DisplayName("Annotation or parameter name resolver should be singleton")
    void testAnnotationOrParameterNameResolverSingleton() {
        final ArgumentNameResolver resolver1 = ArgumentNameResolver.annotationOrParameterNameResolver();
        final ArgumentNameResolver resolver2 = ArgumentNameResolver.annotationOrParameterNameResolver();
        assertSame(resolver1, resolver2);
    }
}
