package edu.njnu.opengms.r2.remote;

import cn.hutool.json.JSONObject;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

/**
 * @ClassName RemoteInstanceService
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/13
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/instance")
public class RemoteInstanceServiceController {
    @Autowired
    ContainerFeign containerFeign;


    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    public JsonResult get(@PathVariable String id) {
        return ResultUtils.success(containerFeign.getInstance(id).getData());
    }

    @RequestMapping (value = "/invoke", method = RequestMethod.POST)
    public JsonResult invoke(@RequestBody JSONObject serviceInstance) {
        String instanceId = (String) ((LinkedHashMap) containerFeign.addInstance(serviceInstance).getData()).get("id");
        return ResultUtils.success(containerFeign.invoke(instanceId).getData());
    }

}
