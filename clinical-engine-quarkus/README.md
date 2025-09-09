# Clinical Triage Engine (Gemini + Manchester)

Serviço de triagem clínica inteligente (Protocolo de Manchester) integrado ao **Google Gemini** via LangChain4J em Quarkus.

## 🔍 Objetivo
Apoiar (não substituir) o julgamento clínico na classificação de prioridade e geração de recomendações estruturadas.

## 🤖 IA + Regras
- LLM (Gemini) gera análise estruturada
- Regras implícitas reforçadas no prompt (Manchester / sinais vitais / fatores de risco)
- Score de confiança ajustado conforme completude dos dados

## ⚙️ Configuração
```bash
# Chave obrigatória
export GEMINI_API_KEY= SUA_CHAVE
# Execução (dev)
./mvnw quarkus:dev
```

## 🛣️ Endpoint
`POST /api/triage`

### Corpo da requisição (TriageInputParameter)
```json
{
  "patientName": "João Pereira",
  "age": 72,
  "gender": "M",
  "arrivalTime": "2025-08-16T11:55:00",
  "temperature": 38.6,
  "heartRate": 118,
  "respiratoryRate": 24,
  "bloodPressureSystolic": 88,
  "bloodPressureDiastolic": 56,
  "oxygenSaturation": 91.0,
  "painScale": 8,
  "chiefComplaint": "Dor torácica intensa",
  "symptomDescription": "Início súbito, irradiação para braço esquerdo, sudorese",
  "symptomDuration": "40 minutos",
  "symptomProgressionWorsening": true,
  "comorbidities": ["hipertensão", "diabetes tipo 2"],
  "currentMedications": ["losartana", "metformina"],
  "allergies": ["penicilina"],
  "immunosuppressed": false,
  "pregnant": null,
  "gestationalWeek": null,
  "recentSurgery": false,
  "anticoagulantUse": false,
  "additionalNotes": "Chegou em cadeira de rodas, sudorese fria"
}
```
Campos opcionais podem ser omitidos ou null. Febre, taquicardia, hipotensão e baixa oxigenação são inferidos automaticamente a partir dos valores brutos.

#### Segundo exemplo (dor abdominal aguda)
```json
{
  "patientName": "Ana Beatriz Costa",
  "age": 26,
  "gender": "F",
  "arrivalTime": "2025-04-05T11:00:00",
  "chiefComplaint": "Dor abdominal",
  "symptomDescription": "Dor iniciou no umbigo e migrou para o lado direito inferior do abdômen. Associada a náuseas e febre baixa. Piora com movimentos.",
  "symptomDuration": "18 horas",
  "symptomProgressionWorsening": true,
  "temperature": 38.5,
  "heartRate": 96,
  "respiratoryRate": 18,
  "bloodPressureSystolic": 110,
  "bloodPressureDiastolic": 70,
  "oxygenSaturation": 97,
  "painScale": 7.0,
  "comorbidities": [],
  "currentMedications": ["anticoncepcional oral"],
  "allergies": ["nenhuma"],
  "anticoagulantUse": false,
  "immunosuppressed": false,
  "pregnant": false,
  "gestationalWeek": null,
  "recentSurgery": false,
  "additionalNotes": "Paciente com dor à descompressão em FID (fossa ilíaca direita), sem sinais de choque."
}
```
Observação: quadro compatível com suspeita de apendicite; provável classificação Amarelo dependendo da avaliação final do profissional.

### Resposta (TriageOutputPresenter)
```json
{
  "analysisId": "d9c9c6d8-4e2a-4f2e-a7b2-1e61a9b7b111",
  "patientName": "João Pereira",
  "classification": {
    "greenProbability": 0.01,
    "greenJustification": "Quadro não compatível com baixa prioridade",
    "yellowProbability": 0.04,
    "yellowJustification": "Sinais hemodinâmicos instáveis prevalecem",
    "redProbability": 0.95,
    "redJustification": "Dor torácica + hipotensão + taquicardia + dessaturação em idoso",
    "recommendedClassification": "Vermelho",
    "highestProbabilityClass": "Vermelho"
  },
  "preliminaryDiagnosis": "Suspeita de Síndrome Coronariana Aguda",
  "differentialDiagnoses": ["Infarto Agudo do Miocárdio", "Dissecção de Aorta", "Embolia Pulmonar"],
  "clinicalRecommendations": [
    "Monitorização contínua",
    "ECG imediato",
    "Acesso venoso e coleta de marcadores cardíacos",
    "Oxigenioterapia se SpO2 < 94%",
    "Analgesia adequada"
  ],
  "urgencyLevel": "Vermelho",
  "timeRecommendation": "<15 minutos",
  "alertSigns": ["Hipotensão", "Taquicardia", "Baixa oxigenação"],
  "nextSteps": "Avaliação imediata por equipe de emergência/cardiologia",
  "analysisDate": "2025-08-16T12:34:56Z",
  "confidenceScore": 1.0,
  "medicalDisclaimer": "Esta é uma avaliação preliminar para apoio à decisão clínica. O médico responsável deve sempre fazer a avaliação final e tomar as decisões terapêuticas."
}
```

## 📑 Campos Principais
Entrada:
- Sinais vitais: temperature, heartRate, respiratoryRate, bloodPressureSystolic/Diastolic, oxygenSaturation, painScale
- Sintomas: chiefComplaint, symptomDescription, symptomDuration, symptomProgressionWorsening
- Histórico: comorbidities, currentMedications, allergies, immunosuppressed, pregnant, gestationalWeek
- Fatores adicionais: recentSurgery, anticoagulantUse, additionalNotes, arrivalTime

Saída:
- classification (probabilidades e justificativas Verde/Amarelo/Vermelho)
- preliminaryDiagnosis + differentialDiagnoses
- clinicalRecommendations, alertSigns, nextSteps
- urgencyLevel (mapeia com recommendedClassification)
- timeRecommendation, confidenceScore, medicalDisclaimer

## 🧠 Protocolo de Manchester (Resumo)
- Vermelho: risco imediato / instabilidade
- Amarelo: condição potencialmente séria, mas estável
- Verde: queixa menor / sem risco imediato

## 🗂️ Estrutura Simplificada
```
controller/ TriageController
service/    TriageOrchestrationService
model/      TriageInput
parameter/  TriageInputParameter
presenter/  TriageOutputPresenter
gateway/    ClinicalAiService + ClinicalAiGateway (LLM)
dto/        PatientAnalysisResponse, TriageClassification
```

## 🧪 Exemplo de Teste via curl
```bash
curl -X POST http://localhost:8080/api/triage \
  -H "Content-Type: application/json" \
  -d @exemplo-triagem.json
```

## ✅ Boas Práticas de Uso
- Preencher o máximo de sinais vitais disponíveis
- Garantir coerência (ex: gestationalWeek somente se pregnant=true)
- Revisar sempre recomendações antes de ações clínicas

## 🚨 Tratamento de Erros
Retorno 500 inclui mensagem: `Erro ao processar triagem: <detalhe>`

## 📈 Próximos Passos (Ideias)
- Cache de respostas
- Auditoria / histórico
- Ajuste dinâmico de prompt por perfil etário
- Regras explícitas complementares

## ⚠️ Aviso
A saída é suporte à decisão. Decisão final sempre do profissional de saúde responsável.
