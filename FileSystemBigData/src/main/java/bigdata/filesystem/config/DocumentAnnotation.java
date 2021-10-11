package bigdata.filesystem.config;

import java.lang.annotation.*;

/**
 * Es 文档注解，用于做索引实体映射
 * 作用在类上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface DocumentAnnotation {
    /**
     * index : 索引名称
     *
     * @return
     */
    String index();

    /**
     * 类型名称
     *
     * @return
     */
    String type();
}
