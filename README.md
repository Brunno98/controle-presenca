# controle-presenca
Um sistema que gerencia a participação de usuários em atividades.

## Como rodar o projeto?

Na versão atual é necessário habilitar o profile "inMemory".  
Para isso rode o projeto com a seguinte variavel de ambiente: `SPRING_PROFILES_ACTIVE=inMemory`  

Os usuarios de teste são:

| Username | Password |
|----------|----------|
| user     | user     |
| admin    | adin     |

## Exemplo de requests

listar atividades:
```bash
curl --location 'localhost:8080/atividades' \
--header 'Authorization: Basic dXNlcjp1c2Vy'
```

criar atividade:
```bash
curl --location 'localhost:8080/atividades' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic VXNlcjpVc2Vy' \
--data '{
    "descricao": "teste",
    "tempoDeConclusao": 1 // tempo em minutos
}'
```

marcar presença:
```bash
curl --location 'localhost:8080/atividades/presenca' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--data '{
    "atividadeId": "3a672601-9b0c-4e72-a0c7-33ccdf918264"
}'
```