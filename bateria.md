# Consumo de bateria

## Battery Profiler
Não detectamos nenhuma anomalia no uso de bateria, com o nível de gasto sempre se mantendo a baixo de "light".

## Patterns
Para economizar energia utilizamos alguns dos principais patterns apresentados em [energy-patterns](https://github.com/if710/if710.github.io/blob/master/2019-10-25/energy-patterns.pdf):

### Cache
Para evitar fazer requisições para a API toda vez que o usuário abrir a aplicação, optamos por rodar um Worker em background que de tempos em tempos faz uma coleta os dados das APIs de clima e agrega os dados em uma base de dados na aplicação, portanto não só diminuindo o consumo de energia mas também reduzindo o tempo para o usuário ter os dados disponíveis na tela. Essa abordagem também foi utilizada com a lista de cidades para o componente de comparação de temperatura com outras cidades, baixando esses dados e armazenando em arquivo em na memória do device. 

### Reduzir quantidade de syncs da aplicação
Pela operação de buscar os dados de clima ser uma atividade potencialmente custosa, por utilizar GPS e consumir dados da internet, decidimos aumentar o intervalo entre os syncs de 15 minutos para 30 minutos.

### Reduzir tamanho dos dados transmitidos
Fizemos isso reduzindo e minificando o arquivo que tem os dados das cidades do componente de comparação com cidade que está armazenado remotamente no repositório da aplicação.

### Tema de UI Escuro
Mudamos o fundo da UI do aplicativo de branco para azul escuro, a fim de diminui a energia gastada para exibir a tela principal do aplicativo na tela.

### Abrir Somente quando Necessário
A feature de notificação tem sua base com o AlarmManager do Android, que, se usado incorretamente, pode drenar facilmente a bateria do dispositivo, visto que ele usa o mecanismo de "wake up", para ativar o dispositivo no horário determinado. Para garantir que isso só ocorra quando necessário, teve-se o cuidado de cadastrar o alarme com repetição apenas para os dias da semana que o usuário requisitou, além de verificar que caso a data/hora já tenha ocorrido na semana, agendar diretamente para a próxima, ao invés de criar e acionar o alarme desnecessariamente (conforme documentação do AlarmManager, ao criar um alarme com data no passado, ele é imediatamente executado).


### Suprimir Logs
Durante o desenvolvimento, naturalmente, fez-se uso extensivo de Logs com finalidade de debugar a aplicação. Para reduzir o consumo de bateria com uma tarefa desnecessária para a experiência do usuário com o aplicativo, refatoramos o APP para remover os logs desnecessários.


### Operações em lote
Como uma única tarefa que realiza tudo uma vez só é melhor do que várias tarefas periódicas, o app confia seu trabalho a um único Worker, que, quando acionado, recupera todos os dados de clima necessários, de uma vez só, para manter todos os componentes do aplicativo atualizados.
