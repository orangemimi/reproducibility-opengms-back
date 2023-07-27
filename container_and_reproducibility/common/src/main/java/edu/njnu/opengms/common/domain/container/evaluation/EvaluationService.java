package edu.njnu.opengms.common.domain.container.evaluation;


import edu.njnu.opengms.common.domain.container.evaluation.support.EvaluationInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName EvaluationService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Document
@Data
public class EvaluationService extends EvaluationInfo {
    String id;
}
