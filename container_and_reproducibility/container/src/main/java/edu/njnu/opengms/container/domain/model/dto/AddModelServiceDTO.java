package edu.njnu.opengms.container.domain.model.dto;

import edu.njnu.opengms.common.domain.container.model.ModelService;
import edu.njnu.opengms.common.domain.container.model.support.ModelBehavior;
import edu.njnu.opengms.common.dto.ToDomainConverter;
import lombok.Data;

import java.util.List;

/**
 * @ClassName AddModelServiceDTO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Data
public class AddModelServiceDTO  implements ToDomainConverter<ModelService> {
    String name;
    String description;
    String details;
    String creator;
    List<String> tags;
    String resourceUrl;
    ModelBehavior behavior;
}
