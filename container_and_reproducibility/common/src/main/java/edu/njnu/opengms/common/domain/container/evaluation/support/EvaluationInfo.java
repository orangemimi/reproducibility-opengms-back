package edu.njnu.opengms.common.domain.container.evaluation.support;


import edu.njnu.opengms.common.domain.container.BaseInfo;
import lombok.Data;

/**
 * @ClassName EvaluationInfo
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/15
 * @Version 1.0.0
 */
@Data
public class EvaluationInfo extends BaseInfo
{
    EvaluationTypeEnum evaluationTypeEnum;
    EvaluationBehavior behavior;
}
