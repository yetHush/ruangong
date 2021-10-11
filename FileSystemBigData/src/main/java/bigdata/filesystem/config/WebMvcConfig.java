package bigdata.filesystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 解决跨域访问
 * @param:
 * @return:
 * @auther: yangqh
 * @date: 2020/12/19/019 15:36
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public static String[] filter_uri = new String[]{
      "http://127.0.0.1:8080",
//      "http://192.168.31.106:8080",
      "http://localhost:8080"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 1 设置访问源地址
        registry.addMapping("/**")
                .allowedOrigins(filter_uri)
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}

