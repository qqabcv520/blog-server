package lol.mifan.myblog.dao;

import lol.mifan.myblog.po.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 米饭 on 2017-05-26.
 */
public interface UserDao extends JpaRepository<Users, Integer> {

    Users findByUsernameAndDeletedFalse(String username);
}
