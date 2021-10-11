package bigdata.filesystem.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class FreeMarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;

    private String backUrl;
    @Value("${backurl}")
    public void setBackUrl(String backUrl){
        this.backUrl = backUrl;
    }

    @PostConstruct
    public void setConfigure() throws Exception {

        configuration.setSharedVariable("backUrl", backUrl);
    }

}