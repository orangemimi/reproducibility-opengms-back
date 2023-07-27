package edu.njnu.opengms.r2.domain.scene.nodes;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Computation
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/22
 * @Version 1.0.0
 */
@Data
public class Computation {
    Map<String,List<String>> processInstances;
    Map<String,List<String>> modelInstances;
    Set<String> modelExpectedInstances;
    Set<String> processExpectedInstances;
}
