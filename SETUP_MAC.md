# AgentDesk AI - Mac Setup Guide

## Prerequisites

Install Homebrew:

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Verify:

```bash
brew --version
```

---

# Git

Install:

```bash
brew install git
```

Verify:

```bash
git --version
```

---

# Node.js

Install:

```bash
brew install node
```

Verify:

```bash
node -v
npm -v
```

---

# Java 21

Install:

```bash
brew install openjdk@21
```

Add to PATH:

```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Verify:

```bash
java -version
```

---

# Maven

Install:

```bash
brew install maven
```

Verify:

```bash
mvn -version
```

---

# PostgreSQL

Install:

```bash
brew install postgresql@17
```

Start:

```bash
brew services start postgresql@17
```

Verify:

```bash
brew services list
```

---

# Create Database

Open PostgreSQL:

```bash
psql postgres
```

Create database:

```sql
CREATE DATABASE agentdesk;
```

Exit:

```sql
\q
```

---

# PGVector

Install:

```bash
brew install pgvector
```

Connect:

```bash
psql -U pratikbehera -d agentdesk
```

Enable extension:

```sql
CREATE EXTENSION vector;
```

Verify:

```sql
SELECT extname FROM pg_extension;
```

---

# Ollama

Install from:

https://ollama.com/download/mac

Verify:

```bash
ollama --version
```

Start Ollama:

```bash
ollama serve
```

---

# Ollama Models

Embedding model:

```bash
ollama pull nomic-embed-text
```

Chat model:

```bash
ollama pull llama3
```

Verify:

```bash
ollama list
```

Expected:

```text
nomic-embed-text
llama3
```

---

# Clone Repository

```bash
git clone <repository-url>
cd agentdesk-ai
```

---

# Backend Setup

Navigate:

```bash
cd backend/agentdesk
```

Run:

```bash
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

---

# Frontend Setup

Navigate:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run:

```bash
npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

---

# Initial User Seed

Current development setup expects user id 1.

Insert:

```sql
INSERT INTO users(email, password, role)
VALUES (
    'admin@agentdesk.com',
    'password',
    'ADMIN'
);
```

Verify:

```sql
SELECT * FROM users;
```

---

# Verification Checklist

```bash
java -version
mvn -version
node -v
npm -v
git --version
ollama list
brew services list
```

---

# Common Issues

## PostgreSQL Connection Refused

Start PostgreSQL:

```bash
brew services start postgresql@17
```

---

## Ollama Model Missing

```bash
ollama pull nomic-embed-text
ollama pull llama3
```

---

## PGVector Missing

```sql
CREATE EXTENSION vector;
```

---

## Frontend Package Not Found

Ensure you are inside the frontend folder:

```bash
cd frontend
npm install
npm run dev
```

---

# Application URLs

Frontend:

```text
http://localhost:5173
```

Backend:

```text
http://localhost:8080
```

PostgreSQL:

```text
localhost:5432
```

Ollama:

```text
http://localhost:11434
```
