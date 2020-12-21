package com.surveys.surveys.controller;

import com.surveys.surveys.exception.EmptyDateException;
import com.surveys.surveys.exception.InvalidNumberOfEntriesPerPageException;
import com.surveys.surveys.exception.SurveyNotFoundException;
import com.surveys.surveys.exception.TitleCannotBeNullException;
import com.surveys.surveys.model.Survey;
import com.surveys.surveys.service.SurveyService;
import com.surveys.surveys.util.ActivityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * SurveyController - класс для обработки запросов связанных с опросами (класс Survey)
 * Получает, создает, изменяет, удаляет опросы
 */
@RestController
public class SurveyController {

    @Autowired
    SurveyService service;

    /**
     * Метод, который получает определенное количество
     * отсортированных опросов с возможностью фильтрации по дате
     * и активности
     *
     * @param direction           - направление сортировки (asc, desc)
     * @param sortProperties      - значение сортировки (dateStarting, dateEnding, title)
     * @param numberRecordsOnPage - кол-во записей в странице
     * @param dateFrom            - фильтр по дате "от
     * @param dateTo              - фильтр по дате "до"
     * @param act                 - фильтр по активности
     */
    @GetMapping(value = "/surveys")
    public ResponseEntity<?> getSurveys(String direction, String sortProperties, int numberRecordsOnPage,
                                        String dateFrom, String dateTo, ActivityFilter act) {

        if (numberRecordsOnPage < 1) {
            throw new InvalidNumberOfEntriesPerPageException();
        }
        ArrayList<Survey> surveys = service.sortAndFilter(service.getDirection(direction),
                numberRecordsOnPage, sortProperties, dateFrom, dateTo, act);

        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    /**
     * Метод удаления опроса по id
     *
     * @param surveyId - id опроса
     */
    @DeleteMapping("/surveys/{surveyId}")
    public ResponseEntity<?> deleteSurvey(@PathVariable("surveyId") UUID surveyId) {
        if (service.getSurvey(surveyId) != null) {
            service.deleteSurvey(surveyId);
            return new ResponseEntity<>(surveyId, HttpStatus.OK);
        } else {
            throw new SurveyNotFoundException();
        }
    }

    /**
     * Метод создания нового опроса
     *
     * @param title     - название опроса
     * @param dateStart - дата начала опроса
     * @param dateEnd   - дата окончания опроса
     */
    @PostMapping(value = "/surveys")
    public ResponseEntity<?> addSurvey(String title, String dateStart, String dateEnd) {

        if (title == null || title.equals("")) {
            throw new TitleCannotBeNullException();
        } else if (dateStart == null || dateEnd == null) {
            throw new EmptyDateException();
        }
        service.createSurvey(title, dateStart, dateEnd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Метод изменения опроса
     *
     * @param surveyId  - id существующего опроса
     * @param title     - новое название опроса
     * @param dateStart - новая дата начала опроса
     * @param dateEnd   - навая дата окончания опроса
     */
    @PutMapping(value = "/surveys/{surveyId}")
    public ResponseEntity<?> updateSurvey(@PathVariable("surveyId") UUID surveyId,
                                          String title, String dateStart, String dateEnd) {
        Survey survey = service.getSurvey(surveyId);
        if (survey != null) {
            if (title == null || title.equals("")) {
                throw new TitleCannotBeNullException();
            } else if (dateStart == null || dateEnd == null) {
                throw new EmptyDateException();
            }
            service.updateSurvey(survey, title, dateStart, dateEnd);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new SurveyNotFoundException();
        }
    }
}