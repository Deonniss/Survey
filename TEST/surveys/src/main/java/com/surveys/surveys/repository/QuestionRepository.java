package com.surveys.surveys.repository;

import com.surveys.surveys.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;


/**
 * QuestionRepository - интерфейс репозиторий для вопросов (Question)
 */
@Transactional
@Repository
public interface QuestionRepository extends CrudRepository<Question, UUID> {
}
