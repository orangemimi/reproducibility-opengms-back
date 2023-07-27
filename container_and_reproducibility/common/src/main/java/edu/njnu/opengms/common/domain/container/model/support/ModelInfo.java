package edu.njnu.opengms.common.domain.container.model.support;


import edu.njnu.opengms.common.domain.container.BaseInfo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ModelInfo
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/15
 * @Version 1.0.0
 */
@Data
public class ModelInfo extends BaseInfo {
    ModelBehavior behavior;
    List<Dependency> dependencies;
    String boundaryCondition;
}
