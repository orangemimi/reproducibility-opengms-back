package edu.njnu.opengms.container.domain.instance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import edu.njnu.opengms.common.domain.container.instance.InstanceEnum;
import edu.njnu.opengms.common.domain.container.instance.ServiceInstance;
import edu.njnu.opengms.common.domain.container.instance.StatusEnum;
import edu.njnu.opengms.common.domain.container.model.ModelService;
import edu.njnu.opengms.common.domain.container.process.DataProcessService;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import edu.njnu.opengms.container.component.AsyncTaskComponent;
import edu.njnu.opengms.container.domain.evaluation.EvaluationServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ServiceController
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/instance")
public class ServiceInstanceController {

    @Autowired
    ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    AsyncTaskComponent asyncTaskComponent;

    @RequestMapping (value = "", method = RequestMethod.POST)
    JsonResult add(@RequestBody ServiceInstance serviceInstance){
        serviceInstance.setCreateTime(new Date());
        serviceInstance.setUpdateTime(new Date());
        return ResultUtils.success(serviceInstanceRepository.insert(serviceInstance));
    }



    @RequestMapping (value = "", method = RequestMethod.GET)
    JsonResult list(SplitPageDTO splitPageDTO){
        return ResultUtils.success(serviceInstanceRepository.findAll(splitPageDTO.getPageable()));
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    JsonResult get(@PathVariable ("id") String id){
        return ResultUtils.success(serviceInstanceRepository.findById(id).orElse(null));
    }

    @RequestMapping (value = "/{id}/invoke", method = RequestMethod.POST)
    JsonResult invoke(@PathVariable ("id") String id) throws InterruptedException {
        ServiceInstance instance=serviceInstanceRepository.findById(id).orElseThrow(MyException::noObject);
        instance.setStatusEnum(StatusEnum.RUN);
        serviceInstanceRepository.save(instance);
        asyncTaskComponent.executeAsyncInstance(instance);
        return ResultUtils.success(instance);
    }


    @RequestMapping (value = "/listByIds", method = RequestMethod.GET)
    public JsonResult listByIds(@RequestParam("ids") List<String> ids){
        return ResultUtils.success(Lists.newArrayList(serviceInstanceRepository.findAllById(ids)));
    }


    @RequestMapping (value = "/{id}/getOutputs",method = RequestMethod.GET)
    JsonResult getInstanceOuputs(@PathVariable ("id") String id){
        ServiceInstance instance = serviceInstanceRepository.findById(id).orElseThrow(MyException::noObject);
        ObjectMapper objectMapper=new ObjectMapper();
        Object service = instance.getService();
        List<String> outputs=new ArrayList<>();
        if(instance.getInstanceEnum().equals(InstanceEnum.MODEL)){
            ModelService modelService = objectMapper.convertValue(service, ModelService.class);
            modelService.getBehavior().getOutputs().forEach(el-> outputs.add(el.getDataServiceId()));
        }else if(instance.getInstanceEnum().equals(InstanceEnum.PROCESS)){
            DataProcessService dataProcess = objectMapper.convertValue(service, DataProcessService.class);
            dataProcess.getBehavior().getOutputs().forEach(el-> outputs.add(el.getDataServiceId()));
        }
        return ResultUtils.success(outputs);
    }


}
