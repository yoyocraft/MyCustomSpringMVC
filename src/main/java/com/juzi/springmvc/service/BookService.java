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


    /**
     * 根据书名模糊查询书籍
     *
     * @param bookName 书名
     * @return 书籍集合
     */
    List<Book> getBooksByName(String bookName);

    /**
     * 用户登录（测试），仅允许CodeJuzi登录
     *
     * @param userName 用户名
     * @return 登录成功｜ 失败
     */
    boolean login(String userName);
}
