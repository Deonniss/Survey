package com.surveys.surveys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Question - класс сущность вопроса
 */
@Entity
@Data
public class Question {

    /**
     * id вопроса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Текст вопроса
     */
    @NotNull
    private String text;

    /**
     * Порядок отображения вопроса
     */
    @NotNull
    private int displayOrder;

    /**
     * Опрос, которому принадлежит вопрос
     */
    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonIgnore
    private Survey survey;
}
