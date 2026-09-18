# 📚 Digiloteca — Biblioteca Comunitária Digital

A Digiloteca é uma aplicação web desenvolvida para apoiar uma biblioteca
comunitária, facilitando o acesso ao acervo, reservas de livros, registro
de doações e divulgação de eventos.

O projeto foi desenvolvido como uma aplicação full stack, com backend em
Spring Boot, frontend em React e persistência em PostgreSQL.

---

## 🎯 Objetivo

O objetivo da Digiloteca é apoiar a gestão e utilização de uma biblioteca
comunitária, permitindo que membros da comunidade consultem o acervo,
reservem livros, registrem doações e acompanhem eventos promovidos pela
biblioteca.

---

## ✨ Funcionalidades

### Usuários

- Cadastro de usuário
- Login básico
- Persistência do usuário logado no navegador
- Logout

### Livros e exemplares

- Cadastro de livros
- Cadastro de exemplares físicos
- Consulta ao catálogo
- Consulta de detalhes do livro
- Controle de disponibilidade dos exemplares

### Reservas

- Reserva de livros
- Seleção automática de exemplar disponível
- Associação da reserva ao usuário logado
- Alteração automática do exemplar para `RESERVADO`

### Empréstimos

- Criação de empréstimo a partir de reserva
- Reserva alterada para `FINALIZADA`
- Exemplar alterado para `EMPRESTADO`
- Registro de devolução
- Retorno automático do exemplar para `DISPONIVEL`

### Doações

- Registro de doações
- Associação ao usuário logado
- Fluxo de status:

`PENDENTE → APROVADA → RECEBIDA`

ou

`PENDENTE → RECUSADA`

A incorporação de uma doação ao catálogo é realizada separadamente,
permitindo utilizar um livro existente ou cadastrar um novo livro.

### Eventos

- Cadastro de eventos
- Listagem de eventos
- Consulta dos próximos eventos
- Exibição dos eventos na página inicial

---

## 🛠 Tecnologias

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation
- BCrypt
- Maven

### Banco de dados

- PostgreSQL
- H2 para testes automatizados

### Frontend

- React
- Vite
- React Router
- JavaScript
- CSS

### Infraestrutura

- Docker
- Docker Compose
- Render
- PostgreSQL em nuvem

---

## 🏗 Arquitetura

O backend segue uma separação em camadas:

Controller
↓
Service
↓
Repository
↓
JPA / Hibernate
↓
PostgreSQL

Os DTOs são utilizados para separar os dados recebidos e enviados pela API
das entidades utilizadas internamente pela aplicação.

O frontend consome a API REST utilizando `fetch`.

---

## 📁 Estrutura principal

```text
src/main/java/dev/isaac/digiloteca
├── config
├── controller
├── dto
├── enums
├── exception
├── model
├── repository
└── service

┌─────────────────┐
│   React / Vite  │
│    Frontend     │
└────────┬────────┘
         │ REST / JSON
         ▼
┌─────────────────┐
│   Spring Boot   │
│      API        │
├─────────────────┤
│ Controllers     │
│ Services        │
│ Repositories    │
└────────┬────────┘
         │ JPA
         ▼
┌─────────────────┐
│   PostgreSQL    │
└─────────────────┘