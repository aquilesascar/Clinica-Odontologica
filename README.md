# Clinica Odontologica

Projeto com ambiente Docker para subir a aplicacao de autenticacao completa:

- MySQL 8
- Servico de autenticacao em Spring Boot
- Frontend estatico servido por Nginx

## Requisitos

- Docker
- Docker Compose

Nao e necessario instalar Java, Maven, MySQL ou Nginx na maquina. O build do backend baixa as dependencias Maven dentro do container.

## Como Rodar

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Apos os containers subirem:

- Frontend: http://localhost:3000
- API de autenticacao: http://localhost:8081
- MySQL local: `localhost:3307`

Para parar:

```bash
docker compose down
```

Para apagar tambem os dados persistidos do MySQL:

```bash
docker compose down -v
```

## Estrutura

```text
Clinica-Odontologica/
|-- docker-compose.yml
|-- frontend-autenticacao/
|   |-- Dockerfile
|   |-- nginx.conf
|   `-- app.js
|-- servico-autenticacao/
|   |-- Dockerfile
|   `-- servico-autenticacao/
|       |-- pom.xml
|       `-- src/
`-- docs/
```

## Observacoes

O `docker-compose.yml` usa healthcheck no MySQL antes de iniciar o backend. Isso evita falhas comuns em maquinas novas, quando o Spring tenta conectar no banco antes dele estar pronto.
