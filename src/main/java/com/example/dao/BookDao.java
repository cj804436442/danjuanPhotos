package com.example.dao;

import com.example.entity.Admin;
import com.example.entity.Book;
import com.example.entity.BookParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BookDao extends Mapper<Book> {

    List<Book> findBySearch(BookParams bookParams);

    @Select("select * from book where name = #{name} limit 1")
    Admin findByName(@Param("name") String name);
}
