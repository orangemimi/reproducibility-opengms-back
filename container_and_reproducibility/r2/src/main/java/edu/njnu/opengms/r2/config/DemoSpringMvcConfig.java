package edu.njnu.opengms.r2.config;


import edu.njnu.opengms.r2.resolver.JwtTokenArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName SpringMVCConfig
 * @Description todo
 * @Author sun_liber
 * @Date 2018/11/14
 * @Version 1.0.0
 */
@Configuration
public class DemoSpringMvcConfig implements WebMvcConfigurer {

    /**
     * @param resolvers
     *
     * @Description 添加参数resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtTokenArgumentResolver());
    }


}
