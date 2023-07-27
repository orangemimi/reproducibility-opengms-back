package edu.njnu.opengms.r2.domain.scene.nodes;

import edu.njnu.opengms.r2.domain.scene.support.Boundary;
import edu.njnu.opengms.r2.domain.scene.support.GeoObject;
import edu.njnu.opengms.r2.domain.scene.support.Theme;
import lombok.Data;

/**
 * @ClassName ContextDefine
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/16
 * @Version 1.0.0
 */
@Data
public class ContextDefine  {
    Theme theme;
    GeoObject object;
    Boundary boundary;
}
