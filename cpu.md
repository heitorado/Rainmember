# CPU e Perfomance

### Patterns
Para otimizar performance, utilizamos alguns dos principais patterns apresentados em [increasing-performance](https://if710.github.io/2019-10-18/increasing-performance.pdf)

### Working with threads
No Rainmember, fazemos uso de um Worker para coletar os dados de clima através de API, além de fazer uso de threads com a ajuda da biblioteca Anko para não travar a UI em operações mais intensivas como consulta ao DB, por exemplo.

### Managing memory leaks
Seguimos os padrões para evitar e tratar memory leaks em nosso projeto, mais detalhes em [memoria.md](https://github.com/heitorado/Rainmember/blob/master/memoria.md)

### Newer is better
Utilizamos sempre que possível os recursos de APIs mais novas do android, como, por exemplo, RecyclerView, ainda que tentando manter compatível com versões anteriores (nosso projeto tem API mínima como 15). Também utilizamos versões recentes das bibliotecas Room e Anko.

### Don’t use exceptions to control the flow
Como o título diz, não usamos exceptions para controlar o fluxo da aplicação em nenhum lugar do código.

### Use for-each loop instead of for loop
Como o título diz, não utilizamos o for loop, preferindo o for-each sempre que necessário iterar sobre os elementos.

### Use profilers to profile performance
Ao utilizar o aplicativo, análisando os daods de CPU, tivemos o resultado esperado, com picos de CPUs apenas quando o worker está sendo executado e quando é trocada a configuração dos componentes 

### Use libraries
Como já mencionado anteriormente, fizemos uso das bibliotecas Anko e Room.
