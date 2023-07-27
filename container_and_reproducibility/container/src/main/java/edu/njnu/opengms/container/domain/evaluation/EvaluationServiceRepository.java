package edu.njnu.opengms.container.domain.evaluation;

import edu.njnu.opengms.common.domain.container.evaluation.EvaluationService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName EvaluationServiceRepository
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@Repository
public interface EvaluationServiceRepository extends MongoRepository<EvaluationService,String> {
}
