package edu.njnu.opengms.common.domain.container.data.support;


import edu.njnu.opengms.common.domain.container.BaseInfo;
import lombok.Data;


/**
 * @ClassName DataInfo
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/15
 * @Version 1.0.0
 */
@Data
public class DataInfo extends BaseInfo {
    String schemaPath;
    String key;
    String suffix;
    String value;
}
