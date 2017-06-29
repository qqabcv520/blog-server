package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.dao.UserDao;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 米饭 on 2017-05-22.
 */
@Service
public class UserServiceImpl extends EntityServiceImpl<Users, Integer> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Users findByUsername(String username) {
        return userDao.findByUsernameAndDeletedFalse(username);
    }

    @Override
    public Object toJsonObj(Users user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("avatar", user.getAvatar());
        map.put("email", user.getEmail());
        map.put("phone", user.getPhone());
        map.put("username", user.getUsername());
        return map;
    }

    @Override
    public JpaRepository<Users, Integer> getEntityDao() {
        return userDao;
    }
}
