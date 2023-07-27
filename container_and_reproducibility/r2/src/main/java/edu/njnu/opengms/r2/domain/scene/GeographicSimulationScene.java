package edu.njnu.opengms.r2.domain.scene;

import edu.njnu.opengms.common.entity.BaseEntity;
import edu.njnu.opengms.r2.domain.scene.nodes.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName GeographicSimulationScene
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Data
@Document
public class GeographicSimulationScene  extends BaseEntity {
    String id;
    String name;
    String description;
    String background;
    String goals;
    String creator;
    String snapshot;
    Boolean isPublish;
    ContextDefine contextDefine;
    ResourceCollect resourceCollect;
    SimulationConceptGraph simulationConceptGraph;
    Computation computation;
    Evaluation evaluation;


}
