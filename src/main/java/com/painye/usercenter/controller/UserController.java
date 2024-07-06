package com.painye.usercenter.controller;
import com.painye.usercenter.model.dto.UserRegisterRequest;
import com.painye.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Long userId = null;
        try {
            userId = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword());
        } catch (Exception e) {
            log.error("用户初测失败："+e.getMessage(), e);
        }
        return userId;
    }

}
