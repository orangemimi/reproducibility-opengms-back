package edu.njnu.opengms.r2.domain.scene;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import edu.njnu.opengms.common.controller.BaseController;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.CopyUtils;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import edu.njnu.opengms.r2.domain.scene.dto.AddG2SDTO;
import edu.njnu.opengms.r2.domain.scene.dto.UpdateG2SDTO;
import edu.njnu.opengms.r2.domain.scene.nodes.Computation;
import edu.njnu.opengms.r2.domain.scene.nodes.Evaluation;
import edu.njnu.opengms.r2.domain.scene.vo.G2SListVO;
import edu.njnu.opengms.r2.domain.scene.vo.G2SVO;
import edu.njnu.opengms.r2.remote.ContainerFeign;
import edu.njnu.opengms.r2.utils.MxGraphUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName G2SControllerImpl
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/30
 * @Version 1.0.0
 */
@RestController
@RequestMapping ("/g2s")
public class G2SControllerImpl implements BaseController<AddG2SDTO, SplitPageDTO, UpdateG2SDTO> {

    @Autowired
    G2SRepository g2SRepository;

    @Autowired
    ContainerFeign containerFeign;

    @Value ("${web.upload-path}")
    String webUploadPath;


    @RequestMapping (value = "/{id}/evaluationServices", method = RequestMethod.GET)
    public JsonResult evaluationServices(@PathVariable String id) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        return Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getEvaluation())
                .map(x -> x.getEvaluationServices())
                .map(x -> ResultUtils.success(containerFeign.listEvaluationServicesVO(x).getData()))
                .orElse(ResultUtils.success(null));
    }

    @RequestMapping (value = "/{id}/evaluationService/{evaluationId}", method = RequestMethod.DELETE)
    public JsonResult evaluationServices(@PathVariable String id, @PathVariable String evaluationId) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        return Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getEvaluation())
                .map(x -> x.getEvaluationServices())
                .map(x -> {
                    x.remove(evaluationId);
                    geographicSimulationScene.getEvaluation().setEvaluationServices(x);
                    return ResultUtils.success(g2SRepository.save(geographicSimulationScene));
                })
                .orElse(ResultUtils.success(null));
    }


    @RequestMapping (value = "/{id}/dataServices", method = RequestMethod.GET)
    public JsonResult dataServices(@PathVariable String id) {
        JSONObject obj = new JSONObject();
        JSONArray array =new JSONArray();
        JSONArray arrayRe =new JSONArray();
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        return Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getResourceCollect())
                .map(x -> {
                    List<String> dataServices = x.getDataServices();
                    if (dataServices != null) {
                        obj.put("originalDataServices", containerFeign.listDataServiceByIds(dataServices).getData());
                    } else {
                        obj.put("originalDataServices", null);
                    }
                    Map<String, List<String>> intermediateDataServicesMap = x.getIntermediateDataServices();
                    if (intermediateDataServicesMap != null) {
                        dataServices.clear();
                        intermediateDataServicesMap.entrySet().stream().forEach(el -> {
                            JSONObject objnew = new JSONObject();
                            //TODO 后面这个字段要展示到前端，以显示中间数据由哪一个服务实例生产
                            String serviceInstanceId = el.getKey();

                            HashMap serviceInstance= (HashMap) containerFeign.getInstance(serviceInstanceId).getData();
                            ArrayList dataServicesNew= (ArrayList) containerFeign.listDataServiceByIds(el.getValue()).getData();
                            for(int i=0;i<dataServicesNew.size();i++)
                            {
                                HashMap map=(HashMap)dataServicesNew.get(i);
                                map.put("instanceName",serviceInstance.get("name"));
                                String name =serviceInstance.get("name").toString()+" "+map.get("name").toString();
                                map.put("name",name);
                                map.put("isReproduced",serviceInstance.get("isReproduced"));
                                array.add(dataServicesNew.get(i));
                            }
//                            array.add(dataServicesNew);

//                            objnew.put("serviceInstance",serviceInstance);
//                            objnew.put("dataValues", containerFeign.listDataServiceByIds(el.getValue()).getData());
                            //获取



//                            List<String> dataValues = el.getValue();
//                            Object dataServiceList= containerFeign.listDataServiceByIds(dataValues).getData();

//                            dataServices.addAll(dataValues);
                        });
                        obj.put("intermediateDataServices",array);
                    } else {
                        obj.put("intermediateDataServices", null);
                    }
                    return ResultUtils.success(obj);
                })
                .orElse(ResultUtils.success(null));
    }

    @RequestMapping (value = "/{id}/computeServices", method = RequestMethod.GET)
    public JsonResult computeServices(@PathVariable String id) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        JSONObject obj = new JSONObject();
        return Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getResourceCollect())
                .map(x -> {
//                    List<String> dataProcessServices = x.getDataProcessServices();
                    List<String> modelServices = x.getModelServices();
//                    obj.put("dataProcessServices", containerFeign.listDataProcessServiceByIds(dataProcessServices).getData());
                    obj.put("modelServices", containerFeign.listModelServiceByIds(modelServices).getData());
                    return ResultUtils.success(obj);
                }).orElse(ResultUtils.success(null));
    }

    @RequestMapping (value = "/{id}/evaluationService", method = RequestMethod.POST)
    public JsonResult evaluationService(@PathVariable String id, @RequestBody JSONObject obj) {
        String evaluationServiceId = (String) ((LinkedHashMap) containerFeign.createEvaluationService(obj).getData()).get("id");
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        List<String> evaluationServices = Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getEvaluation())
                .map(x -> x.getEvaluationServices())
                .orElseGet(() -> new ArrayList<>());
        if (evaluationServices.isEmpty()) {
            evaluationServices.add(evaluationServiceId);
            Evaluation evaluation = new Evaluation();
            evaluation.setEvaluationServices(evaluationServices);
            geographicSimulationScene.setEvaluation(evaluation);
        } else {
            evaluationServices.add(evaluationServiceId);
            geographicSimulationScene.getEvaluation().setEvaluationServices(evaluationServices);
        }
        g2SRepository.save(geographicSimulationScene);
        return ResultUtils.success(evaluationServiceId);
    }


    @RequestMapping (value = "/{id}/bind/{instanceId}", method = RequestMethod.POST)
    public JsonResult bind(@PathVariable String id, @PathVariable String instanceId, @RequestParam ("instanceEnum") String instanceEnum) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        if ("MODEL".equals(instanceEnum)) {
            Set<String> modelExpectedInstances = Optional.ofNullable(geographicSimulationScene)
                    .map(x -> x.getComputation())
                    .map(x -> x.getModelExpectedInstances())
                    .orElse(new HashSet<>());
            if (modelExpectedInstances.contains(instanceId)) {
                return ResultUtils.success("该实例已绑定");
            }
            modelExpectedInstances.add(instanceId);
            geographicSimulationScene.getComputation().setModelExpectedInstances(modelExpectedInstances);
        } else {
            Set<String> processExpectedInstances = Optional.ofNullable(geographicSimulationScene)
                    .map(x -> x.getComputation())
                    .map(x -> x.getProcessExpectedInstances())
                    .orElse(new HashSet<>());
            if (processExpectedInstances.contains(instanceId)) {
                return ResultUtils.success("该实例已绑定");
            }
            processExpectedInstances.add(instanceId);
            geographicSimulationScene.getComputation().setProcessExpectedInstances(processExpectedInstances);
        }

        Map<String, List<String>> intermediateDataServices = Optional.ofNullable(geographicSimulationScene)
                .map(x -> x.getResourceCollect())
                .map(x -> x.getIntermediateDataServices())
                .orElse(new HashMap<>());
        intermediateDataServices.put(instanceId, (List<String>) containerFeign.getInstanceOutputs(instanceId).getData());
        geographicSimulationScene.getResourceCollect().setIntermediateDataServices(intermediateDataServices);

        return ResultUtils.success(g2SRepository.save(geographicSimulationScene));
    }


    @RequestMapping (value = "/{id}/invoke", method = RequestMethod.POST)
    public JsonResult invokeService(@PathVariable String id, @RequestBody JSONObject serviceInstance) {
        try {
            String serviceId = serviceInstance.getJSONObject("service").getStr("id");
            String instanceId = (String) ((LinkedHashMap) containerFeign.addInstance(serviceInstance).getData()).get("id");
            GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
            Computation computation = Optional.ofNullable(geographicSimulationScene)
                    .map(x -> x.getComputation())
                    .orElse(new Computation());
            if (serviceInstance.get("instanceEnum").equals("MODEL")) {
                Map<String, List<String>> modelInstances = Optional.ofNullable(computation).map(x -> x.getModelInstances()).orElse(new HashMap<>());
                List<String> list = Optional.ofNullable(modelInstances).map(x -> x.get(serviceId)).orElse(new ArrayList<>());
                list.add(instanceId);
                modelInstances.put(serviceId, list);
                computation.setModelInstances(modelInstances);
            } else {
                Map<String, List<String>> processInstances = Optional.ofNullable(computation).map(x -> x.getProcessInstances()).orElse(new HashMap<>());
                List<String> list = Optional.ofNullable(processInstances).map(x -> x.get(serviceId)).orElse(new ArrayList<>());
                list.add(instanceId);
                processInstances.put(serviceId, list);
                computation.setProcessInstances(processInstances);
            }
            geographicSimulationScene.setComputation(computation);
            geographicSimulationScene.setUpdateTime(new Date());
            g2SRepository.save(geographicSimulationScene);
            return ResultUtils.success(containerFeign.invoke(instanceId).getData());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }


    @RequestMapping (value = "/{id}/getInstances/{type}/{serviceId}", method = RequestMethod.GET)
    public JsonResult getInstances(@PathVariable String id, @PathVariable String type, @PathVariable String serviceId) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        if (type.equals("MODEL")) {
            return Optional.ofNullable(geographicSimulationScene)
                    .map(x -> x.getComputation())
                    .map(x -> x.getModelInstances())
                    .map(x -> ResultUtils.success(containerFeign.listInstanceByIds(x.get(serviceId)).getData()))
                    .orElse(ResultUtils.success(null));

        } else {
            return Optional.ofNullable(geographicSimulationScene)
                    .map(x -> x.getComputation())
                    .map(x -> x.getProcessInstances())
                    .map(x -> ResultUtils.success(containerFeign.listInstanceByIds(x.get(serviceId)).getData()))
                    .orElse(ResultUtils.success(null));
        }
    }


    @RequestMapping (value = "/{id}/folk", method = RequestMethod.POST)
    public JsonResult folk(@PathVariable ("id") String id, @RequestBody AddG2SDTO a) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        a.convertTo(geographicSimulationScene);
        geographicSimulationScene.setId(null);
        geographicSimulationScene.setIsPublish(false);
        return ResultUtils.success(g2SRepository.insert(geographicSimulationScene));
    }


    @RequestMapping (value = "/{id}/view", method = RequestMethod.GET)
    public JsonResult getG2SVO(@PathVariable String id) {
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElse(null);
        G2SVO g2SVO = new G2SVO();
        CopyUtils.copyProperties(geographicSimulationScene, g2SVO);

        JSONObject jsonObject = new JSONObject();


        List<String> ids = geographicSimulationScene.getResourceCollect().getDataProcessServices();
        if(ids != null){
            jsonObject.put("dataProcessServices", containerFeign.listDataProcessServiceVOByIds(ids).getData());
        }

        ids = geographicSimulationScene.getResourceCollect().getModelServices();
        jsonObject.put("modelServices", containerFeign.listModelServiceVOByIds(ids).getData());
        ids = geographicSimulationScene.getResourceCollect().getDataServices();
        jsonObject.put("dataServices", containerFeign.listDataServiceByIds(ids).getData());
        g2SVO.setResourceCollect(jsonObject);
        jsonObject = new JSONObject();

        if (geographicSimulationScene.getSimulationConceptGraph()!=null) {
            jsonObject.put("imgGraph", geographicSimulationScene.getSimulationConceptGraph().getImgGraph());
            g2SVO.setSimulationConceptGraph(jsonObject);
        }
        jsonObject = new JSONObject();

        ids = new ArrayList<>();
        Set<String> processExpectedInstances = geographicSimulationScene.getComputation().getProcessExpectedInstances();
        Set<String> modelExpectedInstances = geographicSimulationScene.getComputation().getModelExpectedInstances();
        if (processExpectedInstances != null) {
            ids.addAll(processExpectedInstances);
        }

        if (modelExpectedInstances != null) {
            ids.addAll(modelExpectedInstances);
        }

        jsonObject.put("serviceInstances", containerFeign.listInstanceByIds(ids).getData());
        g2SVO.setComputation(jsonObject);
        if(geographicSimulationScene.getEvaluation()!=null){
            ids = geographicSimulationScene.getEvaluation().getEvaluationServices();
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll((Collection<? extends Object>) containerFeign.listEvaluationServicesVO(ids).getData());
            g2SVO.setEvaluation(jsonArray);
        }

        return ResultUtils.success(g2SVO);
    }


    @Override
    public JsonResult add(AddG2SDTO a) {
        GeographicSimulationScene geographicSimulationScene = new GeographicSimulationScene();
        a.convertTo(geographicSimulationScene);
        return ResultUtils.success(g2SRepository.insert(geographicSimulationScene));
    }

    @Override
    public JsonResult delete(String id) {
        g2SRepository.deleteById(id);
        return ResultUtils.success("删除成功");
    }

    @Override
    public JsonResult list(SplitPageDTO findDTO) {
        Page<GeographicSimulationScene> all = g2SRepository.findAll(findDTO.getPageable());
        return ResultUtils.success(all.map(g2s -> {
            G2SListVO vo = new G2SListVO();
            CopyUtils.copyProperties(g2s, vo);
            return vo;
        }));
    }

    @Override
    public JsonResult count() {
        return ResultUtils.success(g2SRepository.count());
    }

    @Override
    public JsonResult get(String id) {
        return ResultUtils.success(g2SRepository.findById(id).orElse(null));
    }

    @Override
    public JsonResult update(String id, UpdateG2SDTO updateDTO) throws ParserConfigurationException, SAXException, IOException {
        String rootXml = updateDTO.getRootXml();
        Integer h = updateDTO.getH();
        Integer w = updateDTO.getW();
        if (h != null && w != null & rootXml != null) {
            String uuid = UUID.randomUUID().toString();
            MxGraphUtils.exportImg(rootXml, w, h, webUploadPath + File.separator + uuid);
            updateDTO.getSimulationConceptGraph().setImgGraph(uuid);
        }
        GeographicSimulationScene geographicSimulationScene = g2SRepository.findById(id).orElseThrow(MyException::noObject);
        updateDTO.updateTo(geographicSimulationScene);
        return ResultUtils.success(g2SRepository.save(geographicSimulationScene));
    }


}
