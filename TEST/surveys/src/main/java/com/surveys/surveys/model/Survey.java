package com.surveys.surveys.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Survey - класс сущность опроса
 */
@Entity
@Data
public class Survey {

    /**
     * id опроса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Название опроса
     */
    @NotNull
    private String title;

    /**
     * Дата начала опроса
     */
    @NotNull
    private Date dateStarting;

    /**
     * Дата окончания опроса
     */
    @NotNull
    private Date dateEnding;

    /**
     * Активность опроса
     */
    @NotNull
    private boolean activity;

    /**
     * Список вопросов у опроса
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;

    /**
     * Метод добавления вопроса к опросу
     */
    public void setQuestions(Question question) {
        this.questions.add(question);
    }

    public void delQuestion(int index) {this.questions.remove(index);}
}
