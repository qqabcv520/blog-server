package lol.mifan.myblog.auth;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Date;

/**
 * Created by 米饭 on 2017-05-04.
 */
public class DigestToken implements AuthenticationToken {

    private String digest;

    private String username;



    private String curTime;

    private String nonce;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCurTime() {
        return curTime;
    }

    public void setCurTime(String curTime) {
        this.curTime = curTime;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.digest;
    }


    @Override
    public String toString() {
        return "DigestToken{" +
                "digest='" + digest + '\'' +
                ", username='" + username + '\'' +
                ", curTime='" + curTime + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}