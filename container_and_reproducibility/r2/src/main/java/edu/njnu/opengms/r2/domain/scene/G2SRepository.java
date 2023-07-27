package edu.njnu.opengms.r2.domain.scene;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName G2SRepositoyry
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Repository
public interface G2SRepository extends MongoRepository<GeographicSimulationScene,String> {
}
