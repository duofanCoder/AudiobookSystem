package com.abao.as.repository.business;


import com.abao.as.dto.model.business.BookDto;
import com.abao.as.model.business.Book;
import com.abao.as.model.business.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
public interface ChapterRepository extends PagingAndSortingRepository<Chapter, Long>, CrudRepository<Chapter, Long>, JpaSpecificationExecutor<Chapter> {
    Page<Chapter> findByBookOrderByQueue(Book book, Pageable pageable);

    Optional<Chapter> findByBookAndQueue(Book book, Integer queue);
}
