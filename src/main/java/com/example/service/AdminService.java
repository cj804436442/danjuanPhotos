package com.example.service;

import com.example.common.JwtTokenUtils;
import com.example.dao.AdminDao;
import com.example.entity.Params;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public PageInfo<Admin> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        //用户名必传
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        // 判断用户是否存在
        Admin user = adminDao.findByName(admin.getName());
        if (user != null) {
            throw new CustomException("该用户已存在");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
//        adminDao.deleteByPrimaryKey(id);
        adminDao.deleteById(id);
    }

    public Admin login(Admin admin) {
        //用户名必传
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        //密码必传
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        Admin user = adminDao.findByNameAndPassword(admin.getName(),admin.getPassword());
        if(user == null) {
            throw new CustomException("用户名或密码输入错误");
        }
        // 生成该登录用户对应的token跟着user一起返给前端
        String token = JwtTokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }
}
