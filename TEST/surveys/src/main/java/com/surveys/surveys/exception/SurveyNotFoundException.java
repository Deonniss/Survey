package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SurveyNotFoundException - класс исключение
 * Указывает, что опрос не найден по заданному id
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Survey not found!")
public class SurveyNotFoundException extends RuntimeException {

}
