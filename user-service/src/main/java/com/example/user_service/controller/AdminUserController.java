package com.example.user_service.controller;

import com.example.user_service.service.AdminUserService;
import com.example.user_service.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    ClientUserService clientUserService;



}
