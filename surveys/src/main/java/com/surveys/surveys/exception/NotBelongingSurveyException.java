package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotBelongingSurveyException - класс исключение
 * Указывает, что вопрос не принадлежит опросу
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The question doesn't belong to the survey!")
public class NotBelongingSurveyException extends RuntimeException {}