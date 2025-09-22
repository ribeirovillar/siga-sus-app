# SIGA-SUS-APP

> Sistema Integrado de Gestão e Análise do SUS - Aplicação de Triagem Hospitalar com IA

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![Quarkus](https://img.shields.io/badge/Quarkus-3.x-blue)
![Docker](https://img.shields.io/badge/Docker-✓-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-✓-blue)
![AI](https://img.shields.io/badge/Google%20Gemini-IA-red)

## 📋 Sobre o Projeto

O SIGA-SUS-APP é uma solução tecnológica inovadora para hospitais e unidades de saúde do Sistema Único de Saúde (SUS), que utiliza **Inteligência Artificial** para auxiliar profissionais de saúde na **triagem de pacientes** baseada no **Protocolo de Manchester**.

### 🎯 Objetivo Principal

Modernizar e otimizar o processo de triagem hospitalar através de:
- **Análise automatizada** de sintomas e sinais vitais
- **Classificação de risco** baseada em evidências clínicas
- **Suporte à decisão** para profissionais de saúde
- **Priorização inteligente** do atendimento

## 🏗️ Arquitetura do Sistema

O sistema é construído com arquitetura de **microserviços**, garantindo escalabilidade e manutenibilidade:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│   Frontend      │────│   API Gateway    │────│  Auth Service       │
│   (Futuro)      │    │  (Spring Boot)   │    │  (Spring Boot)      │
└─────────────────┘    └──────────────────┘    └─────────────────────┘
                                │                         │
                                │                    ┌──────────┐
                                │                    │PostgreSQL│
                                │                    └──────────┘
                                │
                   ┌─────────────────────────┐
                   │ Clinical Engine         │
                   │ (Quarkus + IA)         │
                   │ • Triagem Inteligente   │
                   │ • Google Gemini AI      │
                   │ • Protocolo Manchester  │
                   └─────────────────────────┘
```

### 🧩 Componentes

#### 1. **API Gateway** (Spring Boot)
- **Porta**: 8080
- **Função**: Roteamento central e autenticação
- **Tecnologias**: Spring Cloud Gateway, gRPC
- **Features**:
  - Roteamento inteligente de requisições
  - Validação de tokens JWT
  - Configuração CORS
  - Health checks

#### 2. **Auth Service** (Spring Boot)
- **Porta**: 8081 (HTTP) + 9091 (gRPC)
- **Função**: Autenticação e autorização
- **Tecnologias**: Spring Security, JWT, PostgreSQL, gRPC
- **Features**:
  - Registro e login de usuários
  - Geração e validação de tokens JWT
  - API REST e serviço gRPC
  - Documentação Swagger

#### 3. **Clinical Engine** (Quarkus)
- **Porta**: 8082
- **Função**: Motor de análise clínica com IA
- **Tecnologias**: Quarkus, LangChain4j, Google Gemini AI
- **Features**:
  - Análise inteligente de sintomas
  - Classificação de risco (Protocolo Manchester)
  - Integração com IA Generativa
  - Suporte à decisão clínica

#### 4. **Banco de Dados** (PostgreSQL)
- **Porta**: 5432
- **Função**: Persistência de dados
- **Dados**: Usuários, histórico de triagens, logs

## 🚀 Funcionalidades Principais

### 🏥 Sistema de Triagem Inteligente

#### Classificação de Risco (Protocolo Manchester)
- 🔴 **VERMELHO** (Emergencial): Atendimento imediato (<15 min)
- 🟡 **AMARELO** (Urgente): Avaliação em até 1 hora
- 🟢 **VERDE** (Eletivo): Pode aguardar até 4 horas

#### Dados Analisados
**Informações do Paciente:**
- Nome, idade, sexo
- Comorbidades e medicações
- Histórico de alergias
- Status de gravidez e imunossupressão

**Sinais Vitais:**
- Temperatura, frequência cardíaca
- Pressão arterial, saturação de O2
- Frequência respiratória, escala de dor

**Sintomas e Queixas:**
- Queixa principal e descrição detalhada
- Duração e progressão dos sintomas
- Sinais de alerta específicos

### 🤖 Inteligência Artificial

O sistema utiliza **Google Gemini AI** através do LangChain4j para:
- Análise contextual de dados clínicos
- Geração de hipóteses diagnósticas
- Recomendações de tratamento
- Identificação de sinais de alerta
- Score de confiança da análise

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem principal
- **Spring Boot 3.5.4** - Framework para API Gateway e Auth Service
- **Quarkus** - Framework nativo para Clinical Engine
- **PostgreSQL** - Banco de dados relacional
- **Docker & Docker Compose** - Containerização

### IA e Análise
- **Google Gemini AI** - Motor de IA generativa
- **LangChain4j** - Framework de integração com LLMs
- **gRPC** - Comunicação entre serviços

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Swagger/OpenAPI** - Documentação de APIs
- **Spring Security** - Autenticação e autorização
- **JWT** - Tokens de autenticação

## 🚦 Pré-requisitos

- **Java 21+**
- **Docker & Docker Compose**
- **Maven 3.6+**
- **Chave API do Google Gemini** (para IA)

## 🔧 Instalação e Execução

### 1. Clone o Repositório
```bash
git clone git@github.com:ribeirovillar/siga-sus-app.git
cd siga-sus-app
```

### 2. Configuração da IA (Obrigatório)
```bash
# Edite o docker-compose.yml e substitua "KEY_HERE" pela sua chave do Google Gemini
# Ou configure a variável de ambiente:
export GEMINI_API_KEY="sua_chave_aqui"
```

### 3. Execução com Docker (Recomendado)
```bash
# Inicia todos os serviços
docker-compose up -d

# Verificar logs
docker-compose logs -f
```

### 4. Execução Manual (Desenvolvimento)

#### Terminal 1 - Banco de Dados
```bash
docker run -d --name postgres \
  -e POSTGRES_DB=siga-sus-app \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 postgres:latest
```

#### Terminal 2 - Auth Service
```bash
cd auth-service
./mvnw spring-boot:run
```

#### Terminal 3 - API Gateway
```bash
cd api-gateway
./mvnw spring-boot:run
```

#### Terminal 4 - Clinical Engine
```bash
cd clinical-engine-quarkus
export GEMINI_API_KEY="sua_chave_aqui"
./mvnw quarkus:dev
```

## 📡 APIs e Endpoints

### 🔐 Autenticação (via API Gateway)
```
POST /api/auth/register - Registro de usuário
POST /api/auth/login    - Login de usuário
```

### 🏥 Triagem Clínica (via API Gateway)
```
POST /api/triage - Análise de triagem com IA
```

### 📊 Monitoramento
```
GET  /actuator/health - Status dos serviços
GET  /swagger-ui.html - Documentação interativa
```

## 🔍 Exemplo de Uso

### Registro de Usuário
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "medico01",
    "password": "senha123",
    "email": "medico@hospital.com"
  }'
```

### Análise de Triagem
```bash
curl -X POST http://localhost:8080/api/triage \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "patientName": "João Silva",
    "age": 45,
    "gender": "Masculino",
    "temperature": 38.5,
    "heartRate": 110,
    "bloodPressureSystolic": 85,
    "chiefComplaint": "Dor no peito",
    "symptomDescription": "Dor intensa no peito há 2 horas",
    "symptomDuration": "2 horas"
  }'
```

### Resposta da Triagem
```json
{
  "analysisId": "uuid-123",
  "patientName": "João Silva",
  "preliminaryDiagnosis": "Possível síndrome coronariana aguda",
  "urgencyLevel": "ALTO",
  "timeRecommendation": "IMEDIATO",
  "classification": {
    "recommendedClassification": "VERMELHO",
    "redProbability": 0.85,
    "redJustification": "Dor torácica com sinais vitais alterados"
  },
  "confidenceScore": 0.92,
  "medicalDisclaimer": "Esta análise é apenas um suporte. A decisão final é do médico."
}
```

## 🏥 Protocolo de Manchester Implementado

### Critérios de Classificação

#### 🔴 Vermelho (Emergencial)
- Parada cardiorrespiratória
- Obstrução de via aérea
- Choque/hipotensão severa
- Alteração do nível de consciência
- Dor torácica com instabilidade

#### 🟡 Amarelo (Urgente)  
- Dor torácica moderada/intensa
- Dispneia significativa
- Sinais vitais alterados
- Dor abdominal severa
- Sintomas neurológicos

#### 🟢 Verde (Eletivo)
- Sintomas leves e estáveis
- Consultas de rotina
- Renovação de receitas
- Procedimentos eletivos

## 🧪 Testes e Qualidade

### Executar Testes
```bash
# Testes unitários
./mvnw test

# Testes de integração
./mvnw verify

# Testes do Clinical Engine (Quarkus)
cd clinical-engine-quarkus
./mvnw test
```

### Health Checks
```bash
# Status geral
curl http://localhost:8080/actuator/health

# Auth Service
curl http://localhost:8081/actuator/health

# Clinical Engine  
curl http://localhost:8082/q/health
```

## 🔒 Segurança

### Autenticação e Autorização
- **JWT Tokens** com expiração configurável
- **gRPC** para comunicação segura entre serviços
- **HTTPS** recomendado para produção
- **Validação** de tokens em todas as rotas protegidas

### Configuração de Produção
```bash
# Variáveis de ambiente recomendadas
JWT_SECRET=<secret-forte-256-bits>
POSTGRES_PASSWORD=<senha-segura>
GEMINI_API_KEY=<sua-chave-google>
```

## 🐳 Docker e Produção

### Construir Imagens
```bash
# Construir todos os serviços
docker-compose build

# Construir serviço específico
docker-compose build clinical-engine-quarkus
```

### Produção
```bash
# Produção com SSL e configurações otimizadas
docker-compose -f docker-compose.prod.yml up -d
```

## 📈 Monitoramento e Logs

### Logs dos Serviços
```bash
# Logs em tempo real
docker-compose logs -f

# Logs de um serviço específico
docker-compose logs -f clinical-engine-quarkus
```

### Métricas
- Actuator endpoints para Spring Boot services
- Quarkus health checks para Clinical Engine
- PostgreSQL connection monitoring

## 🤝 Contribuição

### Estrutura de Branches
- `main` - Código de produção
- `develop` - Desenvolvimento ativo  
- `feature/*` - Novas funcionalidades
- `hotfix/*` - Correções urgentes

### Padrões de Código
- **Java 21** features quando aplicável
- **Clean Code** principles
- **SOLID** principles
- Documentação com **Javadoc**
- Testes unitários obrigatórios

## 📄 Licença

Este projeto está sob licença [MIT](LICENSE).

## 👥 Equipe de Desenvolvimento

- **Backend**: Microserviços Spring Boot e Quarkus
- **IA/ML**: Integração Google Gemini via LangChain4j  
- **DevOps**: Docker, Docker Compose
- **Database**: PostgreSQL com JPA/Hibernate

## 📞 Suporte

Para dúvidas e suporte:
- **Issues**: Utilize o sistema de issues do GitHub
- **Documentação**: Swagger UI disponível em cada serviço
- **Logs**: Verifique os logs dos containers para diagnóstico

---

**⚠️ Importante**: Este sistema é uma ferramenta de **apoio à decisão médica** e não substitui o julgamento clínico profissional. Sempre consulte um médico qualificado para decisões finais de tratamento.

**🏥 SIGA-SUS** - Modernizando a saúde pública com tecnologia e inteligência artificial.

