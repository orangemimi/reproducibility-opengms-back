package edu.njnu.opengms.container.domain.model;

import com.google.common.collect.Lists;
import edu.njnu.opengms.common.controller.BaseController;
import edu.njnu.opengms.common.domain.container.model.ModelService;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.utils.CopyUtils;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import edu.njnu.opengms.container.domain.model.dto.AddModelServiceDTO;
import edu.njnu.opengms.container.domain.model.vo.ModelServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @ClassName ModelServiceControllerImpl
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@RestController
@RequestMapping("model_service")
public class ModelServiceControllerImpl  implements BaseController<AddModelServiceDTO, SplitPageDTO, ModelService> {

    @Autowired
    ModelServiceRepository modelServiceRepository;

    @RequestMapping (value = "/listVOByIds", method = RequestMethod.GET)
    public JsonResult listVOByIds(@RequestParam ("ids") List<String> ids) {
        return ResultUtils.success(StreamSupport.stream(modelServiceRepository.findAllById(ids).spliterator(), true).map(service -> {
//            ModelServiceVO vo = new ModelServiceVO();
//            CopyUtils.copyProperties(service, vo);
            return service;
        }).collect(Collectors.toList()));
    }

    @RequestMapping (value = "/listByIds", method = RequestMethod.GET)
    public JsonResult listByIds(@RequestParam("ids")List<String>ids){
        return ResultUtils.success(Lists.newArrayList(modelServiceRepository.findAllById(ids)));
    }

    @RequestMapping (value = "/all", method = RequestMethod.GET)
    public JsonResult findAll(){
        return ResultUtils.success(modelServiceRepository.findAll());
    }

    @Override
    public JsonResult add(AddModelServiceDTO a) {
        ModelService modelService=new ModelService();
        a.convertTo(modelService);
        return ResultUtils.success(modelServiceRepository.insert(modelService));
    }

    @Override
    public JsonResult get(String id) {
        return ResultUtils.success(modelServiceRepository.findById(id).orElseGet(null));
    }

    @Override
    public JsonResult delete(String id) {
         modelServiceRepository.deleteById(id);
         return ResultUtils.success("删除成功");
    }

    @Override
    public JsonResult list(SplitPageDTO findDTO) {
        if(findDTO.getValue()==null||findDTO.getValue().equals("")){
            Page<ModelService> all = modelServiceRepository.findAll(findDTO.getPageable());
            return ResultUtils.success(all.map(service -> {
                ModelServiceVO vo = new ModelServiceVO();
                CopyUtils.copyProperties(service, vo);
                return vo;
            }));
        }
        return ResultUtils.success(modelServiceRepository.getByNameContainsIgnoreCase(findDTO.getValue(),findDTO.getPageable()));
    }

    @Override
    public JsonResult count() {
        return ResultUtils.success(modelServiceRepository.count());
    }


    @Override
    public JsonResult update(String id, ModelService updateDTO) {
        return null;
    }
}
