package edu.njnu.opengms.common.domain.container.process.support;


import edu.njnu.opengms.common.domain.container.model.support.ModelEvent;
import edu.njnu.opengms.common.domain.container.model.support.Parameter;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ProcessBehavior
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/9
 * @Version 1.0.0
 */
@Data
public class ProcessBehavior {
    List<ModelEvent> inputs;
    List<ModelEvent> outputs;
    List<Parameter> parameters;
}
