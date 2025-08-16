package com.apps.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TriageClassification {
    private double greenProbability;
    private String greenJustification;
    private double yellowProbability;
    private String yellowJustification;
    private double redProbability;
    private String redJustification;
    private String recommendedClassification;
    private String highestProbabilityClass;

    // Getters e Setters
    public double getGreenProbability() {
        return greenProbability;
    }

    public void setGreenProbability(double greenProbability) {
        this.greenProbability = greenProbability;
    }

    public String getGreenJustification() {
        return greenJustification;
    }

    public void setGreenJustification(String greenJustification) {
        this.greenJustification = greenJustification;
    }

    public double getYellowProbability() {
        return yellowProbability;
    }

    public void setYellowProbability(double yellowProbability) {
        this.yellowProbability = yellowProbability;
    }

    public String getYellowJustification() {
        return yellowJustification;
    }

    public void setYellowJustification(String yellowJustification) {
        this.yellowJustification = yellowJustification;
    }

    public double getRedProbability() {
        return redProbability;
    }

    public void setRedProbability(double redProbability) {
        this.redProbability = redProbability;
    }

    public String getRedJustification() {
        return redJustification;
    }

    public void setRedJustification(String redJustification) {
        this.redJustification = redJustification;
    }

    public String getRecommendedClassification() {
        return recommendedClassification;
    }

    public void setRecommendedClassification(String recommendedClassification) {
        this.recommendedClassification = recommendedClassification;
    }

    public String getHighestProbabilityClass() {
        return highestProbabilityClass;
    }

    public void setHighestProbabilityClass(String highestProbabilityClass) {
        this.highestProbabilityClass = highestProbabilityClass;
    }
}