package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TitleCannotBeNullException - класс исключение
 * Указывает, что название не может быть пустым
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Title cannot be null!")
public class TitleCannotBeNullException extends RuntimeException {

}