package com.surveys.surveys.service;

import com.surveys.surveys.model.Question;
import com.surveys.surveys.model.Survey;
import com.surveys.surveys.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс QuestionService - содержит бизнес логику для работы с вопросами
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Метод, который получает вопрос по id
     *
     * @param id - id вопроса
     */
    public Question getQuestion(UUID id) {
        Optional<Question> task = questionRepository.findById(id);
        return task.orElse(null);
    }

    /**
     * Метод, который создает и сохраняет новый вопрос
     *
     * @param survey - Опрос
     * @param text   - текст вопроса
     */
    public void saveQuestion(Survey survey, String text) {
        Question question = new Question();
        question.setText(text);
        question.setDisplayOrder(survey.getQuestions().size());
        question.setSurvey(survey);
        survey.setQuestions(question);
        questionRepository.save(question);
    }

    /**
     * Метод, который изменяет вопрос
     *
     * @param question - вопрос
     * @param text     - текст вопроса
     */
    public Question updateQuestion(Question question, String text) {
        question.setText(text);
        questionRepository.save(question);
        return question;
    }

    /**
     * Метод, который удаление вопрос
     *
     * @param questionId - id вопроса
     */
    public void deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

}