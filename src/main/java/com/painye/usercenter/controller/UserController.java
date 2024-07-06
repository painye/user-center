package com.painye.usercenter.controller;
import com.painye.usercenter.constants.Constant;
import com.painye.usercenter.model.domain.User;
import com.painye.usercenter.model.dto.UserLoginRequest;
import com.painye.usercenter.model.dto.UserRegisterRequest;
import com.painye.usercenter.model.vo.UserResponse;
import com.painye.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : com.painye.usercenter.controller.UserController
 * @Description : 用户controller层，用来处理用户接口的请求
 * @author pan
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
            userResponse.setResultMessage("用户注册失败："+e.getMessage());
            log.error("用户初测失败："+e.getMessage(), e);
        }
        return userResponse;
    }

    @PostMapping("/login")
    public UserResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        User user = null;
        UserResponse userResponse = new UserResponse();
        try {
            user = userService.doLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), request);
            userResponse.setResultStatus(Constant.RESULT_STATUS_SUCCESS);
            userResponse.setResultMessage("用户登录成功！");
            Map<String, User> result = new HashMap<>();
            result.put("user", user);
            userResponse.setBody(result);
        } catch (Exception e) {
            userResponse.setResultStatus(Constant.RESULT_STATUS_FAIL);
            userResponse.setResultMessage("用户登录失败："+e.getMessage());
            log.error("用户初测失败："+e.getMessage(), e);
        }
        return userResponse;
    }


}
