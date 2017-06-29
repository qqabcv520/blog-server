package lol.mifan.myblog.auth;

import lol.mifan.myblog.po.Power;
import lol.mifan.myblog.po.Role;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 米饭 on 2017-06-01.
 */
public class FormRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("参数不能为空");
        }
        String username = (String) getAvailablePrincipal(principals);

        // 根据用户名查询当前用户拥有的角色
        Users user = userService.findByUsername(username);
        Set<String> roleNames = new HashSet<String>();

        // 根据用户名查询当前用户权限
        Set<String> permissionNames = new HashSet<String>();
        for (Role role : user.getRoles()) {
            roleNames.add(role.getName());
            for (Power power : role.getPowers()) {
                permissionNames.add(power.getName());
            }
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissionNames);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken token
     * @return AuthenticationInfo
     * @throws AuthenticationException 抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("usernames不能为空");
        }


        Users user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        return new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getUsername()),//salt=userName
                getName()  //realm name
        );
    }
}