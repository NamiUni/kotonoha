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
package io.github.namiuni.kotonoha.translation.context;

import java.lang.reflect.Parameter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a single argument passed during a proxy method invocation.
 *
 * @see InvocationContext
 * @since 0.1.0
 */
@NullMarked
public sealed interface InvocationArgument permits InvocationArgumentImpl {

    /**
     * Returns the parameter associated with this argument.
     *
     * @return the parameter
     * @since 0.1.0
     */
    Parameter parameter();

    /**
     * Returns the runtime value of the argument.
     *
     * @return the argument value, which may be null depending on the argument's nullability
     * @since 0.1.0
     */
    @Nullable Object value();

    /**
     * Returns the zero-based index of the parameter within the method's parameter list.
     *
     * @return the index
     * @since 0.1.0
     */
    int index();
}
