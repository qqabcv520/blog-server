package lol.mifan.myblog.service.impl;

import lol.mifan.myblog.service.EncryptService;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 米饭 on 2017-05-23.
 */
@Service
public class EncryptServiceImpl implements EncryptService {


    @Resource
    private PasswordService passwordService;


    @Override
    public String encryptPassword(String password, String salt) {
        return passwordService.encryptPassword(password);
    }

    @Override
    public String tokenDigest(String token) {
        return messageDigest(token, "SHA-256");
    }

    /**
     * 字符串加密
     * @param strText 需要加密的字符串，加密类型
     * @param strType
     * @return
     */
    protected String messageDigest(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuilder strHexString = new StringBuilder();
                // 遍歷 byte buffer
                for (byte aByteBuffer : byteBuffer) {
                    String hex = Integer.toHexString(0xff & aByteBuffer);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    @Override
    public String generateToken() {
        return messageDigest(String.valueOf(System.currentTimeMillis()), "SHA-256");
//        return passwordService.encryptPassword(String.valueOf(System.currentTimeMillis()));
    }
}
