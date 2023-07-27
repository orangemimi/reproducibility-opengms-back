package edu.njnu.opengms.common.domain.container.process;


import edu.njnu.opengms.common.domain.container.process.support.ProcessInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName DataProcessService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/9
 * @Version 1.0.0
 */
@Document
@Data
public class DataProcessService extends ProcessInfo {
    @Id
    String id;
}