package edu.njnu.opengms.container.domain.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @InterfaceName FileStorageRepository
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
public interface FileStorageRepository extends MongoRepository<FileStorage,String> {
    Optional<FileStorage> findByKey(String key);
}
