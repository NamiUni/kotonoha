# Kotonoha (言の葉)

A localization framework for [Adventure](https://github.com/KyoriPowered/adventure) focused projects.

Kotonoha simplifies the creation and management of multilingual messages by treating them as methods in a Java interface.

## Modules

| Module                                                   | Description                                                                                                                                                                                     |
|----------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **kotonoha-annotations**                                 | Common annotations shared across modules.                                                                                                                                                       |
| **kotonoha-resourcebundle-generator-processor**          | An annotation processor that generates .properties resource bundles directly from annotated interfaces and includes them in the resulting artifact.                                             |
| **kotonoha-translatable-message**                        | The core module providing reflection-based proxies. These proxies create TranslatableComponent instances from interface method calls, applying configurable format and transformation policies. |

[//]: # (| **kotonoha-translatable-message-extra-miniplaceholders** | Extension module providing integration with [MiniPlaceholders]&#40;https://github.com/MiniPlaceholders&#41;.                                                                                            |)

## Basic Examples

First, define the message interface.

```java
@ResourceBundle(baseName = "messages")
public interface ExampleMessages {

    // MessageFormat style
    // {0} will be replaced by the value of the 'playerName' argument at runtime.
    @Key("your_plugin.welcome.message")
    @Message(locale = "en_US", content = "Welcome, {0}!!")
    @Message(locale = "ja_JP", content = "ようこそ、{0}！！")
    TranslatableComponent welcomeMessage(Component playerName);

    /*
     For MiniMessage
     <player_name> will be replaced the value of the 'playerName' argument at runtime.
     @Key("your_plugin.welcome.message")
     @Message(locale = "en_US", content = "Welcome, <player_name>!!")
     @Message(locale = "ja_JP", content = "ようこそ、<player_name>！！")
     TranslatableComponent welcomeMessage(@Name("player_name") Component playerName);
    */
}
```

### Resource Bundle Generation

The `kotonoha-resourcebundle-generator-processor` automatically generates and embeds the following files at compile time:

`messages_en_US.properties`
```properties
your_plugin.welcome.message = Welcome, {0}!!
```

`messages_ja_JP.properties`

```properties
your_plugin.welcome.message = ようこそ、{0}！！
```

### Creating the Message Proxy

Use `kotonoha-translatable-message` to create a proxy instance that implements your interface.

```java
// A proxy instance of ExampleMessages is created, configured to use MessageFormat rules.
ExampleMessages messages = KotonohaMessages.of(ExampleMessages.class, FormatTypes.MESSAGE_FORMAT);

// When the method is called, a TranslatableComponent is created.
TranslatableComponent result = messages.welcomeMessage(player.name());
```

This result component holds only the message key and argument information. When this message is sent to an audience, the translated message will be automatically rendered based on the audience's locale settings, provided that an appropriate `Translator` is registered with `GlobalTranslator`.

More details on Adventure's localization can be found here:
https://docs.papermc.io/adventure/localization/

## Documentation

Javadocs (Available soon)

GitHub Wiki (Available soon)