package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TextCannotBeNullException - класс исключение
 * Указывает, что текс вопроса не может быть пустым
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Text cannot be null!")
public class TextCannotBeNullException extends RuntimeException {
}

