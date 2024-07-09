package com.painye.usercenter.controller;

import com.painye.usercenter.constants.Constant;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.model.dto.UserLoginRequest;
import com.painye.usercenter.model.dto.UserRegisterRequest;
import com.painye.usercenter.model.vo.UserResponse;
import com.painye.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public UserResponse userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Long userId = null;
        UserResponse userResponse = new UserResponse();
        try {
            userId = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword());
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("用户注册成功！");
            Map<String, Long> result = new HashMap<>();
            result.put("userId", userId);
            userResponse.setBody(result);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("用户注册失败：" + e.getMessage());
            log.error("用户注册失败：" + e.getMessage(), e);
        }
        return userResponse;
    }

    @PostMapping("/login")
    public UserResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        UserResponse userResponse = new UserResponse();
        try {
            User user = userService.doLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), session);
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("用户登录成功！");
            Map<String, User> result = new HashMap<>();
            result.put("user", user);
            userResponse.setBody(result);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("用户登录失败：" + e.getMessage());
            log.error("用户登录失败：" + e.getMessage(), e);
        }
        return userResponse;
    }

    @GetMapping("/search/{userAccount}")
    public UserResponse userSearch(@PathVariable String userAccount, HttpSession session) {
        UserResponse userResponse = new UserResponse();
        try {
            List<User> users = userService.searchUser(userAccount, session);
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("用户查询成功！");
            Map<String, List<User>> result = new HashMap<>();
            result.put("userList", users);
            userResponse.setBody(result);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("用户查询失败：" + e.getMessage());
            log.error("用户查询失败：" + e.getMessage(), e);
        }
        return userResponse;
    }

    @GetMapping("/revoke/{userId}")
    public UserResponse userRevoke(@PathVariable Long userId, HttpSession session) {
        UserResponse userResponse = new UserResponse();
        try {
            userService.revokeUser(userId, session);
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("用户注销成功！");
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("用户注销失败：" + e.getMessage());
            log.error("用户注销失败：" + e.getMessage(), e);
        }
        return userResponse;
    }


    @GetMapping("/current")
    public User getCurrentUser(HttpSession session){
        UserResponse userResponse = new UserResponse();
        User user = null;
        try {
            user = userService.getCurrentUser(session);
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("登录用户获取成功！");
            Map<String, User> result = new HashMap<>();
            result.put("user", user);
            userResponse.setBody(result);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("登录用户获取失败：" + e.getMessage());
            log.error("登录用户获取失败：" + e.getMessage(), e);
        }
        return user;
    }

    @GetMapping("/searchAll")
    public List<User> searchAll(){
        UserResponse userResponse = new UserResponse();
        List<User> users = null;
        try {
            users = userService.searchAll();
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("查询所有用户成功！");
            Map<String, List<User>> result = new HashMap<>();
            result.put("user", users);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("查询所有用户失败：" + e.getMessage());
            log.error("查询所有用户失败：" + e.getMessage(), e);
        }
        return users;
    }


}
