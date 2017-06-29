package lol.mifan.myblog.util.annitation;

import java.lang.annotation.*;

/**
 * Created by 米饭 on 2017-05-27.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}