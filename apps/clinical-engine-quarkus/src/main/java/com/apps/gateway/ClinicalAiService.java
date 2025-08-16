package com.apps.gateway;

import com.apps.gateway.dto.PatientAnalysisResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * Serviço de IA especializado em triagem clínica com base no Protocolo de Manchester.
 * Integra com Google Gemini via LangChain4j para análise de risco e suporte à decisão.
 */
@RegisterAiService
public interface ClinicalAiService {

    @SystemMessage("""
        Você é um assistente médico de inteligência artificial especializado em triagem clínica, integrado ao sistema nacional de saúde brasileiro (SIGA-SUS), com base no Protocolo de Manchester de Classificação de Risco.
        
        Seu papel é **apoiar** a equipe de saúde com análises estruturadas, baseadas em evidências, para orientar a priorização do atendimento. Nunca substitua o julgamento clínico do profissional de saúde.
        
        ⚠️ PRINCÍPIOS ÉTICOS E DE SEGURANÇA:
        - Sempre indique que a decisão final é de responsabilidade do médico.
        - Em caso de ambiguidade ou risco potencial, opte pela classificação mais conservadora (maior prioridade).
        - Evite diagnósticos definitivos; forneça hipóteses diagnósticas com base nos dados disponíveis.
        - Respeite a privacidade do paciente: não armazene nem compartilhe dados.
        
        🔴🟠🟢 SISTEMA DE CLASSIFICAÇÃO DE RISCO (PROTOCOLO DE MANCHESTER):
        • 🔴 VERMELHO (Emergencial): Atendimento imediato (<15 minutos). Risco de instabilidade vital ou condição potencialmente fatal.
        • 🟡 AMARELO (Urgente): Avaliação em até 1 hora. Condições graves, mas estáveis no curto prazo.
        • 🟢 VERDE (Eletivo): Pode aguardar até 4 horas. Quadros leves ou crônicos sem risco iminente.
        
        ✅ FATORES A CONSIDERAR:
        - Idade >65 ou <16 → maior risco
        - Comorbidades: diabetes, hipertensão, doenças cardíacas, imunossupressão
        - Uso de anticoagulantes, corticoides, quimioterápicos
        - Alergias medicamentosas
        - Sinais vitais alterados (taquicardia, hipotensão, febre, baixa oxigenação)
        - Sintomas em piora rápida
        - Gestação e idade gestacional
        - Escala de dor
        
        ❗ INSTRUÇÕES FINAIS:
        - O JSON deve ser válido, com chaves entre aspas duplas.
        - Não inclua texto adicional antes ou depois do JSON.
        - O campo "medicalDisclaimer" deve ser exatamente como especificado.
        - O "confidenceScore" reflete a qualidade dos dados: 1.0 = completos, 0.5 = incompletos.
        - Considere TODAS as evidências fornecidas abaixo; se faltarem dados relevantes, reflita isso no confidenceScore.
        """)
    @UserMessage("""
        Realize uma análise clínica completa com base nos dados abaixo. Retorne APENAS o objeto JSON, sem explicações.
        
        PACIENTE:
        - Nome: {name}
        - Idade: {age} anos
        - Sexo: {gender}
        - Comorbidades: {medicalHistory}
        - Medicações em uso: {medications}
        - Alergias: {allergies}
        - Imunossuprimido: {immunosuppressed}
        - Gestante: {pregnant}
        - Semana gestacional: {gestationalWeek}
        
        SINAIS VITAIS:
        - Temperatura (°C): {temperature}
        - FC (bpm): {heartRate}
        - FR (irpm): {respiratoryRate}
        - PA Sistólica (mmHg): {bloodPressureSystolic}
        - PA Diastólica (mmHg): {bloodPressureDiastolic}
        - Saturação O2 (%): {oxygenSaturation}
        - Escala de dor (0-10): {painScale}
        
        SINTOMAS:
        - Queixa principal: {chiefComplaint}
        - Descrição: {symptomDescription}
        - Duração: {symptomDuration}
        - Piora recente: {symptomProgressionWorsening}
        - Resumo formatado: {symptoms}
        
        FATORES / SINAIS DE ALERTA:
        - Cirurgia recente: {recentSurgery}
        - Uso de anticoagulante: {anticoagulantUse}
        - Febre: {fever}
        - Alteração do estado mental: {alteredMentalStatus}
        - Dor torácica: {chestPain}
        - Dispneia: {dyspnea}
        - Hipotensão: {hypotension}
        - Taquicardia: {tachycardia}
        - Bradicardia: {bradycardia}
        - Baixa oxigenação: {lowOxygen}
        
        OBSERVAÇÕES ADICIONAIS:
        {additionalNotes}
        
        ⚠️ IMPORTANTE:
        - Use julgamento clínico conservador em caso de dúvida.
        - Retorne apenas o JSON.
        """)
    PatientAnalysisResponse analyzePatient(
        String name,
        int age,
        String gender,
        String medicalHistory,
        String medications,
        String allergies,
        boolean immunosuppressed,
        Boolean pregnant,
        Integer gestationalWeek,
        Double temperature,
        Integer heartRate,
        Integer respiratoryRate,
        Double bloodPressureSystolic,
        Double bloodPressureDiastolic,
        Double oxygenSaturation,
        Double painScale,
        String chiefComplaint,
        String symptomDescription,
        String symptomDuration,
        boolean symptomProgressionWorsening,
        String symptoms,
        boolean recentSurgery,
        boolean anticoagulantUse,
        boolean fever,
        boolean alteredMentalStatus,
        boolean chestPain,
        boolean dyspnea,
        boolean hypotension,
        boolean tachycardia,
        boolean bradycardia,
        boolean lowOxygen,
        String additionalNotes
    );


}