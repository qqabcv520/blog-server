package lol.mifan.myblog.auth;

import lol.mifan.myblog.po.Power;
import lol.mifan.myblog.po.Role;
import lol.mifan.myblog.po.Users;
import lol.mifan.myblog.service.EncryptService;
import lol.mifan.myblog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.cache.Cache;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 米饭 on 2017-05-04.
 */
public class TokenRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private Cache tokenCache;

    @Resource
    private EncryptService encryptService;


    /**
     * 提供用户信息，返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("参数不能为空");
        }
        String username = (String) getAvailablePrincipal(principals);


        Set<String> roleNames = new HashSet<String>();
        // 根据用户名查询当前用户拥有的角色
        Users user = userService.findByUsername(username);

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
     * 提供账户信息，返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        DigestToken myToken = (DigestToken) token;

        String tokenStr = tokenCache.get(myToken.getUsername(), String.class);

        //抛出账号不存在的异常
        if(tokenStr == null) {
            throw new UnknownAccountException("token不存在");
        }
        return new SimpleAuthenticationInfo(myToken.getUsername(), tokenStr, getName());
    }


    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        DigestToken digestToken = (DigestToken) token;

        String nonce = digestToken.getNonce();
        String curTime = digestToken.getCurTime();
        String digest = digestToken.getDigest();

        String key = (String) info.getCredentials();

        String d = encryptService.tokenDigest(key + nonce + curTime);
        if(!d.equals(digest)) {
            String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
            throw new IncorrectCredentialsException(msg);
        }
    }

}
