package edu.njnu.opengms.common.domain.container.data.support;

/**
 * @InterfaceName OgcWFService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/17
 * @Version 1.0.0
 */
public interface OgcWFService  extends  OgcService{
    String DescribeFeatureType();
    String GetFeature();
}
