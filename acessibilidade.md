# Acessibilidade
Para acessibilidade utilizamos os guidelines indicados na [Documentação oficial do android](https://developer.android.com/guide/topics/ui/accessibility/apps)

### Melhorar visibilidade dos Textos
Para isso aumentamos os textos para um tamanho fixo para cada componente e também mudamos os tema de cores do aplicativo, aumentando o contraste entre a cor da fonte e dos fundos dos cards

### Usar botões grandes
Aumentamos os botões de navegação do aplicativo para um tamanho que respeite as guidelines definidas

### Descreva os elementos da UI
Para isso adicionamos anotações do que cada componente da UI significa, como por exemplo no componente de navegação:

```xml
<menu
    xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/navigation_dashboard"
        android:title="@string/navigation_dashboard"
        android:icon="@drawable/ic_dashboard"
        android:textSize="48dp"
        android:contentDescription="Dashboard button"
        />

    <item
        android:id="@+id/navigation_alarms"
        android:title="@string/navigation_alarms"
        android:icon="@drawable/ic_alarm"
        android:contentDescription="Alarms button"
        android:textSize="48dp"
        />

</menu>

```
