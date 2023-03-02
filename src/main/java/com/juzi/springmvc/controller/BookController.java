package com.juzi.springmvc.controller;

import com.juzi.springmvc.core.annotation.*;
import com.juzi.springmvc.pojo.entity.Book;
import com.juzi.springmvc.service.BookService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 测试控制器
 *
 * @author codejuzi
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 测试方法1
     *
     * @param request  request
     * @param response response
     */
    @RequestMapping(value = "/book/list")
    public void listBooks(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");

        try {
            List<Book> bookList = bookService.listBooks();
            parseResponse(response, bookList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试方法2
     *
     * @param request  request
     * @param response response
     * @param bookName book name
     */
    @RequestMapping("/book/get")
    public void getBookByBookName(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(value = "bookName") String bookName) {
        response.setContentType("text/html; charset=utf-8");

        try {
            if (StringUtils.isBlank(bookName)) {
                bookName = "";
            }
            List<Book> bookList = bookService.getBooksByName(bookName);
            parseResponse(response, bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试方法3
     *
     * @param request  request
     * @param response response
     * @param userName user name
     * @return view
     */
    @RequestMapping("/book/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "name") String userName) {
        System.out.println("userName = " + userName);
        boolean isSuccess = bookService.login(userName);
        System.out.println("isSuccess = " + isSuccess);
        request.setAttribute("userName", userName);
        if (isSuccess) {
            return "forward:/login_success.jsp";
        } else {
            return "forward:/login_error.jsp";
        }
    }

    /**
     * 测试方法4
     *
     * @param request  request
     * @param response response
     * @return book list
     */
    @RequestMapping(value = "/book/list/json")
    @ResponseBody
    public List<Book> listBooksByJson(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        return bookService.listBooks();
    }


    private void parseResponse(HttpServletResponse response, List<Book> bookList) throws IOException {
        StringBuilder content = new StringBuilder("<h1>Book List</h1>");
        content.append("<table width='500px' style='border-collapse:collapse border='1px'>");
        for (Book book : bookList) {
            content.append("<tr><td>")
                    .append(book.getBookId())
                    .append("</td><td>")
                    .append(book.getBookName())
                    .append("</td>");
        }
        content.append("</table>");
        PrintWriter writer = response.getWriter();
        writer.write(content.toString());
    }

}
