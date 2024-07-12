package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
//@RequestMapping("/user")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin loginUser = adminService.login(admin);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @PostMapping("/search")
    public Result findBySearch(@RequestBody Params params) {
        log.info("拦截器放行,查询列表信息");
        if(params.getSex().equals("1")) {
            params.setSex("男");
        } else if(params.getSex().equals("2")){
            params.setSex("女");
        } else {
            params.setSex("");
        }
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        if(admin.getId() == null) {
            adminService.add(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    @PostMapping("/deleteById")
    public Result deleteStudent(@RequestBody Params params) {
        adminService.deleteById(params.getId());
        return Result.success();
    }
}
