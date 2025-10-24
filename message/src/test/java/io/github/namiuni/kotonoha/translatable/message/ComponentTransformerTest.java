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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.github.namiuni.kotonoha.translatable.message.utility.ComponentTransformer;
import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@NullMarked
@DisplayName("ComponentTransformer tests")
final class ComponentTransformerTest {

    @Test
    @DisplayName("Should create transformer with builder")
    void testCreateTransformer() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(String.class, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        assertNotNull(transformer);
    }

    @Test
    @DisplayName("Should register transformer with Class")
    void testRegisterTransformerWithClass() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(String.class, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        assertTrue(transformer.supports(new TypeToken<String>() {
        }.getType()));
    }

    @Test
    @DisplayName("Should register transformer with TypeToken")
    void testRegisterTransformerWithTypeToken() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(new TypeToken<>() {
                }, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        assertTrue(transformer.supports(new TypeToken<String>() {
        }.getType()));
    }

    @Test
    @DisplayName("Should register transformer for void type")
    void testRegisterTransformerForVoid() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(void.class, component -> null)
                .build();

        assertTrue(transformer.supports(void.class));
    }

    @Test
    @DisplayName("Should transform component to registered type")
    void testTransformComponent() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(String.class, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        final Component component = Component.text("test");
        final Object result = transformer.transform(new TypeToken<String>() {
        }.getType(), component);

        assertNotNull(result);
        assertInstanceOf(String.class, result);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unsupported type")
    void testTransformUnsupportedType() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(String.class, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        final Component component = Component.text("test");
        final Type integerType = new TypeToken<Integer>() { }.getType();
        assertThrows(IllegalArgumentException.class, () -> transformer.transform(integerType, component));
    }

    @Test
    @DisplayName("Should check if type is supported")
    void testSupportsType() {
        final ComponentTransformer transformer = ComponentTransformer.builder()
                .register(String.class, PlainTextComponentSerializer.plainText()::serialize)
                .build();

        assertTrue(transformer.supports(new TypeToken<String>() {
        }.getType()));
        assertFalse(transformer.supports(new TypeToken<Integer>() {
        }.getType()));
    }
}
