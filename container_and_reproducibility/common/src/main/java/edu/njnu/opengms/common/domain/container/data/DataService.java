package edu.njnu.opengms.common.domain.container.data;


import edu.njnu.opengms.common.domain.container.data.support.DataInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName edu.njnu.opengms.container.domain.data.DataService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/17
 * @Version 1.0.0
 */

@Data
@Document
public class DataService extends DataInfo {
    public String id;
    public Boolean isIntermediate;
    public Boolean isReproduced;
    public String instanceName;
    public String geoserverEntrance;
}
