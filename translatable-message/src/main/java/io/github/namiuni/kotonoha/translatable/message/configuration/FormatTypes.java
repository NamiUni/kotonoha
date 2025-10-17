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
package io.github.namiuni.kotonoha.translatable.message.configuration;

import io.github.namiuni.kotonoha.translatable.message.KotonohaMessages;
import org.jspecify.annotations.NullMarked;

/**
 * Holds various message format types used when creating proxy instances in {@link KotonohaMessages}.
 *
 * @see KotonohaMessages
 * @since 0.1.0
 */
@NullMarked
@SuppressWarnings("unused")
public final class FormatTypes {

    /**
     * {@code MessageFormat} style.
     *
     * @see java.text.MessageFormat
     * @since 0.1.0
     */
    public static final FormatType MESSAGE_FORMAT;

    /**
     * {@code MiniMessage} style.
     *
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @since 0.1.0
     */
    public static final FormatType MINI_MESSAGE;

    static {
        MESSAGE_FORMAT = new MessageFormatType();
        MINI_MESSAGE = new MiniMessageType();
    }

    private FormatTypes() {
    }
}
