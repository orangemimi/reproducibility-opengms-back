package edu.njnu.opengms.container.domain.process;

import edu.njnu.opengms.common.domain.container.process.DataProcessService;
import edu.njnu.opengms.container.domain.process.vo.DataProcessServiceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName DataProcessServiceRepository
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Repository
public interface DataProcessServiceRepository extends MongoRepository<DataProcessService,String> {
    Page<DataProcessServiceVO> getByNameContainsIgnoreCase(String name, Pageable pageable);
}
