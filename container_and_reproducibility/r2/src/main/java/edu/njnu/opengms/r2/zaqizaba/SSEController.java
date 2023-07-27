package edu.njnu.opengms.r2.zaqizaba;


import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SSEController
 * @Description todo
 * @Author sun_liber
 * @Date 2019/7/17
 * @Version 1.0.0
 */

@RequestMapping ("/sse")
@RestController
public class SSEController {
    @RequestMapping (value = "getdata", produces = "text/event-stream;charset=UTF-8", method = RequestMethod.GET)
    public JsonResult push() {
        return ResultUtils.success(Math.random());
    }
}