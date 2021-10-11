package bigdata.filesystem.config;

import java.lang.annotation.*;

/**
 * 作用在字段上，用于定义类型，映射关系
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {

    FieldType type() default FieldType.TEXT;

    /**
     * 指定分词器
     *
     * @return
     */
    AnalyzerType analyzer() default AnalyzerType.STANDARD;
}
