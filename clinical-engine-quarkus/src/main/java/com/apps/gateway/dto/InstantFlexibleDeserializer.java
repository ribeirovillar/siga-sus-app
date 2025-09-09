package com.apps.gateway.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Desserializa Instant aceitando:
 * - String ISO-8601
 * - Número epoch seconds
 * - Objeto {"seconds": <epochSeconds>} (estilo retornado pelo LLM)
 */
public class InstantFlexibleDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();
        try {
            if (token == JsonToken.VALUE_STRING) {
                String txt = p.getText().trim();
                if (txt.isEmpty()) return Instant.now();
                return Instant.parse(adjustIfMissingZ(txt));
            } else if (token == JsonToken.VALUE_NUMBER_INT || token == JsonToken.VALUE_NUMBER_FLOAT) {
                long epochSeconds = p.getLongValue();
                return Instant.ofEpochSecond(epochSeconds);
            } else if (token == JsonToken.START_OBJECT) {
                JsonNode node = p.readValueAsTree();
                if (node.has("seconds")) {
                    return Instant.ofEpochSecond(node.get("seconds").asLong());
                }
                if (node.has("epochSeconds")) {
                    return Instant.ofEpochSecond(node.get("epochSeconds").asLong());
                }
                if (node.has("iso")) {
                    String iso = node.get("iso").asText();
                    return Instant.parse(adjustIfMissingZ(iso));
                }
            }
        } catch (DateTimeParseException e) {
            // cai para retorno padrão
        }
        // Fallback: agora
        return Instant.now();
    }

    private String adjustIfMissingZ(String iso) {
        // Permite "2025-08-16T12:34:56" sem Z
        if (iso.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}") && !iso.endsWith("Z")) {
            return iso + "Z";
        }
        return iso;
    }
}

