# Finance Tracker - Microservices

Sistema distribuído de monitoramento financeiro desenvolvido com foco em arquitetura de microserviços, mensageria assíncrona e resiliência de dados.

## 🚀 Tecnologias e Versões
* **Linguagem:** Java 21
* **Framework:** Spring Boot 4.0.5
* **Mensageria:** Apache Kafka 4.1.2 (Zookeeper mode)
* **Bancos de Dados:** PostgreSQL 18.3 & Hibernate
* **Containers:** Docker & Docker Compose
* **Gateway:** Spring Cloud Gateway
* **Segurança:** Spring Security & JWT (Resource Server)

## 🏗️ Arquitetura do Projeto

O ecossistema é dividido em serviços especializados para garantir escalabilidade:

1. **API Gateway:** Centraliza as requisições na porta `8080` e distribui para os serviços internos.
2. **Transaction Service:** Gerencia o CRUD de transações financeiras. Ao persistir uma transação no PostgreSQL, ele publica um evento no tópico `transaction.created`.
3. **Notification Service:** Consome os eventos do Kafka de forma assíncrona. Utiliza `ErrorHandlingDeserializer` e suporte a `JSR310` para processar datas (`LocalDate`) e disparar alertas.

## 🛠️ Como Executar

### Pré-requisitos
* Docker e Docker Compose instalados.

### Execução
Na raiz do projeto, onde se encontra o arquivo `docker-compose.yml`, execute:
```bash
docker-compose up --build
