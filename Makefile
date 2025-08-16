# SIGA SUS - Makefile para gerenciamento de bancos de dados e serviços
# Uso: make <comando>

# Variáveis
COMPOSE_FILE = docker-compose.yml
PROJECT_NAME = siga-sus-app

# Cores para output
GREEN = \033[0;32m
YELLOW = \033[1;33m
RED = \033[0;31m
NC = \033[0m # No Color

.PHONY: help db-up db-down db-restart db-logs db-status services-up services-down clean build test

# Comando padrão
help: ## Mostra esta ajuda
	@echo "$(GREEN)SIGA SUS - Sistema de Triagem$(NC)"
	@echo "$(YELLOW)Comandos disponíveis:$(NC)"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  $(GREEN)%-20s$(NC) %s\n", $$1, $$2}'

# Comandos para bancos de dados
db-up: ## Sobe apenas os bancos de dados (PostgreSQL, MongoDB, Redis)
	@echo "$(GREEN)🚀 Subindo bancos de dados...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d postgres mongo redis
	@echo "$(GREEN)✅ Bancos de dados iniciados!$(NC)"
	@echo "$(YELLOW)Aguardando inicialização...$(NC)"
	@sleep 10
	@make db-status

db-down: ## Para os bancos de dados
	@echo "$(RED)🛑 Parando bancos de dados...$(NC)"
	docker-compose -f $(COMPOSE_FILE) stop postgres mongo redis
	@echo "$(GREEN)✅ Bancos de dados parados!$(NC)"

db-restart: ## Reinicia os bancos de dados
	@echo "$(YELLOW)🔄 Reiniciando bancos de dados...$(NC)"
	@make db-down
	@sleep 5
	@make db-up

db-logs: ## Mostra logs dos bancos de dados
	@echo "$(GREEN)📋 Logs dos bancos de dados:$(NC)"
	docker-compose -f $(COMPOSE_FILE) logs -f postgres mongo redis

db-status: ## Verifica status dos bancos de dados
	@echo "$(GREEN)📊 Status dos bancos de dados:$(NC)"
	@echo "$(YELLOW)PostgreSQL:$(NC)"
	@docker-compose -f $(COMPOSE_FILE) ps postgres
	@echo "$(YELLOW)MongoDB:$(NC)"
	@docker-compose -f $(COMPOSE_FILE) ps mongo
	@echo "$(YELLOW)Redis:$(NC)"
	@docker-compose -f $(COMPOSE_FILE) ps redis

# Comandos para ferramentas de administração
db-admin: ## Sobe ferramentas de administração (Mongo Express)
	@echo "$(GREEN)🔧 Subindo ferramentas de administração...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d mongo-express
	@echo "$(GREEN)✅ Mongo Express disponível em: http://localhost:8090$(NC)"

# Comandos para todos os serviços
services-up: ## Sobe todos os serviços (bancos + microsserviços)
	@echo "$(GREEN)🚀 Subindo todos os serviços...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d --build
	@echo "$(GREEN)✅ Todos os serviços iniciados!$(NC)"
	@make services-status

services-down: ## Para todos os serviços
	@echo "$(RED)🛑 Parando todos os serviços...$(NC)"
	docker-compose -f $(COMPOSE_FILE) down
	@echo "$(GREEN)✅ Todos os serviços parados!$(NC)"

services-restart: ## Reinicia todos os serviços
	@echo "$(YELLOW)🔄 Reiniciando todos os serviços...$(NC)"
	@make services-down
	@sleep 5
	@make services-up

services-logs: ## Mostra logs de todos os serviços
	@echo "$(GREEN)📋 Logs de todos os serviços:$(NC)"
	docker-compose -f $(COMPOSE_FILE) logs -f

services-status: ## Verifica status de todos os serviços
	@echo "$(GREEN)📊 Status de todos os serviços:$(NC)"
	docker-compose -f $(COMPOSE_FILE) ps

# Comandos de build e teste
build: ## Compila todos os microsserviços
	@echo "$(GREEN)🔨 Compilando microsserviços...$(NC)"
	mvn clean package -DskipTests
	@echo "$(GREEN)✅ Compilação concluída!$(NC)"

test: ## Executa testes de todos os microsserviços
	@echo "$(GREEN)🧪 Executando testes...$(NC)"
	mvn test
	@echo "$(GREEN)✅ Testes concluídos!$(NC)"

# Comandos de limpeza
clean: ## Remove containers, volumes e imagens não utilizados
	@echo "$(RED)🧹 Limpando containers e volumes...$(NC)"
	docker-compose -f $(COMPOSE_FILE) down -v --remove-orphans
	docker system prune -f
	@echo "$(GREEN)✅ Limpeza concluída!$(NC)"

clean-volumes: ## Remove apenas os volumes dos bancos de dados (CUIDADO: apaga dados!)
	@echo "$(RED)⚠️  ATENÇÃO: Isso irá apagar todos os dados dos bancos!$(NC)"
	@read -p "Tem certeza? (y/N): " confirm && [ "$$confirm" = "y" ] || exit 1
	docker-compose -f $(COMPOSE_FILE) down -v
	docker volume rm $(PROJECT_NAME)_postgres_data $(PROJECT_NAME)_mongo_data $(PROJECT_NAME)_redis_data 2>/dev/null || true
	@echo "$(GREEN)✅ Volumes removidos!$(NC)"

# Comandos úteis para desenvolvimento
dev-setup: ## Configuração inicial para desenvolvimento
	@echo "$(GREEN)⚙️  Configurando ambiente de desenvolvimento...$(NC)"
	@make db-up
	@make db-admin
	@echo "$(GREEN)✅ Ambiente pronto para desenvolvimento!$(NC)"
	@echo "$(YELLOW)URLs úteis:$(NC)"
	@echo "  - Mongo Express: http://localhost:8090"
	@echo "  - PostgreSQL: localhost:5432 (user: postgres, pass: postgres, db: siga-sus-app)"
	@echo "  - MongoDB: localhost:27017"
	@echo "  - Redis: localhost:6379"

# Comandos para microsserviços individuais
gateway: ## Sobe apenas o API Gateway (requer bancos rodando)
	@echo "$(GREEN)🚀 Subindo API Gateway...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d api-gateway
	@echo "$(GREEN)✅ API Gateway disponível em: http://localhost:8080$(NC)"

auth: ## Sobe apenas o Auth Service (requer MongoDB)
	@echo "$(GREEN)🚀 Subindo Auth Service...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d auth-service
	@echo "$(GREEN)✅ Auth Service disponível em: http://localhost:8081$(NC)"

users: ## Sube apenas o User Service (requer PostgreSQL)
	@echo "$(GREEN)🚀 Subindo User Service...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d user-service
	@echo "$(GREEN)✅ User Service disponível em: http://localhost:8085$(NC)"

patients: ## Sobe apenas o Patient Record Service (requer PostgreSQL)
	@echo "$(GREEN)🚀 Subindo Patient Record Service...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d patient-record-service
	@echo "$(GREEN)✅ Patient Record Service disponível em: http://localhost:8082$(NC)"

triage: ## Sobe apenas o Triage Orchestrator (requer Redis)
	@echo "$(GREEN)🚀 Subindo Triage Orchestrator...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d triage-orchestrator-service
	@echo "$(GREEN)✅ Triage Orchestrator disponível em: http://localhost:8083$(NC)"

rules: ## Sobe apenas o Clinical Rule Engine
	@echo "$(GREEN)🚀 Subindo Clinical Rule Engine...$(NC)"
	docker-compose -f $(COMPOSE_FILE) up -d clinical-rule-engine-service
	@echo "$(GREEN)✅ Clinical Rule Engine disponível em: http://localhost:8084$(NC)"

# Comandos de monitoramento
health: ## Verifica saúde de todos os serviços
	@echo "$(GREEN)🏥 Verificando saúde dos serviços...$(NC)"
	@echo "$(YELLOW)API Gateway:$(NC)"
	@curl -s http://localhost:8080/actuator/health 2>/dev/null | jq . || echo "❌ Indisponível"
	@echo "$(YELLOW)Auth Service:$(NC)"
	@curl -s http://localhost:8081/api/health 2>/dev/null | jq . || echo "❌ Indisponível"
	@echo "$(YELLOW)User Service:$(NC)"
	@curl -s http://localhost:8085/api/health 2>/dev/null | jq . || echo "❌ Indisponível"
	@echo "$(YELLOW)Patient Record Service:$(NC)"
	@curl -s http://localhost:8082/api/health 2>/dev/null | jq . || echo "❌ Indisponível"
	@echo "$(YELLOW)Triage Orchestrator:$(NC)"
	@curl -s http://localhost:8083/api/health 2>/dev/null | jq . || echo "❌ Indisponível"
	@echo "$(YELLOW)Clinical Rule Engine:$(NC)"
	@curl -s http://localhost:8084/actuator/health 2>/dev/null | jq . || echo "❌ Indisponível"

# Comando de informações
info: ## Mostra informações do projeto
	@echo "$(GREEN)📋 SIGA SUS - Sistema de Triagem$(NC)"
	@echo "$(YELLOW)Arquitetura de Microsserviços:$(NC)"
	@echo "  • API Gateway (8080) - Ponto de entrada único"
	@echo "  • Auth Service (8081) - Autenticação JWT"
	@echo "  • User Service (8085) - Gestão de usuários"
	@echo "  • Patient Record (8082) - Registros de pacientes"
	@echo "  • Triage Orchestrator (8083) - Orquestração de triagem"
	@echo "  • Clinical Rule Engine (8084) - Regras clínicas"
	@echo ""
	@echo "$(YELLOW)Bancos de Dados:$(NC)"
	@echo "  • PostgreSQL (5432) - User Service & Patient Records"
	@echo "  • MongoDB (27017) - Auth Service"
	@echo "  • Redis (6379) - Sessões de triagem"
	@echo ""
	@echo "$(YELLOW)Ferramentas:$(NC)"
	@echo "  • Mongo Express (8090) - Administração MongoDB"
