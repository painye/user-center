package com.painye.usercenter.controller;

import com.painye.usercenter.common.BaseResponse;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.model.dto.UserLoginRequest;
import com.painye.usercenter.model.dto.UserRegisterRequest;
import com.painye.usercenter.service.UserService;
import com.painye.usercenter.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.controller.UserController
 * @Description : 用户controller层，用来处理用户接口的请求
 * @date 2024/7/6 8:17
 */

@RestController
@RequestMapping("/user")
public class UserController {

    public Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {
        Long userid = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword());
        return ResponseUtils.success(userid);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) throws Exception {
        User user = userService.doLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), session);
        return ResponseUtils.success(user);
    }

    @GetMapping("/search/{userAccount}")
    public BaseResponse<List<User>> userSearch(@PathVariable String userAccount, HttpSession session) throws Exception {
        List<User> users = userService.searchUser(userAccount, session);
        return ResponseUtils.success(users);
    }

    @GetMapping("/revoke/{userId}")
    public BaseResponse userRevoke(@PathVariable Long userId, HttpSession session) throws Exception {
        userService.revokeUser(userId, session);
        return ResponseUtils.success(null);
    }


    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpSession session) throws Exception {
        User user = userService.getCurrentUser(session);
        return ResponseUtils.success(user);
    }

    @GetMapping("/searchAll")
    public BaseResponse<List<User>> searchAll() throws Exception {
        List<User> users = userService.searchAll();
        return ResponseUtils.success(users);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logOut(HttpSession session) {
        boolean flag = userService.logOut(session);
        return ResponseUtils.success(flag);
    }


}
