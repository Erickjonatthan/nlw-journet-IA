# Projeto NLW Journey - Java Spring Boot

Este repositório contém o projeto desenvolvido durante o evento NLW Journey, organizado pela Rocketseat. O projeto tem como objetivo demonstrar o conhecimento adquirido em Java Spring Boot e a capacidade de desenvolver uma aplicação completa, desde a configuração do ambiente até a implementação das funcionalidades finais.

## Descrição do Projeto

O projeto consiste em uma aplicação web construída com Java Spring Boot que permite a criação e gerenciamento de viagens. Os usuários podem definir destinos, datas de início e término, e convidar participantes para suas viagens, enviando convites por e-mail.

## Funcionalidades

- Configuração do ambiente de desenvolvimento com Spring Boot
- Implementação de endpoints REST para gerenciar viagens
- Conexão com banco de dados e operações CRUD para viagens

## Features Adicionais
- Adicionar uma validação nos campos de date para que a data de início da viagem seja sempre menor que a data de término e também a data de uma atividade está entre as datas da viagem.
- Extração da core das trips para dentro de uma classe Service
- Mapeamento das exeções para retornar um erro mais amigável para o usuário
- Validação de email para o convite de participantes

## Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Pré-requisitos

- Java 21
- Maven

## Como Executar o Projeto

1. Clone este repositório:
   ```sh
   git clone https://github.com/Erickjonatthan/nlw-journey-JAVA.git
   ```

2. Navegue até o diretório do projeto:
   ```sh
   cd nlw-journey-JAVA
   ```

3. Compile o projeto:
   ```sh
   mvn clean install
   ```

4. Execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```

5. A aplicação estará disponível em `http://localhost:8080`.

## Endpoints

### Endpoints da TripController

1. **Criar uma nova viagem**
   - `POST /trips`
   - **Request Body:**
     ```json
     {
       "destination": "Florianópolis, SC",
       "starts_at": "2024-06-25T21:51:54.7342",
       "ends_at": "2024-06-25T21:51:54.7342",
       "emails_to_invite": ["mayk.brito@rocketseat.com"],
       "owner_name": "Fernanda Kipper",
       "owner_email": "fernanda.kipper@rocketseat.com"
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID"
     }
     ```

2. **Obter detalhes de uma viagem**
   - `GET /trips/{id}`
   - **Response Body:**
     ```json
     {
       "id": "UUID",
       "destination": "Florianópolis, SC",
       "starts_at": "2024-06-25T21:51:54.7342",
       "ends_at": "2024-06-25T21:51:54.7342",
       "emails_to_invite": ["mayk.brito@rocketseat.com"],
       "owner_name": "Fernanda Kipper",
       "owner_email": "fernanda.kipper@rocketseat.com",
       "createdAt": "2024-07-09T12:34:56"
     }
     ```

3. **Atualizar uma viagem**
   - `PUT /trips/{id}`
   - **Request Body:**
     ```json
     {
       "destination": "Florianópolis, SC",
       "starts_at": "2024-06-25T21:51:54.7342",
       "ends_at": "2024-06-25T21:51:54.7342",
       "owner_name": "Fernanda Kipper",
       "owner_email": "fernanda.kipper@rocketseat.com"
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID",
       "destination": "Florianópolis, SC",
       "starts_at": "2024-06-25T21:51:54.7342",
       "ends_at": "2024-06-25T21:51:54.7342",
       "emails_to_invite": ["mayk.brito@rocketseat.com"],
       "owner_name": "Fernanda Kipper",
       "owner_email": "fernanda.kipper@rocketseat.com",
       "createdAt": "2024-07-09T12:34:56"
     }
     ```

4. **Confirmar uma viagem**
   - `GET /trips/{id}/confirm`
   - **Response Body:**
     ```json
     {
       "id": "UUID",
       "isConfirmed": true
     }
     ```

5. **Convidar um participante para a viagem**
   - `POST /trips/{id}/invite`
   - **Request Body:**
     ```json
     {
       "email": "participant@example.com"
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID",
     }
     ```

6. **Obter todos os participantes de uma viagem**
   - `GET /trips/{id}/participants`
   - **Response Body:**
     ```json
     [
       {
         "id": "UUID",
         "email": "participant@example.com",
         "isConfirmed": true
       }
     ]
     ```

7. **Registrar uma atividade na viagem**
   - `POST /trips/{id}/activities`
   - **Request Body:**
     ```json
     {
       "title": "Atividade X",
       "occursAt": "2024-06-25T21:51:54.7342",
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID",
     }

     ```

8. **Obter todas as atividades de uma viagem**
   - `GET /trips/{id}/activities`
   - **Response Body:**
     ```json
     [
       {
         "id": "UUID",
         "title": "Atividade X",
         "description": "Descrição da atividade"
       }
     ]
     ```

9. **Adicionar um link à viagem**
   - `POST /trips/{id}/links`
   - **Request Body:**
     ```json
     {
       "title": "Link X",
       "url": "https://example.com"
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID",
     }
     ```

10. **Obter todos os links de uma viagem**
    - `GET /trips/{id}/links`
    - **Response Body:**
      ```json
      [
        {
          "id": "UUID",
          "title": "Link X",
          "url": "https://example.com"
        }
      ]
      ```

### Endpoints da ParticipantController

1. **Confirmar um participante**
   - `POST /participants/{id}/confirm`
   - **Request Body:**
     ```json
     {
       "name": "Participant Name",
       "email": "email@example.com"
     }
     ```
   - **Response Body:**
     ```json
     {
       "id": "UUID",
     }
     ```

## Autor

- **Erick Santos** - [LinkedIn](https://www.linkedin.com/in/ericksantos/)
- **Rocketseat** - [Site Oficial](https://www.rocketseat.com.br/)