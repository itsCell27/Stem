package com.example.stem;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private List<String> choices;
    private int correctAnswerIndex;

    public Question() {
        // Default constructor required for Firebase
        this.questionText = "";
        this.choices = new ArrayList<>();
        this.correctAnswerIndex = -1;
    }

    public Question(String questionText, List<String> choices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

