package com.juzi.springmvc.pojo.entity;

/**
 * Book实体类
 *
 * @author codejuzi
 */
public class Book {
    private String bookId;

    private String bookName;

    public Book() {
    }

    public Book(String bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
