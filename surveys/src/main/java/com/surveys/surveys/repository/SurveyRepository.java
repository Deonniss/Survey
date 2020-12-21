package com.surveys.surveys.repository;

import com.surveys.surveys.model.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * SurveyRepository - интерфейс репозиторий для опросов (Survey)
 */
@Transactional
@Repository
public interface SurveyRepository extends PagingAndSortingRepository<Survey, UUID> {

    /**
     * Метод, возвращающий записи на странице (пагинация)
     *
     * @param pageable       - данные запрошенной страницы
     * @param sortProperties - данные по которым происходит сортировка
     */
    default Page<Survey> findAllPage(Pageable pageable, String sortProperties) {
        return findAllBy(applyDefaultOrder(pageable, sortProperties));
    }

    /**
     * Метод, возвращающий страницу
     *
     * @param pageable
     */
    Page<Survey> findAllBy(Pageable pageable);


    /**
     * Метод, возвращающий отсортированную страницу
     *
     * @param pageable       - данные запрошенной страницы
     * @param sortProperties - данные по которым происходит сортировка
     */
    default Pageable applyDefaultOrder(Pageable pageable, String sortProperties) {
        if (pageable.getSort().isUnsorted()) {
            Sort defaultSort = Sort.by(sortProperties).ascending();
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
        }
        return pageable;
    }

}
