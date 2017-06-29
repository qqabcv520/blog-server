package lol.mifan.myblog.auth;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 米饭 on 2017-06-05.
 */
@Component
public class StatelessAuthcFilter  extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(AccessControlFilter.class);

    private static final String USERNAME_HEADER = "Username";
    private static final String NONCE_HEADER = "Nonce";
    private static final String CUR_TIME_HEADER = "CurTime";
    private static final String DIGEST_HEADER = "Digest";


    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = WebUtils.toHttp(request);

        String username = req.getHeader(USERNAME_HEADER);
        String nonce = req.getHeader(NONCE_HEADER);
        String curTime = req.getHeader(CUR_TIME_HEADER);
        String digest = req.getHeader(DIGEST_HEADER);

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(nonce) ||
                StringUtils.isEmpty(curTime) || StringUtils.isEmpty(digest)) {
//            String msg = "验证失败失败,token不完整";
//            log.info(msg);
//            loginFail(response);
            return true;
        }


        DigestToken digestToken = new DigestToken();
        digestToken.setUsername(username);
        digestToken.setNonce(nonce);
        digestToken.setCurTime(curTime);
        digestToken.setDigest(digest);
        try {
            getSubject(request, response).login(digestToken);
        } catch (Exception e) {
            String msg = "Token登录失败,token:" + digestToken + ", exception:" + e;
            log.info(msg);
            loginFail(response);
            return false;
        }

        return true;
    }

    private void loginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
