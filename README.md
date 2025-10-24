# Kotonoha (言の葉)

A localization framework for projects using [Adventure](https://github.com/KyoriPowered/adventure).

Kotonoha allows defining multilingual messages as methods in Java interfaces.
## Modules

| Module                                          | Description                                                                                                                                  |
|-------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| **kotonoha-annotations**                        | Common annotations shared across modules.                                                                                                    |
| **kotonoha-resourcebundle-generator-processor** | An annotation processor that generates `.properties` resource bundles from annotated interfaces and includes them in the resulting artifact. |
| **kotonoha-message**                            | Core module providing reflection-based proxies that create `TranslatableComponent` instances from interface method calls.                    |
| **kotonoha-message-extra-miniplaceholders**     | Extension module providing [MiniPlaceholders](https://github.com/MiniPlaceholders) integration.                                              |
| **kotonoha-translator**                         | A `Translator` module that registers translations from annotated methods.                                                                    |

### Quick Start (MessageFormat)

Depends on `kotonoha-message` and `kotonoha-translator`.

Define a message interface:

```java
@ResourceBundle(baseName = "messages")
public interface ExampleMessages {

    // {0} will be replaced by the value of the 'playerName' argument.
    @Key("your_plugin.welcome.message")
    @Message(locale = "en_US", content = "Welcome, {0}!")
    @Message(locale = "ja_JP", content = "ようこそ、{0}!")
    Component welcomeMessage(Component playerName);

    /*
     For MiniMessage:
     <player_name> will be replaced by the value of the 'playerName' argument.
     @Key("your_plugin.welcome.message")
     @Message(locale = "en_US", content = "Welcome, <player_name>!")
     @Message(locale = "ja_JP", content = "ようこそ、<player_name>!")
     Component welcomeMessage(@Name("player_name") Component playerName);
    */
}
```

Register the translator and create a proxy instance:

```java
// Register translations from the interface.
Key name = Key.key("your_plugin", "messages");
KotonohaTranslationStore<MessageFormat> translator = KotonohaTranslationStore.messageFormat(name);
translator.registerInterface(ExampleMessages.class);

// Add to global source.
GlobalTranslator.translator().addSource(translator);

// Create proxy instance.
ExampleMessages messages = KotonohaMessage.createProxy(ExampleMessages.class, FormatTypes.MESSAGE_FORMAT);

// Get the message and send it.
TranslatableComponent result = messages.welcomeMessage(player.name());
audience.sendMessage(result); // Welcome, Notch!!
```

For more information on Adventure's localization:
https://docs.papermc.io/adventure/localization/

## Documentation

Javadocs (Available soon)

GitHub Wiki (Available soon)