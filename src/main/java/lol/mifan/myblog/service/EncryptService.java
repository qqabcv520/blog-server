package lol.mifan.myblog.service;

/**
 * Created by 米饭 on 2017-05-23.
 */
public interface EncryptService {


    /**
     * 加密
     * @author 范子才
     * @param password 需要加密的内容
     * @param salt 加盐
     * @return 密文
     * @version 2016年4月22日 下午10:52:45
     */
    String encryptPassword(String password, String salt);


    /**
     * 加密
     * @author 范子才
     * @param token 需要加密的内容
     * @return 密文
     * @version 2016年4月22日 下午10:52:45
     */
    String tokenDigest(String token);

    /**
     * 生成随机token
     * @author 范子才
     * @return
     * @version 2016年4月22日 下午10:52:45
     */
    String generateToken();
}
