package edu.njnu.opengms.container.domain.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName FileStorage
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@Data
@Document
@Builder
public class FileStorage {
    @Id
    String id;
    String key;
    String name;
    String contentType;
    Long size;
}
