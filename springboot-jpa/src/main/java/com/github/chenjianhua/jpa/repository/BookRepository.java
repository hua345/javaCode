package com.github.chenjianhua.jpa.repository;

import com.github.chenjianhua.common.jpa.support.JdbcQuery;
import com.github.chenjianhua.common.leafjpa.repository.BaseLongRepository;
import com.github.chenjianhua.jpa.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Repository
public interface BookRepository extends BaseLongRepository<Book> {

    /**
     * 根据用户姓名，分页查询用户列表（使用原生SQL语句）
     */
    @Query(value = "SELECT * FROM book WHERE book_name LIKE %:bookName%", nativeQuery = true)
    public Page<Book> jpaPageBookByNameSQL(@Param("bookName") String bookName, Pageable pageable);

    /**
     * 根据用户姓名，分页查询用户列表（使用JPQL语句）
     */
    @Query("SELECT u FROM Book u WHERE u.bookName LIKE %:bookName%")
    public Page<Book> jpaPageBookByNameJPQL(@Param("bookName") String bookName, Pageable pageable);

    default JdbcQuery getFindPageSql(String bookName) {
        Map<String, Object> paramMap = new HashMap<>(8);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM book ");
        if (StringUtils.hasText(bookName)) {
            sb.append("WHERE book_name like :bookName ");
            paramMap.put("bookName", "%" + bookName + "%");
        }
        return new JdbcQuery(sb.toString(), paramMap);
    }
}

