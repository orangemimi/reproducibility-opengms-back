package edu.njnu.opengms.r2.domain.scene.nodes;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ResourceCollect
 * @Description Map<String,String> intermediateDataServices 中间数据，第一个String是服务的name，第二个是数据的id
 * @Author sun_liberzz
 * @Date 2020/4/16
 * @Version 1.0.0
 */
@Data
public class ResourceCollect{
    List<String>  dataServices;
    List<String> modelServices;
    List<String> dataProcessServices;
    Map<String, List<String>> intermediateDataServices;
}
