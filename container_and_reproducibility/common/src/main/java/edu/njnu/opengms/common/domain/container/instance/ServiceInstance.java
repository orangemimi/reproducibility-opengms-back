package edu.njnu.opengms.common.domain.container.instance;

import edu.njnu.opengms.common.entity.AgentInfo;
import edu.njnu.opengms.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @ClassName ServiceInstance
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Document
@Data
public class ServiceInstance<T> extends BaseEntity {
    @Id
    String id;
    String name;
    StatusEnum statusEnum;
    InstanceEnum instanceEnum;
    String creator;
    Boolean isReproduced;
//    String type;
    T service;
    String type;
    String dependency;
    List<AgentInfo> agentInfo;
}
