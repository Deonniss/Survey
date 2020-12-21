package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * QuestionNotFoundException - класс исключение
 * Указывает, что вопрос не найден по заданному id
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Question not found!")
public class QuestionNotFoundException extends RuntimeException {

}