package edu.njnu.opengms.r2.remote;

import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RModelServiceController
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/13
 * @Version 1.0.0
 */
@RestController
@RequestMapping ("/model_service")
public class RemoteModelServiceController {
    @Autowired
    ContainerFeign containerFeign;
    @RequestMapping (value = "", method = RequestMethod.GET)
    public JsonResult list(SplitPageDTO splitPageDTO) {
        return ResultUtils.success(containerFeign.listModelServices(splitPageDTO).getData());
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    public JsonResult get(@PathVariable String id) {
        return ResultUtils.success(containerFeign.getModelService(id).getData());
    }

    @RequestMapping (value = "/all", method = RequestMethod.GET)
    public JsonResult listAll() {
//        System.out.print(containerFeign.listAllDataServices());
        return ResultUtils.success(containerFeign.listAllModelServices());
    }

}
