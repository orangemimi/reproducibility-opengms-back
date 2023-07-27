package edu.njnu.opengms.r2.domain.scene.dto;

import edu.njnu.opengms.common.dto.ToDomainConverter;
import edu.njnu.opengms.r2.domain.scene.GeographicSimulationScene;
import edu.njnu.opengms.r2.domain.scene.nodes.*;
import lombok.Data;

/**
 * @ClassName UpdateG2SDTO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Data
public class UpdateG2SDTO implements ToDomainConverter<GeographicSimulationScene> {
    ContextDefine contextDefine;
    ResourceCollect resourceCollect;
    SimulationConceptGraph simulationConceptGraph;
    Computation computation;
    Evaluation evaluation;
    Boolean isPublish;

    Integer w;
    Integer H;
    String rootXml;

}
