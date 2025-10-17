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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.TranslationArgumentLike;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class StandardArgumentAdapter {

    static final TranslationArgumentAdapter STANDARD;

    static {
        STANDARD = TranslationArgumentAdapter.builder()

                // components
                .argument(TranslationArgumentLike.class, TranslationArgumentLike::asTranslationArgument)
                .argument(TranslationArgument.class, translationArgument -> translationArgument)
                .component(ComponentLike.class, TranslationArgument::component)
                .component(Component.class, TranslationArgument::component)

                // numbers
                .argument(Number.class, TranslationArgument::numeric)
                .argument(int.class, TranslationArgument::numeric)
                .argument(Integer.class, TranslationArgument::numeric)
                .argument(long.class, TranslationArgument::numeric)
                .argument(Long.class, TranslationArgument::numeric)
                .argument(float.class, TranslationArgument::numeric)
                .argument(Float.class, TranslationArgument::numeric)
                .argument(double.class, TranslationArgument::numeric)
                .argument(Double.class, TranslationArgument::numeric)

                // boolean
                .argument(boolean.class, TranslationArgument::bool)
                .argument(Boolean.class, TranslationArgument::bool)

                // string
                .string(String.class, string -> string)

                // char
                .component(char.class, Component::text)
                .component(Character.class, Component::text)

                // build
                .build();
    }

    private StandardArgumentAdapter() {
    }
}
