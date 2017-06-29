/**   
 * filename：AuthPassport.java
 *   
 * date：2016年4月17日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.util.annitation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthPassport {
    //String validate() default "";

	String value() default "";
}