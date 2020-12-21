package com.surveys.surveys.service;

import com.surveys.surveys.model.Survey;
import com.surveys.surveys.repository.SurveyRepository;
import com.surveys.surveys.util.ActivityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Класс SurveyService - содержит бизнес логику для работы с опросами
 */
@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    /**
     * Метод, который получает опрос по id
     *
     * @param id - id опроса
     */
    public Survey getSurvey(UUID id) {
        Optional<Survey> list = surveyRepository.findById(id);
        return list.orElse(null);
    }

    public ArrayList<Survey> sortAndFilter(Sort.Direction dir, int numberRecordsOnPage, String sortProperties,
                                           String dateFrom, String dateTo, ActivityFilter act) {
        Date from = parseDate(dateFrom);
        Date to = parseDate(dateTo);
        Date now = new Date();

        Pageable sorted = PageRequest.of(0, numberRecordsOnPage, dir, sortProperties);

        Iterable<Survey> surveysIt = surveyRepository.findAllPage(sorted, sortProperties);
        ArrayList<Survey> surveys = new ArrayList<>();

        for (Survey survey : surveysIt) {
            survey.setActivity(survey.getDateStarting().after(now) && survey.getDateEnding().before(now));
            if (survey.getDateStarting().after(from) && survey.getDateStarting().before(to)) {
                switch (act) {
                    case NON:
                        surveys.add(survey);
                        break;
                    case TRUE:
                        if (survey.isActivity()) {
                            surveys.add(survey);
                        }
                        break;
                    case FALSE:
                        if (!survey.isActivity()) {
                            surveys.add(survey);
                        }
                        break;
                }

            }
        }
        return surveys;
    }

    /**
     * Метод, который создает и сохраняет опрос
     *
     * @param title     - название списка
     * @param dateStart - дата начала опроса
     * @param dateEnd   - дата окончания опроса
     */
    public void createSurvey(String title, String dateStart, String dateEnd) {
        Survey survey = new Survey();
        updateSurvey(survey, title, dateStart, dateEnd);
    }

    /**
     * Метод, который изменяет опрос
     *
     * @param survey    - опрос
     * @param title     - название списка
     * @param dateStart - дата начала опроса
     * @param dateEnd   - дата окончания опроса
     */
    public void updateSurvey(Survey survey, String title, String dateStart,
                             String dateEnd) {
        Date start = parseDate(dateStart);
        Date end = parseDate(dateEnd);
        Date now = new Date();
        survey.setTitle(title);
        survey.setDateStarting(start);
        survey.setDateEnding(end);
        survey.setActivity(start.before(now) && end.after(now));
        surveyRepository.save(survey);
    }

    /**
     * Метод, который удаляет опрос по id
     *
     * @param surveyId - id опроса
     */
    public void deleteSurvey(UUID surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    public Sort.Direction getDirection(String direction) {
        if ("desc".equals(direction)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    /**
     * Метод, который парсит строку в дату
     *
     * @param date - строка (дата)
     */
    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy kk:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}