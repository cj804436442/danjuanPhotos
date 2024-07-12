package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Book;
import com.example.entity.BookParams;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Resource
    private BookService bookService;

    @PostMapping("/searchBook")
    public Result findBySearch(@RequestBody BookParams bookParams) {
        PageInfo<Book> info = bookService.findBySearch(bookParams);
        return Result.success(info);
    }

    @PostMapping("/addBook")
    public Result addBook(@RequestBody Book book) {
        if(book.getId() == null) {
            bookService.add(book);
        } else {
            bookService.update(book);
        }
        return Result.success();
    }

}
