# Kotonoha (言の葉)
 
A localization framework for Java projects built on [Adventure](https://github.com/KyoriPowered/adventure).  
Define multilingual messages as methods on a plain Java interface.
 
```java
public interface ExampleMessages {
 
    @Key("example.welcome")
    @Message(locale = Locales.EN_US, content = "Welcome, {0}!")
    @Message(locale = Locales.JA_JP, content = "ようこそ、{0}!")
    Component welcomeMessage(Component playerName);
}
```
 
```java
KotonohaTranslationStore<MessageFormat> store = KotonohaTranslationStore.messageFormat(name);
store.registerInterface(ExampleMessages.class);
GlobalTranslator.translator().addSource(store);
 
ExampleMessages messages = KotonohaMessage.createProxy(ExampleMessages.class, FormatTypes.MESSAGE_FORMAT);
Componnent message = messages.welcomeMessage(player.displayName());
player.sendMessage(message);
```
 
## Modules
 
| Module | Description |
| --- | --- |
| `kotonoha-annotations` | Common annotations (`@Key`, `@Message`, `@Name`, etc.) |
| `kotonoha-message` | Proxy factory that builds `TranslatableComponent` from interface calls |
| `kotonoha-translator` | Registers translations from annotated interfaces into Adventure's `TranslationStore` |
| `kotonoha-resourcebundle-generator-processor` | Compile-time processor that generates `.properties` files from annotated interfaces |
| `kotonoha-message-extra-miniplaceholders` | [MiniPlaceholders](https://github.com/MiniPlaceholders/MiniPlaceholders) integration |
 
## Documentation
 
[GitHub Wiki](https://github.com/NamiUni/kotonoha/wiki)
 
[Javadoc](https://javadoc.io/doc/io.github.namiuni)
