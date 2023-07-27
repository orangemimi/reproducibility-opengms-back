package edu.njnu.opengms.r2.remote;

import cn.hutool.json.JSONObject;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.utils.JsonResult;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @InterfaceName ContainerFeign
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@FeignClient (name = "container",
        url = "http://127.0.0.1:8081",
        fallbackFactory = ContainerFeign.FeignServiceFallBack.class)
@Primary
public interface ContainerFeign {

    @RequestMapping (value = "/model_service",method = RequestMethod.GET)
    JsonResult listModelServices(@SpringQueryMap  SplitPageDTO splitPageDTO);

    @RequestMapping (value = "/data_process_service",method = RequestMethod.GET)
    JsonResult listDataProcessServices(@SpringQueryMap  SplitPageDTO splitPageDTO);


    @RequestMapping (value = "/data_service",method = RequestMethod.GET)
    JsonResult listDataServices(@SpringQueryMap  SplitPageDTO splitPageDTO);

    @RequestMapping (value = "/data_service/all",method = RequestMethod.GET)
    JsonResult listAllDataServices();

    @RequestMapping (value = "/model_service/all",method = RequestMethod.GET)
    JsonResult listAllModelServices();

    @RequestMapping (value = "/model_service/{id}",method = RequestMethod.GET)
    JsonResult getModelService(@PathVariable String id);

    @RequestMapping (value = "/data_process_service/{id}",method = RequestMethod.GET)
    JsonResult getDataProcessService(@PathVariable String id);

    @RequestMapping (value = "/data_service/{id}",method = RequestMethod.GET)
    JsonResult getDataService(@PathVariable String id);


    @RequestMapping (value = "/instance",method = RequestMethod.POST)
    JsonResult addInstance(@RequestBody JSONObject serviceInstance);

    @RequestMapping (value = "/instance/{id}",method = RequestMethod.GET)
    JsonResult getInstance(@PathVariable("id")String id);

    @RequestMapping (value = "/instance/{id}/invoke", method = RequestMethod.POST)
    JsonResult invoke(@PathVariable ("id") String id);

    @RequestMapping (value = "/data_service/addByFile",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    JsonResult addDataServiceByFile(@RequestPart ("file") MultipartFile file);


    @RequestMapping (value = "/data_service/addByFileToPublic",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    JsonResult addByFileToPublic(@RequestPart ("file") MultipartFile file,
                                 @RequestPart ("name") String name,
//                                 @RequestPart ("resourceUrl") String resourceUrl,
                                 @RequestPart ("description") String description);

    @RequestMapping (value = "/evaluation_service",method = RequestMethod.POST)
    JsonResult createEvaluationService(@RequestBody JSONObject evaluationService);

    @RequestMapping (value = "/instance/listByIds",method = RequestMethod.GET)
    JsonResult listInstanceByIds(@RequestParam ("ids") List<String> id);

    @RequestMapping (value = "/instance/{id}/getOutputs",method = RequestMethod.GET)
    JsonResult getInstanceOutputs(@PathVariable ("id") String id);

    @RequestMapping (value = "/evaluation_service/{id}",method = RequestMethod.GET)
    JsonResult getEvaluationServiceById(@PathVariable ("id") String id);


    @RequestMapping (value = "/model_service/listByIds", method = RequestMethod.GET)
    JsonResult listModelServiceByIds(@RequestParam ("ids") List<String> ids);

    @RequestMapping (value = "/data_process_service/listByIds", method = RequestMethod.GET)
    JsonResult listDataProcessServiceByIds(@RequestParam ("ids") List<String> ids);


    @RequestMapping (value = "/model_service/listVOByIds", method = RequestMethod.GET)
    JsonResult listModelServiceVOByIds(@RequestParam ("ids") List<String> ids);

    @RequestMapping (value = "/data_process_service/listVOByIds", method = RequestMethod.GET)
    JsonResult listDataProcessServiceVOByIds(@RequestParam ("ids") List<String> ids);

    @RequestMapping (value = "/evaluation_service/listVOByIds", method = RequestMethod.GET)
    JsonResult listEvaluationServicesVO(@RequestParam ("ids") List<String> evaluationServices);

    @RequestMapping (value = "/data_service/listByIds", method = RequestMethod.GET)
    JsonResult listDataServiceByIds(@RequestParam ("ids") List<String> id);




    @Component
    class FeignServiceFallBack implements FallbackFactory<ContainerFeign> {
        @Override
        public ContainerFeign create(Throwable cause) {
            return new ContainerFeign() {
                @Override
                public JsonResult listModelServices(SplitPageDTO splitPageDTO) {
                    return null;
                }

                @Override
                public JsonResult listDataProcessServices(SplitPageDTO splitPageDTO) {
                    return null;
                }

                @Override
                public JsonResult listDataServices(SplitPageDTO splitPageDTO) {
                    return null;
                }

                @Override
                public JsonResult listAllDataServices() {
                    return null;
                }

                @Override
                public JsonResult listAllModelServices() {
                    return null;
                }

                @Override
                public JsonResult getModelService(String id) {
                    return null;
                }

                @Override
                public JsonResult getDataProcessService(String id) {
                    return null;
                }

                @Override
                public JsonResult getDataService(String id) {
                    return null;
                }

                @Override
                public JsonResult addInstance(JSONObject serviceInstance) {
                    System.out.println(cause.toString());
                    return null;
                }

                @Override
                public JsonResult getInstance(String id) {
                    return null;
                }

                @Override
                public JsonResult invoke(String id) {
                    return null;
                }

                @Override
                public JsonResult addDataServiceByFile(MultipartFile file) {
                    System.out.println(cause.toString());
                    return null;
                }

                @Override
                public JsonResult addByFileToPublic(MultipartFile file, String name, String description) {
                    return null;
                }

                @Override
                public JsonResult createEvaluationService(JSONObject evaluationService) {
                    return null;
                }

                @Override
                public JsonResult listInstanceByIds(List<String> id) {
                    return null;
                }

                @Override
                public JsonResult getInstanceOutputs(String id) {
                    return null;
                }

                @Override
                public JsonResult getEvaluationServiceById(String id) {
                    return null;
                }

                @Override
                public JsonResult listModelServiceByIds(List<String> ids) {
                    return null;
                }

                @Override
                public JsonResult listDataProcessServiceByIds(List<String> ids) {
                    return null;
                }

                @Override
                public JsonResult listModelServiceVOByIds(List<String> ids) {
                    return null;
                }

                @Override
                public JsonResult listDataProcessServiceVOByIds(List<String> ids) {
                    return null;
                }

                @Override
                public JsonResult listEvaluationServicesVO(List<String> evaluationServices) {
                    return null;
                }

                @Override
                public JsonResult listDataServiceByIds(List<String> id) {
                    return null;
                }
            };
        }
    }
}
