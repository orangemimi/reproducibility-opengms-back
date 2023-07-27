package edu.njnu.opengms.container.domain.instance;

import edu.njnu.opengms.common.domain.container.instance.ServiceInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName ServiceInstaceRepositoyry
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Repository
public interface ServiceInstanceRepository extends MongoRepository<ServiceInstance,String> {
}
