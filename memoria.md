# Memória

## Memory Profiler
Ao rodar o aplicativo em um device real com o profiler, percebemos que ao tentar ler um arquivo json de 10mb (arquivo que armazena cidades para a comparação) a aplicação ficava parada. Ao análisar os logs e a utilizar a ferramenta de debug, descobrimos que após rodar a seguinte linha:

```kotlin
    var countriesJSON = JSONArray(countryCodesFile.readText())
```
O aplicativo ficava preso executando o garbage collector, daí percebemos que ao tentar criar um array de jsons com uma quantidade tão grande de elementos, esgotavamos a heap estipulada para o aplicativo em dado device, o que fazia com que o aplicativo ficasse preso em GC. Para resolver isso, decidimos reduzir a lista de cidades, se 
limitando a apenas capitais do mundo e as cidades do Brasil.

## Leak Canary
Ao utilizar o leak canary, ele indicou "Library Leaks", mas ao investigar em conjunto com o Profiler, não detectamos nenhuma anomália no uso da memória, além disso todas a classes que apareceram no trace eram classes de bibliotecas do Android, nos fazendo acreditar que esses casos são leaks na própria API do android.

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
