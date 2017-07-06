package lol.mifan.myblog.controller;

import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.exception.HttpException;
import lol.mifan.myblog.service.EncryptService;
import lol.mifan.myblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 米饭 on 2017-05-22.
 */
@RestController
@RequestMapping(value = "/users", produces="application/json;charset=UTF-8")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private Cache tokenCache;

    @Resource
    private EncryptService encryptService;


    @PostMapping(value = "/token", consumes="application/json")
    public Map<String, Object> postToken(@RequestBody Users loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            logger.info("用户" + username + "登录，用户名不存在");
            throw new HttpException(403, "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            logger.info("用户" + username + "登录，密码错误");
            throw new HttpException(401, "密码错误");
        }

        String token = encryptService.generateToken();
        Users user = userService.findByUsername(username);

        user.setLastLoginTime(new Date());//更新最后登录时间
        userService.save(user);

        tokenCache.put(username, token);//缓存token

        logger.info("用户" + username + "登录，登录成功");

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return map;
    }


    @DeleteMapping(value = "/token")
    public void deleteToken() {

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        tokenCache.evict(username);

        subject.logout();

        logger.info("用户" + username + "登出成功");
    }


    @GetMapping(value = "/info")
    public Object info() throws HttpException {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Users user = userService.findByUsername(username);
        return userService.toJsonObj(user);
    }


//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    public Map<String, Object> info(@PathVariable("id")int id) throws HttpException {
//        return userService.get(1);
//    }

//    @RequestMapping(method = RequestMethod.POST)
//    public Map<String, Object> post(@RequestBody Users user) throws HttpException{
//        return userService.add(user);
//    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Map<String, Object> get(@PathVariable("id")int id) throws HttpException {
//        return userService.get(id);
//    }

}
