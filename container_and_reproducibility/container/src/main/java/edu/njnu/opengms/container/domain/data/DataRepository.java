package edu.njnu.opengms.container.domain.data;

import edu.njnu.opengms.common.domain.container.data.DataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName DataRepository
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Repository
public interface DataRepository extends MongoRepository<DataService,String> {
    Page<DataService> findAllByDescriptionIsNotNull(Pageable pageable);
}
