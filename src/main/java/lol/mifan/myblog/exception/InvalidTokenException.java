package lol.mifan.myblog.exception;

import org.apache.shiro.authc.AccountException;

/**
 * Created by 米饭 on 2017-05-31.
 */
public class InvalidTokenException extends AccountException {

    public InvalidTokenException(){
        super();
    }
    public InvalidTokenException(Throwable t){
        super(t);
    }
    public InvalidTokenException(String msg){
        super(msg);
    }
}