package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmptyDateException - класс исключение
 * Указывает, что дата не может быть пустой
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The date can't be empty!")
public class EmptyDateException extends RuntimeException {
}
