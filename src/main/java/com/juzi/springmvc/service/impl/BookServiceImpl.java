package com.juzi.springmvc.service.impl;

import com.juzi.springmvc.core.annotation.Service;
import com.juzi.springmvc.pojo.entity.Book;
import com.juzi.springmvc.service.BookService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codejuzi
 */
@Service
public class BookServiceImpl implements BookService {
    @Override
    public List<Book> listBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("001","《根治猪瘟饕的狗叫》"));
        bookList.add(new Book("002","《根治猪瘟饕的嚣张》"));
        return bookList;
    }
}
