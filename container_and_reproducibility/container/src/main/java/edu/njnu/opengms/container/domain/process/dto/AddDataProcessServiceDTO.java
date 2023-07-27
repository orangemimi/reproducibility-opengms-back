package edu.njnu.opengms.container.domain.process.dto;

import edu.njnu.opengms.common.domain.container.process.DataProcessService;
import edu.njnu.opengms.common.domain.container.process.support.ProcessBehavior;
import edu.njnu.opengms.common.dto.ToDomainConverter;
import lombok.Data;

import java.util.List;

/**
 * @ClassName AddDataProcessServiceDTO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Data
public class AddDataProcessServiceDTO  implements ToDomainConverter<DataProcessService> {
    String name;
    String description;
    String details;
    String creator;
    List<String> tags;
    String resourceUrl;
    ProcessBehavior behavior;
}
