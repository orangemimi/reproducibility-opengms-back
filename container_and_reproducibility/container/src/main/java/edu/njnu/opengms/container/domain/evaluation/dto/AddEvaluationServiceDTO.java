package edu.njnu.opengms.container.domain.evaluation.dto;

import edu.njnu.opengms.common.domain.container.evaluation.EvaluationService;
import edu.njnu.opengms.common.domain.container.evaluation.support.EvaluationBehavior;
import edu.njnu.opengms.common.dto.ToDomainConverter;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AddEvaluationServiceDTO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Data
public class AddEvaluationServiceDTO implements ToDomainConverter<EvaluationService> {
    String name;
    String description;
    String details;
    List<String> tags;
    String resourceUrl;
    String creator;
    Map<String,String> indicatorInfo;
    EvaluationBehavior behavior;
}
