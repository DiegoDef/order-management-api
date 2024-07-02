# Order Management API
Este projeto é uma API de gestão de gedidos construída usando Spring Boot. Ela fornece endpoints para gerenciar produtos/serviços, pedidos e itens de pedidos. A API permite realizar ações CRUD com busca filtrada no findAll (atualmente disponível para itens de produto/serviço), além de aplicar descontos nos pedidos.

# Passos iniciais para rodar o projeto
- Abra o terminal na pasta `database` e rode o `docker compose` utilizando `docker compose -f compose.yaml up -d`

## Principais tecnologias utilizadas
- Java 17
- Spring Boot
- PostgreSQL
- JPA
- QueryDSL
- Bean Validation
- REST com JSON
- Lombok
- Docker
- ControllerAdvice

# Explicando alguns endpoints CRUD Item
- É possível fazer o cadastro de um item especificando se é um `servico`, se esta `ativo` entre outros parâmetros. O endpoint é `POST /item`
```shell
{
  {
    "service": true,
    "active": true,
    "name": "Playstation 3",
    "orders": [
         {
             "id": "15140eaa-348d-450b-889a-1ce0bd1b0084"
         }
    ],
    "price": 11
}
}
```

#### Update Item `PUT /item`
```shell
{
  "id": "UUID",
  "name": "string",
  "service": true,
  "price": 10.0,
  "active": true
}

```

#### FindById Item `GET /item/{id}`
```shell
{
        "id": "2d4adadf-6f7f-4497-8c1e-711e286efe66",
        "name": "Playstation 4",
        "service": false,
        "orders": [
            {
                "id": "331bd912-e9dd-478b-8fdd-acab5688cb4a",
                "open": true
            },
            {
                "id": "8ec7aca7-584b-403a-9b5b-1e3905e46032",
                "open": true
            },
            {
                "id": "e3935094-a756-4e9d-8b19-ec2b47382bbe",
                "open": true
            }
        ],
        "price": 10.00,
        "active": true
    }
```

#### FindAll Item `Get /item`
- Neste endpoint é possível enviar o parâmetro `search`, especificando filtros que desejar, utilizando `:`para igual ou contains se for string, `<` para menor que e `>` para maior que, separando os campos e valores por virgula. Parâmetro search não é obrigatório. Alguns exemplos:
  `/item?search=name:"playstation",price<1000,service:false`
  `/item?search=name:"conserto",price<200,service:true`
  `/item?search=name:"conserto",service:true`

```shell
[
    {
        "id": "2d4adadf-6f7f-4497-8c1e-711e286efe66",
        "name": "Playstation 4",
        "service": false,
        "orders": [
            {
                "id": "331bd912-e9dd-478b-8fdd-acab5688cb4a",
                "open": true
            },
            {
                "id": "8ec7aca7-584b-403a-9b5b-1e3905e46032",
                "open": true
            },
            {
                "id": "e3935094-a756-4e9d-8b19-ec2b47382bbe",
                "open": true
            }
        ],
        "price": 10.00,
        "active": true
    },
    {
        "id": "d4527d80-49a6-4007-994b-33401a8e5cac",
        "name": "Playstation 3",
        "service": false,
        "orders": [],
        "price": 10.00,
        "active": true
    },
    {
        "id": "f8213c78-a936-4743-a378-39842fd4b0bf",
        "name": "Playstation 3",
        "service": false,
        "orders": [],
        "price": 10.00,
        "active": true
    }
]
```


#### Delete Item `PUT /item`
- Retorna `no content`
