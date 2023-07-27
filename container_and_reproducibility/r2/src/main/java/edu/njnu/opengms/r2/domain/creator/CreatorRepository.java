package edu.njnu.opengms.r2.domain.creator;


import edu.njnu.opengms.r2.domain.creator.vo.CreatorVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @InterfaceName CreatorRepository
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/7
 * @Version 1.0.0
 */
@Repository
public interface CreatorRepository extends MongoRepository<Creator,String> {
    Optional<CreatorVO> findVOByName(String name);
    Optional<Creator> findByName(String name);
}
