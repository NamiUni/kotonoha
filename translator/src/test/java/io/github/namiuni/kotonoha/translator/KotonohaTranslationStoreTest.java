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
package io.github.namiuni.kotonoha.translator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.github.namiuni.kotonoha.annotations.Key;
import io.github.namiuni.kotonoha.annotations.Message;
import java.text.MessageFormat;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@NullMarked
@SuppressWarnings("unused")
class KotonohaTranslationStoreTest {

    private static final net.kyori.adventure.key.Key TEST_NAME = net.kyori.adventure.key.Key.key("test");

    interface EmptyInterface {
        // empty
    }

    interface DefaultMethodInterface {
        @Key("default.method")
        @Message(locale = "en_US", content = "Default method")
        default void defaultMethod() {
            // ignored
        }
    }

    interface ObjectMethodsInterface {
        @Override
        boolean equals(Object obj);

        @Override
        String toString();

        @Override
        int hashCode();
    }

    interface MissingKeyAnnotationInterface {
        @Message(locale = "en_US", content = "Missing @Key Annotation")
        void missingKey();
    }

    interface MissingMessageAnnotationInterface {
        @Key("missing.message")
        void missingMessage();
    }

    interface ValidSingleMethodInterface {
        @Key("valid.method")
        @Message(locale = "en_US", content = "Valid method")
        void validMethod();
    }

    interface ValidMultipleMethodsInterface {
        @Key("valid.method1")
        @Message(locale = "en_US", content = "Valid method 1")
        void validMethod1();

        @Key("valid.method2")
        @Message(locale = "en_US", content = "Valid method 2")
        void validMethod2();
    }

    interface MultipleMessagesInterface {
        @Key("multi.locale.message")
        @Message(locale = "en_US", content = "Hello")
        @Message(locale = "ja_JP", content = "こんにちは")
        void multiLocaleMessage();
    }

    interface TestMessageInterface {
        @Key("test.message")
        @Message(locale = "en_US", content = "Test message")
        @Message(locale = "ja_JP", content = "テストメッセージ")
        void testMessage();
    }

    interface TestMessageFormatArgsInterface {
        @Key("test.args.message")
        @Message(locale = "en_US", content = "Hello, {0}!")
        void testArgsMessage(String name);
    }

    interface InvalidMessageFormatInterface {
        @Key("invalid.mf.syntax")
        @Message(locale = "en_US", content = "Invalid { syntax")
        void invalidMessageFormat();
    }

    interface TestMiniMessageInterface {
        @Key("test.mm.message")
        @Message(locale = "en_US", content = "<green>Hello!</green>")
        void testMiniMessage();
    }

    @Nested
    @DisplayName("Factory Methods")
    class FactoryMethodsTest {

        @Test
        @DisplayName("should create MessageFormat translation store")
        void shouldCreateMessageFormatTranslationStore() {
            assertDoesNotThrow(() -> {
                KotonohaTranslationStore.messageFormat(TEST_NAME);
            });
        }

        @Test
        @DisplayName("should create MiniMessage translation store")
        void shouldCreateMiniMessageTranslationStore() {
            assertDoesNotThrow(() -> {
                KotonohaTranslationStore.miniMessage(TEST_NAME);
            });
            assertDoesNotThrow(() -> {
                KotonohaTranslationStore.miniMessage(TEST_NAME, MiniMessage.miniMessage());
            });
        }
    }

    @Nested
    @DisplayName("registerInterface() Logic")
    class RegisterInterfaceLogicTest {

        private KotonohaTranslationStore<MessageFormat> store;

        @BeforeEach
        void setUp() {
            this.store = KotonohaTranslationStore.messageFormat(TEST_NAME);
        }

        @Test
        @DisplayName("should register empty interface without errors")
        void shouldRegisterEmptyInterface() {
            assertDoesNotThrow(() -> this.store.registerInterface(EmptyInterface.class));
        }

        @Test
        @DisplayName("should ignore default methods")
        void shouldIgnoreDefaultMethod() {
            this.store.registerInterface(DefaultMethodInterface.class);
            assertFalse(this.store.contains("default.method"));
        }

        @Test
        @DisplayName("should ignore methods from java.lang.Object")
        void shouldIgnoreObjectMethods() {
            assertDoesNotThrow(() -> this.store.registerInterface(ObjectMethodsInterface.class));
        }

        @Test
        @DisplayName("should throw IllegalStateException for missing @Key annotation")
        void shouldThrowMissingKeyAnnotation() {
            Exception e = assertThrows(
                    IllegalStateException.class,
                    () -> this.store.registerInterface(MissingKeyAnnotationInterface.class)
            );
            assertTrue(e.getMessage().contains("Missing annotation '@Key'"));
        }

        @Test
        @DisplayName("should throw IllegalStateException for missing @Message annotation")
        void shouldThrowMissingMessageAnnotation() {
            Exception e = assertThrows(
                    IllegalStateException.class,
                    () -> this.store.registerInterface(MissingMessageAnnotationInterface.class)
            );
            assertTrue(e.getMessage().contains("Missing annotation '@Message'"));
        }

        @Test
        @DisplayName("should register a single valid method")
        void shouldRegisterValidSingleMethod() {
            this.store.registerInterface(ValidSingleMethodInterface.class);
            assertTrue(this.store.contains("valid.method", Locale.US));
        }

        @Test
        @DisplayName("should register multiple valid methods")
        void shouldRegisterValidMultipleMethods() {
            this.store.registerInterface(ValidMultipleMethodsInterface.class);
            assertTrue(this.store.contains("valid.method1", Locale.US));
            assertTrue(this.store.contains("valid.method2", Locale.US));
        }

        @Test
        @DisplayName("should register multiple locales from multiple @Message annotations")
        void shouldRegisterMultipleLocales() {
            this.store.registerInterface(MultipleMessagesInterface.class);

            assertTrue(this.store.contains("multi.locale.message", Locale.US));
            assertTrue(this.store.contains("multi.locale.message", Locale.JAPAN));

            MessageFormat en = this.store.translate("multi.locale.message", Locale.US);
            assertNotNull(en);
            assertEquals("Hello", en.format(null));

            MessageFormat ja = this.store.translate("multi.locale.message", Locale.JAPAN);
            assertNotNull(ja);
            assertEquals("こんにちは", ja.format(null));
        }
    }

    @Nested
    @DisplayName("MessageFormat Store")
    class MessageFormatStoreTest {

        private KotonohaTranslationStore<MessageFormat> store;

        @BeforeEach
        void setUp() {
            this.store = KotonohaTranslationStore.messageFormat(TEST_NAME);
        }

        @Test
        @DisplayName("should return correct MessageFormat object via translate(String, Locale)")
        void shouldReturnMessageFormat() {
            this.store.registerInterface(TestMessageInterface.class);

            MessageFormat translated = this.store.translate("test.message", Locale.US);
            assertNotNull(translated);
            assertEquals(new MessageFormat("Test message", Locale.US), translated);

            MessageFormat translatedJa = this.store.translate("test.message", Locale.JAPAN);
            assertNotNull(translatedJa);
            assertEquals(new MessageFormat("テストメッセージ", Locale.JAPAN), translatedJa);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException for invalid MessageFormat syntax")
        void shouldThrowForInvalidMessageFormatSyntax() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.store.registerInterface(InvalidMessageFormatInterface.class)
            );
        }
    }

    @Nested
    @DisplayName("MiniMessage Store")
    class MiniMessageStoreTest {

        private KotonohaTranslationStore<String> storeWithDefaultMM;
        private KotonohaTranslationStore<String> storeWithCustomMM;
        @SuppressWarnings("FieldCanBeLocal")
        private MiniMessage customMiniMessage;

        @BeforeEach
        void setUp() {
            this.customMiniMessage = MiniMessage.builder().strict(true).build();
            this.storeWithDefaultMM = KotonohaTranslationStore.miniMessage(TEST_NAME);
            this.storeWithCustomMM = KotonohaTranslationStore.miniMessage(TEST_NAME, this.customMiniMessage);
        }

        @Test
        @DisplayName("should return parsed Component using default MiniMessage")
        void shouldReturnMiniMessageWithDefault() {
            this.storeWithDefaultMM.registerInterface(TestMiniMessageInterface.class);

            TranslatableComponent translatable = Component.translatable("test.mm.message");
            Component translated = this.storeWithDefaultMM.translate(translatable, Locale.US);

            Component expected = Component.text("Hello!").color(NamedTextColor.GREEN);
            assertEquals(expected, translated);
        }

        @Test
        @DisplayName("should return parsed Component using custom MiniMessage")
        void shouldReturnMiniMessageWithCustom() {
            this.storeWithCustomMM.registerInterface(TestMiniMessageInterface.class);

            TranslatableComponent translatable = Component.translatable("test.mm.message");
            Component translated = this.storeWithCustomMM.translate(translatable, Locale.US);

            Component expected = Component.text("Hello!").color(NamedTextColor.GREEN);
            assertEquals(expected, translated);
        }
    }
}
