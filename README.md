# controle-presenca
Um sistema que gerencia a participação de usuários em atividades.

## Como rodar o projeto?

Na versão atual é necessário habilitar o profile "inMemory".  
Para isso rode o projeto com a seguinte variavel de ambiente: `SPRING_PROFILES_ACTIVE=inMemory`  
O id de usuário default é 1

## Exemplo de requests

listar atividades:
```bash
curl --location 'localhost:8080/atividades'
```

criar atividade:
```bash
curl --location 'localhost:8080/atividades' \
--header 'Content-Type: application/json' \
--data '{
    "descricao": "teste",
    "tempoDeConclusao": 1 // tempo em minutos
}'
```

marcar presença:
```bash
curl --location 'localhost:8080/atividades/presenca' \
--header 'X-USER-ID: 1' \
--header 'Content-Type: application/json' \
--data '{
    "atividadeId": "cba623a0-2c66-426c-aca3-642d0d94ef9d"
}'
```