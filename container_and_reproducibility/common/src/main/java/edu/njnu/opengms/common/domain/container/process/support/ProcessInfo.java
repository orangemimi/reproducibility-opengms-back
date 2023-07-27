package edu.njnu.opengms.common.domain.container.process.support;


import edu.njnu.opengms.common.domain.container.BaseInfo;
import lombok.Data;

/**
 * @ClassName ProcessInfo
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/15
 * @Version 1.0.0
 */
@Data
public class ProcessInfo extends BaseInfo {
    ProcessBehavior behavior;
}
