package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.CustomTranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.name.ArgumentNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.jspecify.annotations.NullMarked;

/**
 * A policy that integrates {@code MiniPlaceholders} tag resolvers into the translation argument
 * adaptation process.
 * <p>
 * This policy extends the standard MiniMessage argument adaptation by automatically
 * injecting MiniPlaceholders {@link TagResolver} instances as additional translation
 * arguments when a method is annotated with {@link WithPlaceholders}.
 * <p>
 * The policy works by:
 * <ol>
 *   <li>Delegating to the standard MiniMessage policy to adapt method arguments</li>
 *   <li>Checking if the invoked method has a {@link WithPlaceholders} annotation</li>
 *   <li>If present, appending the appropriate MiniPlaceholders {@link TagResolver}
 *       based on the specified {@link PlaceholderScope}</li>
 * </ol>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * public interface Messages {
 *     @Key("welcome.message")
 *     @WithPlaceholders(PlaceholderScope.GLOBAL)
 *     Component welcomeMessage(@Name("player_name") String playerName);
 * }
 *
 * // The policy will automatically inject global placeholders
 * // Translation message: "Welcome <player_name>! Server TPS: <server_tps>"
 * }</pre>
 *
 * @see WithPlaceholders
 * @see PlaceholderScope
 * @see CustomTranslationArgumentAdaptationPolicy
 * @see io.github.miniplaceholders.api.MiniPlaceholders
 * @since 0.1.0
 */
@NullMarked
public final class MiniPlaceholdersArgumentPolicy implements CustomTranslationArgumentAdaptationPolicy {

    private final TranslationArgumentAdaptationPolicy minimessagePolicy;

    private MiniPlaceholdersArgumentPolicy(final TranslationArgumentAdaptationPolicy minimessagePolicy) {
        this.minimessagePolicy = minimessagePolicy;
    }

    /**
     * Returns a {@link io.github.miniplaceholders.api.MiniPlaceholders} policy.
     * <p>
     * This policy internally creates a standard MiniMessage argument adaptation policy
     * using the provided adapter and name resolver, then wraps it to add MiniPlaceholders
     * support.
     *
     * @param argumentAdapter the adapter used to transform method argument types to translation arguments
     * @param nameResolver the resolver used to obtain the argument name from parameter
     * @return a miniplaceholders argument adaptation policy
     * @see io.github.miniplaceholders.api.MiniPlaceholders
     * @see TranslationArgumentAdaptationPolicy#miniMessage(TranslationArgumentAdapter, ArgumentNameResolver)
     * @see WithPlaceholders
     * @see PlaceholderScope
     * @since 0.1.0
     */
    public static MiniPlaceholdersArgumentPolicy of(final TranslationArgumentAdapter argumentAdapter, final ArgumentNameResolver nameResolver) {
        final TranslationArgumentAdaptationPolicy minimessagePolicy = TranslationArgumentAdaptationPolicy.miniMessage(argumentAdapter, nameResolver);
        return new MiniPlaceholdersArgumentPolicy(minimessagePolicy);
    }

    @Override
    public ComponentLike[] adaptArguments(final InvocationContext context) throws IllegalArgumentException, NullPointerException {
        final ComponentLike[] standardArguments = this.minimessagePolicy.adaptArguments(context);

        final Method method = context.method();
        if (!method.isAnnotationPresent(WithPlaceholders.class)) {
            return standardArguments;
        }

        final ComponentLike[] placeholdersIncludedArguments = Arrays.copyOf(standardArguments, standardArguments.length + 1);

        // Gets the MiniPlaceholders placeholders
        final WithPlaceholders placeholderAnnotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = placeholderAnnotation.value();
        final TagResolver placeholders = placeholderScope.placeholders();

        // Add the placeholder argument
        final ComponentLike placeholderArgument = Argument.tagResolver(placeholders);
        placeholdersIncludedArguments[standardArguments.length] = placeholderArgument;

        return placeholdersIncludedArguments;
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {
        this.minimessagePolicy.validate(method);
    }
}
