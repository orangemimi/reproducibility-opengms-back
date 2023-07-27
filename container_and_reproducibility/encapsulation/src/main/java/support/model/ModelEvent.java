package support.model;

import lombok.Data;


/**
 * @ClassName ModelEvent
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/18
 * @Version 1.0.0
 */
@Data
public class ModelEvent {
    public String name;
    public Boolean isOptional;
    public String dataServiceId;
}
