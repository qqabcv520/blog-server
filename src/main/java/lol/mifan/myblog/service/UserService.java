package lol.mifan.myblog.service;

import lol.mifan.myblog.po.Users;

import java.util.Map;

/**
 * Created by 米饭 on 2017-05-22.
 */
public interface UserService extends EntityService<Users, Integer> {
    Users findByUsername(String username);

}
