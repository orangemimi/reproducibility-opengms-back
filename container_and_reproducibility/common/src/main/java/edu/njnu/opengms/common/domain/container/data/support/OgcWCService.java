package edu.njnu.opengms.common.domain.container.data.support;

/**
 * @InterfaceName OgcWCService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/17
 * @Version 1.0.0
 */
public interface OgcWCService  extends  OgcService {
     String DescribeCoverage();
     String GetCoverage();
}
