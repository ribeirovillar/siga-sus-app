package com.app.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("error")
    private String error;

    @JsonProperty("timestamp")
    private String timestamp;

    public ErrorResponse() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public ErrorResponse(String error) {
        this();
        this.error = error;
    }

    // Getters and Setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

class HealthResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("timestamp")
    private String timestamp;

    public HealthResponse() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public HealthResponse(String status) {
        this();
        this.status = status;
    }

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
