package com.juzi.springmvc.service.impl;

import com.juzi.springmvc.core.annotation.Service;
import com.juzi.springmvc.pojo.entity.Book;
import com.juzi.springmvc.service.BookService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codejuzi
 */
@Service
public class BookServiceImpl implements BookService {

    private static final List<Book> BOOK_LIST = new ArrayList<>();

    static {
        BOOK_LIST.add(new Book("001", "《根治猪瘟饕的狗叫》"));
        BOOK_LIST.add(new Book("002", "《根治猪瘟饕的嚣张》"));
        BOOK_LIST.add(new Book("003", "《根治猪瘟饕的跋扈》"));
        BOOK_LIST.add(new Book("004", "《根治猪瘟饕的猪叫》"));
    }

    @Override
    public List<Book> listBooks() {
        return BOOK_LIST;
    }

    @Override
    public List<Book> getBooksByName(String bookName) {
        List<Book> bookList = new ArrayList<>();
        System.out.println("bookName = " + bookName);
        for (Book book : BOOK_LIST) {
            String bookNameInList = book.getBookName();
            if (StringUtils.isNotBlank(bookNameInList) && StringUtils.isNotBlank(bookName)) {
                if (bookNameInList.contains(bookName)) {
                    bookList.add(book);
                }
            }
        }
        System.out.println("bookList = " + bookList);
        return bookList;
    }
}
