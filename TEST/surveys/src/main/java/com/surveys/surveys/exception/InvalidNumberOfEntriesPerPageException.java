package com.surveys.surveys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidNumberOfEntriesPerPageException - класс исключение
 * Указывает, что количество записей на странице неверно задано
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid number of entries per page. Enter a value of 1 or more!")
public class InvalidNumberOfEntriesPerPageException extends RuntimeException {
}
