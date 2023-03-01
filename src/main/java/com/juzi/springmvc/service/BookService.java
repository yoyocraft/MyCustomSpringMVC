package com.juzi.springmvc.service;

import com.juzi.springmvc.pojo.entity.Book;

import java.util.List;

/**
 * @author codejuzi
 */
public interface BookService {

    /**
     * （模拟）查询所有书籍
     *
     * @return 书籍集合
     */
    List<Book> listBooks();
}
