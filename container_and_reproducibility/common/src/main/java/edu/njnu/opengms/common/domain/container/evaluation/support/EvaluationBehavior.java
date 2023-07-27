package edu.njnu.opengms.common.domain.container.evaluation.support;


import edu.njnu.opengms.common.domain.container.model.support.ModelEvent;
import lombok.Data;

import java.util.List;

/**
 * @ClassName EvaluationBehavior
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/13
 * @Version 1.0.0
 */
@Data
public class EvaluationBehavior {
    List<ModelEvent> inputs;
    String interactiveCode;
}
