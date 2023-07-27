package edu.njnu.opengms.container.domain.evaluation;

import edu.njnu.opengms.common.controller.BaseController;
import edu.njnu.opengms.common.domain.container.evaluation.EvaluationService;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.CopyUtils;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import edu.njnu.opengms.container.domain.evaluation.dto.AddEvaluationServiceDTO;
import edu.njnu.opengms.container.domain.evaluation.vo.EvaluationServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @ClassName EvaluationServiceControllerImpl
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@RestController
@RequestMapping("evaluation_service")
public class EvaluationServiceControllerImpl implements BaseController<AddEvaluationServiceDTO, SplitPageDTO, EvaluationService> {

    @Autowired
    EvaluationServiceRepository evaluationServiceRepository;

    @RequestMapping (value = "/listVOByIds", method = RequestMethod.GET)
    public JsonResult listVOByIds(@RequestParam ("ids") List<String> ids) {
        return ResultUtils.success(StreamSupport.stream(evaluationServiceRepository.findAllById(ids).spliterator(), true).map(service -> {
            EvaluationServiceVO vo = new EvaluationServiceVO();
            CopyUtils.copyProperties(service, vo);
            return vo;
        }).collect(Collectors.toList()));
    }


    @Override
    public JsonResult add(AddEvaluationServiceDTO a) {
        EvaluationService evaluationService=new EvaluationService();
        a.convertTo(evaluationService);
        return ResultUtils.success(evaluationServiceRepository.insert(evaluationService));
    }

    @Override
    public JsonResult delete(String id) {
        evaluationServiceRepository.deleteById(id);
        return ResultUtils.success("删除成功");
    }

    @Override
    public JsonResult list(SplitPageDTO findDTO) {
        return ResultUtils.success(evaluationServiceRepository.findAll(findDTO.getPageable()));
    }

    @Override
    public JsonResult get(String id) {
        return ResultUtils.success(evaluationServiceRepository.findById(id));
    }

    @Override
    public JsonResult count() {
        return ResultUtils.success(evaluationServiceRepository.count());
    }


    @Override
    public JsonResult update(String id, EvaluationService updateDTO) {
        EvaluationService evaluationService = evaluationServiceRepository.findById(id).orElseThrow(MyException::noObject);
        evaluationService.setUpdateTime(new Date());
        evaluationService.getBehavior().setInteractiveCode(updateDTO.getBehavior().getInteractiveCode());
        return ResultUtils.success(evaluationServiceRepository.save(evaluationService));
    }
}
