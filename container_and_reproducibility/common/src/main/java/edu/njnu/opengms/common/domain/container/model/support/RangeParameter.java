package edu.njnu.opengms.common.domain.container.model.support;

import lombok.Data;

/**
 * @ClassName RangeParameter
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/18
 * @Version 1.0.0
 */
@Data
public class RangeParameter extends Parameter {
    public String from;
    public String to;
    public String defaultValue;

}
