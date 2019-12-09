# Consumo de bateria

## Patterns
Para economizar energia utilizamos dois principais patterns apresentados em [energy-patterns](https://github.com/if710/if710.github.io/blob/master/2019-10-25/energy-patterns.pdf):

### Cache
Para evitar fazer requisições para a API toda vez que o usuário abrir a aplicação, optamos por rodar um Worker em background que de tempos em tempos faz uma coleta os dados das APIs de clima e agrega os dados em uma base de dados na aplicação, portanto não só diminuindo o consumo de energia mas também reduzindo o tempo para o usuário ter os dados disponíveis na tela. Essa abordagem também foi utilizada com a lista de cidades para o componente de comparação de temperatura com outras cidades, baixando esses dados e armazenando em arquivo em na memória do device. 

### Reduzir quantidade de syncs da aplicação
Pela operação de buscar os dados de clima ser uma atividade potencialmente custosa, por utilizar GPS e consumir dados da internet, decidimos aumentar o intervalo entre os syncs de 15 minutos para 30 minutos.

### Reduzir tamanho dos dados transmitidos
Fizemos isso reduzindo e minificando o arquivo que tem os dados das cidades do componente de comparação com cidade que está armazenado remotamente no repositório da aplicação.
