package edu.njnu.opengms.r2.zaqizaba;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName ClassFromProperties
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/21
 * @Version 1.0.0
 */
@Component
@PropertySource (value = "classpath:application.properties")
@ConfigurationProperties (prefix = "hello")
@Data
public class ClassFromProperties {
    String name;
}
