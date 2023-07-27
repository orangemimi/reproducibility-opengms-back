package support;

import lombok.Data;

/**
 * @ClassName ServiceInstance
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Data
public class ServiceInstance<T> {
    String id;
    InstanceEnum instanceEnum;
    T service;
}
