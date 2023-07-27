package edu.njnu.opengms.r2.remote;

import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName RemoteDataServiceController
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/13
 * @Version 1.0.0
 */
@RestController
@RequestMapping ("/data_service")
public class RemoteDataServiceController {
    @Autowired
    ContainerFeign containerFeign;

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    public JsonResult get(@PathVariable String id) {
        return ResultUtils.success(containerFeign.getDataService(id).getData());
    }


    @RequestMapping (value = "", method = RequestMethod.GET)
    public JsonResult list(SplitPageDTO splitPageDTO) {
        return ResultUtils.success(containerFeign.listDataServices(splitPageDTO).getData());
    }

//    @RequestMapping (value = "/all", method = RequestMethod.GET)
//    public JsonResult listAll(SplitPageDTO splitPageDTO) {
//        return ResultUtils.success(containerFeign.listDataServices().getData());
//    }
    @RequestMapping (value = "/all", method = RequestMethod.GET)
    public JsonResult listAll() {
//        System.out.print(containerFeign.listAllDataServices());
        return ResultUtils.success(containerFeign.listAllDataServices());
    }

    @RequestMapping (value = "/addByFile", method = RequestMethod.POST)
    public JsonResult addDataService(@RequestParam ("file") MultipartFile file) {
        try {
            JsonResult jsonResult = containerFeign.addDataServiceByFile(file);
            Object data = jsonResult.getData();
            return ResultUtils.success(data);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    @RequestMapping (value = "/addByFileToPublic", method = RequestMethod.POST)
    public JsonResult addByFileToPublic(@RequestParam ("file") MultipartFile file,
                                        @RequestPart ("name") String name,
//                                        @RequestPart ("resourceUrl") String resourceUrl,
                                        @RequestPart ("description") String description

    ) {
        return ResultUtils.success(containerFeign.addByFileToPublic(file,name,description).getData());
    }



}
