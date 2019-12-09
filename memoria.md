# Memória

## Leak Canary
TODO

## Android Profiler
TODO

## Leak Patterns

Para análisar as práticas de uso de memória usadas no desenvolvimento de aplicativo usamos os anti-patterns de memória apresentada no artigo [Everything you need to know about Memory Leaks in Android.](https://proandroiddev.com/everything-you-need-to-know-about-memory-leaks-in-android-d7a59faaf46a) apresentado em aula

### Memory leaks por causa de threads não paradas com fim da atividade
Usamos anko para suportar tarefas em background, portanto confiamos que a biblioteca está abstraindo o trabalho encerrar as threads junto com a sua activity.

### Singletons causando memory leaks
A instanciação de singletons usando um contexto diferente do applicationContext pode trazer problemas de memory leak, portanto cuidamos para utilizar sempre o applicationContext invés do context da activity atual. Um exemplo de refatoração para corrigir esse pattern foi: 

* No fragment ClothingSuggestionFragment:

```kotlin
    val db = WeatherDataDB.getDatabase(context!!)
```

foi modificado para:

```kotlin
    val db = WeatherDataDB.getDatabase(activity?.applicationContext!!)
```

### Leaks causados por Listeners
No nosso caso atentamos a esse problema ao cadastrar broadcast receivers na aplicação, e no certificamos que os receivers são removidos da seguinte forma:

Desregistrando todos os listeners ao destruir a activity que os utiliza:

```kotlin
    override fun onDestroy() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(dashboardChangeReceiver)
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(climateInfoChangeReceiver)
        super.onDestroy()
    }
```