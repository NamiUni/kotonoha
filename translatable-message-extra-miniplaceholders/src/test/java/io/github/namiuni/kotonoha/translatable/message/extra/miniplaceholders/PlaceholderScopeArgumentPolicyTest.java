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
