package edu.njnu.opengms.container.domain.evaluation.vo;

import edu.njnu.opengms.common.domain.container.evaluation.support.EvaluationTypeEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName EvaluationServiceVO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/13
 * @Version 1.0.0
 */
@Data
public class EvaluationServiceVO {
    public String name;
    public String description;
    public List<String> tags;
    public String creator;
    public Date createTime;
    String id;
    EvaluationTypeEnum evaluationTypeEnum;
}
