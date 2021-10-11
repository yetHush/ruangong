package bigdata.filesystem.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @Description: 创建文件类型校验注解
 * @param:
 * @return:
 * @auther: yangqh
 * @date: 2020/12/25/025 18:58
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateFileType {
    String[] fileTypes();

    /**
     * 启用与否
     */
    boolean required() default true;
}