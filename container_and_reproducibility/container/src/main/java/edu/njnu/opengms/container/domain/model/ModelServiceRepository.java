package edu.njnu.opengms.container.domain.model;

import edu.njnu.opengms.common.domain.container.model.ModelService;
import edu.njnu.opengms.container.domain.model.vo.ModelServiceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @InterfaceName ModelServiceRepository
 * @Description @Query只能将字段对应的值赋值为null，但是仍然会对外暴露出字段名   @Query (fields="{ 'name' : 1,'description':1,'createTime':1}")
 *              这里使用了VO进行处理
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
public interface ModelServiceRepository extends MongoRepository<ModelService,String> {
    Page<ModelServiceVO> getByNameContainsIgnoreCase(String name, Pageable pageable);
}
