package edu.njnu.opengms.common.domain.container.data.support;

/**
 * @InterfaceName OgcWMService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/17
 * @Version 1.0.0
 */
public interface OgcWMService  extends  OgcService {
    String GetMap();
    String GetFeatureInfo();
}
