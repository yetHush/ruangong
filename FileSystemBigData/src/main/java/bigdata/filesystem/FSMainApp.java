package bigdata.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>http://localhost:8080/swagger-ui.html</P>
 */
@Slf4j
@SpringBootApplication
public class FSMainApp {

    public static void main(String[] args) {
        SpringApplication.run(FSMainApp.class, args);
        log.info("WOW!");
    }
}
