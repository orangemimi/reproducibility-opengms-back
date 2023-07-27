
package edu.njnu.opengms.r2.domain.creator;


import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CreatorController
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/7
 * @Version 1.0.0
 */
@RestController
@RequestMapping (value = "/creator")
public class CreatorController {

    @Autowired
    CreatorService creatorService;

    @RequestMapping (value = "/register", method = RequestMethod.POST)
    public JsonResult doRegister(@RequestParam ("name") String name,
                                 @RequestParam ("password") String password
    ) {

        return ResultUtils.success(creatorService.register(name, password));
    }

    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public JsonResult doLogin(@RequestParam ("name") String name,
                              @RequestParam ("password") String password) {
        return ResultUtils.success(creatorService.login(name, password));
    }

    @RequestMapping (value = "/getByName/{name}", method = RequestMethod.GET)
    public JsonResult findVOByName(@PathVariable ("name") String name) {
        return ResultUtils.success(creatorService.findVOByName(name));
    }
}
