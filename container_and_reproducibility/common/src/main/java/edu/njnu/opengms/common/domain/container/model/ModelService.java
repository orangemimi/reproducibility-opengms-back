package edu.njnu.opengms.common.domain.container.model;


import edu.njnu.opengms.common.domain.container.model.support.ModelInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName ModelService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Document
@Data
public class ModelService extends ModelInfo {
    public String id;
}
