package edu.njnu.opengms.r2.domain.scene.dto;

import edu.njnu.opengms.common.dto.ToDomainConverter;
import edu.njnu.opengms.r2.domain.scene.GeographicSimulationScene;
import lombok.Data;

/**
 * @ClassName AddG2SDTO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Data
public class AddG2SDTO implements ToDomainConverter<GeographicSimulationScene> {
    String name;
    String description;
    String background;
    String goals;
    String creator;
    String snapshot;
}
