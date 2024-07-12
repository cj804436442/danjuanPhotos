package com.example.service;

import com.example.dao.BookDao;
import com.example.entity.Admin;
import com.example.entity.Book;
import com.example.entity.BookParams;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookDao bookDao;

    public PageInfo<Book> findBySearch(BookParams bookParams) {
        PageHelper.startPage(bookParams.getPageNum(),bookParams.getPageSize());
        List<Book> list = bookDao.findBySearch(bookParams);
        return PageInfo.of(list);
    }

    public void add(Book book) {
        //用户名必传
        if (book.getName() == null || "".equals(book.getName())) {
            throw new CustomException("用户名不能为空");
        }
        bookDao.insertSelective(book);
    }

    public void update(Book book) {
        bookDao.updateByPrimaryKeySelective(book);
    }

}
