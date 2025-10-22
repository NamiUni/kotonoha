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
package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.tag.TagNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import java.lang.reflect.Method;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@NullMarked
final class PlaceholderScopeArgumentPolicyTest {

    interface TestMessages {

        @WithPlaceholders(PlaceholderScope.GLOBAL)
        Component testMessage();
    }

    @Test
    @DisplayName("should included the MiniPlaceholders TagResolver")
    void shouldIncludePlaceholders() throws NoSuchMethodException {

        final MiniPlaceholdersArgumentPolicy policy = MiniPlaceholdersArgumentPolicy.of(
                TranslationArgumentAdapter.standard(),
                TagNameResolver.annotationNameResolver()
        );

        final Method method = TestMessages.class.getMethod("testMessage");
        final InvocationContext context = InvocationContext.of(method, new Object[0]);

        final ComponentLike[] arguments = policy.adaptArguments(context);

        assertEquals(1, arguments.length);
    }
}
