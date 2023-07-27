package support.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ModelBehavior
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/9
 * @Version 1.0.0
 */
@Data
public class ModelBehavior {
    List<ModelEvent> inputs;
    List<ModelEvent> outputs;
    List<Parameter> parameters;
}
