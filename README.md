# Desafio técnico - Engenheiro back-end

Este repositório tem por objetivo apresentar a solução proposta para o desafio técnico Engenheiro back-end

## Dependências externas:

```
$ docker run --name some-mongo -d -p 27017:27017 mongo:4
$ docker run --name some-redis -d -p6379:6379 redis
$ docker run -d --name some-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

## Executar local

```
$ cd /eureka ; ./gradlew bootRun
$ cd /gateway ; ./gradlew  bootRun
$ cd /cashback ; ./gradlew bootRun
$ cd /catalog ; ./gradlew bootrun
$ cd /sales ; ./gradlew bootRun
```
Depois de subir os projetos o API-GATEWAY estará em http://localhost:2808/

## Documentação da API
[Api Publicada no Postman](https://documenter.getpostman.com/view/5722889/S11Bz2v9)

## Decisões arquiteturais

### Decomposição em serviços funcionais

As APIS do projeto foram distribuidas funcionalmente em 3 microserviços, são eles:
* *cashback:* Responsável por armazenar a tabela de cashback proposta no problema
* *catalog:* Responsável por armazenar os produtos 
* *sales:* Responsável pelas vendas.

### Resiliencia e tolerancia a falhas

#### CQRS/ES
Foi usado no SALES uma arquitetura orientada a eventos usando o [Axon Framework](https://axoniq.io/)

[OrderAggregate.java](https://github.com/lucasnascimento/lucashback/blob/master/sales/src/main/java/ln/lucashback/sales/aggregates/OrderAggregate.java)

#### Cache de chamadas entre microserviços usando FEIGN/REDIS
Foi implementado um cache em REDIS cujo o objetivo é armazenar a ultima versão válida dos ojetos de retorno dos serivços CATALOG e CASHBACK, e em caso de indispobilidade o Fallback recupera o dado do cache. 

[FeignClientAspect.java](https://github.com/lucasnascimento/lucashback/blob/master/sales/src/main/java/ln/lucashback/sales/feign/FeignClientAspect.java)

#### Netflix OSS - Eureka e Zull
Foram criados dois projetos auxiliares eureka e gateway (Zull), cujo objetivo é ter um serviço de descoberta de nomes dos serviços e o Zull para balanceamento de carga e ApiGateway dos demais projetos.

# Disclaimer

* Não foi possível criar a integração com a API do Spotify, conceitualmente eu criaria um client Feign para essa integração e um schedule para execução
* Ambiente não foi dockerizado, minha escolha seria criar os serviços com Kubernates
* Ambiente de integração não criado. Usaria o Travis.
