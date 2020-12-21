package com.surveys.surveys.controller;

import com.surveys.surveys.exception.NotBelongingSurveyException;
import com.surveys.surveys.exception.QuestionNotFoundException;
import com.surveys.surveys.exception.SurveyNotFoundException;
import com.surveys.surveys.exception.TextCannotBeNullException;
import com.surveys.surveys.model.Question;
import com.surveys.surveys.model.Survey;
import com.surveys.surveys.service.QuestionService;
import com.surveys.surveys.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * QuestionController - класс для обработки запросов связанных с вопросами (класс Question)
 * Создает, изменяет, удаляет вопросы
 */
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    SurveyService surveyService;

    /**
     * Метод создания нового вопроса для опроса
     *
     * @param surveyId - id опроса
     * @param text     - текст вопроса
     */
    @PostMapping("/surveys/{surveyId}")
    public ResponseEntity<?> addQuestion(@PathVariable("surveyId") UUID surveyId, String text) {
        Survey survey = surveyService.getSurvey(surveyId);
        if (survey != null) {
            if (text == null || text.equals(""))
                throw new TextCannotBeNullException();
            questionService.saveQuestion(survey, text);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        throw new SurveyNotFoundException();

    }

    /**
     * Метод изменения вопроса в опросе
     *
     * @param surveyId   - id опроса
     * @param questionId - id вопроса
     * @param text       - новый текст вопроса
     */
    @PutMapping("/surveys/{surveyId}/{questionId}")
    public ResponseEntity<?> updateTask(@PathVariable("surveyId") UUID surveyId,
                                        @PathVariable("questionId") UUID questionId,
                                        String text) {

        Survey survey = surveyService.getSurvey(surveyId);
        Question question = questionService.getQuestion(questionId);
        if (survey != null) {
            if (question != null) {
                if (text == null || text.equals("")) {
                    throw new TextCannotBeNullException();
                }
                if (!survey.getQuestions().contains(question)) {
                    throw new NotBelongingSurveyException();
                }
                questionService.updateQuestion(question, text);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new QuestionNotFoundException();

        }
        throw new SurveyNotFoundException();
    }

    /**
     * Метод, который удаляет вопрос в опросе
     *
     * @param surveyId   - id опроса
     * @param questionId - id вопроса
     */
    @DeleteMapping("/surveys/{surveyId}/{questionId}")
    public ResponseEntity<?> deleteTask(@PathVariable("surveyId") UUID surveyId,
                                        @PathVariable("questionId") UUID questionId) {

        Survey survey = surveyService.getSurvey(surveyId);
        Question question = questionService.getQuestion(questionId);
        if (survey != null) {
            if (question != null) {
                if (!survey.getQuestions().contains(question)) {
                    throw new NotBelongingSurveyException();
                }
                survey.delQuestion(survey.getQuestions().indexOf(question));
                questionService.deleteQuestion(questionId);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new QuestionNotFoundException();
        }
        throw new SurveyNotFoundException();
    }
}
