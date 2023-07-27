//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package support;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.ZipUtil;
import support.model.ModelBehavior;
import support.model.ModelEvent;
import support.model.ModelService;
import support.model.Parameter;
import support.utils.DataUtils;
import support.utils.PathUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ModelEncapsulation implements Instance {

    public ModelService modelService;

    public ModelEncapsulation(ModelService modelService) {
        this.modelService = modelService;
    }


    @Override
    public void run(String instanceId) {
        //获取模型行为
        ModelBehavior behavior = modelService.getBehavior();
        //将模型依赖拷贝到工作目录
        Path assemblySpace = PathUtils.getAssembly();
        Path workSpace = PathUtils.getWorkSpace(instanceId);
        FileUtil.copyContent(assemblySpace.toFile(), workSpace.toFile(), true);
        //将输入数据下载到本地
        File fileInput = null;
        for (ModelEvent input : behavior.getInputs()) {
            if ("DEM".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("Basics").resolve("DEM.asc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("FAC".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("Basics").resolve("FAC.asc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("FDR".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("Basics").resolve("FDR.asc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("Stream".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("Basics").resolve("Stream.asc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("InitialConditions".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("ICS").resolve("InitialConditions.txt").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("Pets".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("PETs.zip").toFile();
                DataUtils.download(dataServiceId, fileInput);
                ZipUtil.unzip(fileInput);
            }
            if ("file_obs01_input".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("DATA").resolve("obs01_daily.nc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("file_BJPfcst_input".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("DATA").resolve("ens_gefs_bjp.nc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
            if ("file_rawfcst_input".equals(input.getName())) {
                String dataServiceId = input.getDataServiceId();
                fileInput = workSpace.resolve("DATA").resolve("ens_gefs_raw.nc").toFile();
                DataUtils.download(dataServiceId, fileInput);
            }
        }


        List<Parameter> parameters = behavior.getParameters();
        String warm_up_arange = "",
                issuetime = "",
                runoff_obs_begin = "",
                runoff_obs_end = "",
                fcst_data_type = "",
                drawing_type = "",
                streamflow_start = "",
                streamflow_end = "";
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            if (name.equals("warm_up_arange")) {
                warm_up_arange = parameter.getValue();
            }
            if (name.equals("issuetime")) {
                issuetime = parameter.getValue();
            }
            if (name.equals("runoff_obs_begin")) {
                runoff_obs_begin = parameter.getValue();
            }
            if (name.equals("runoff_obs_end")) {
                runoff_obs_end = parameter.getValue();
            }
            if (name.equals("fcst_data_type")) {
                fcst_data_type = parameter.getValue();
            }
            if (name.equals("drawing_type")) {
                drawing_type = parameter.getValue();
            }
            if (name.equals("streamflow_start")) {
                streamflow_start = parameter.getValue();
            }
            if (name.equals("streamflow_end")) {
                streamflow_end = parameter.getValue();
            }
        }


        //执行模型
        String cmd;
        cmd = "H:\\Environment\\Python\\python "+workSpace.toAbsolutePath()+"/Rolling_forecast/parameter_setting.py " +
                warm_up_arange + " "
                + issuetime + " "
                + runoff_obs_begin + " "
                + runoff_obs_end + " "
                + fcst_data_type + " "
                + streamflow_start + " "
                + streamflow_end + " "
                + drawing_type + " "
                + workSpace.toAbsolutePath() + "/"
        ;
        Process process = RuntimeUtil.exec(null, workSpace.toFile(), new String[]{cmd});
        RuntimeUtil.getResult(process);

        cmd = "H:\\Environment\\Python\\python "+workSpace.toAbsolutePath()+"/Rolling_forecast/rolling_forecast_paralleling.py "+
                workSpace.toAbsolutePath()+"/Rolling_forecast/cfg_crest.json";
        process = RuntimeUtil.exec(null, workSpace.toFile(), new String[]{cmd});
        RuntimeUtil.getResult(process);

        List<ModelEvent> outputs = behavior.getOutputs();
        File outFile;
        //将输出数据上传到服务器
        for (ModelEvent output : outputs) {
            String name = output.getName();
            if (name.equals("ens_0")) {
                output.setDataServiceId(DataUtils.upload(
                        workSpace
                                .resolve("Rolling_result")
                                .resolve(issuetime)
                                .resolve("raw_forecast")
                                .resolve("ens_0")
                                .resolve("Results")
                                .resolve("Outlet_wjb_Results.csv").toFile()
                ));
            }
            if (name.equals("ens_1")) {
                output.setDataServiceId(DataUtils.upload(
                        workSpace
                                .resolve("Rolling_result")
                                .resolve(issuetime)
                                .resolve("raw_forecast")
                                .resolve("ens_1").resolve("Results")
                                .resolve("Outlet_wjb_Results.csv").toFile()
                ));
            }
            if (name.equals("ens_2")) {
                output.setDataServiceId(DataUtils.upload(
                        workSpace
                                .resolve("Rolling_result")
                                .resolve(issuetime)
                                .resolve("raw_forecast")
                                .resolve("ens_2").resolve("Results")
                                .resolve("Outlet_wjb_Results.csv").toFile()
                ));
            }
            if (name.equals("ens_3")) {
                output.setDataServiceId(DataUtils.upload(
                        workSpace
                                .resolve("Rolling_result")
                                .resolve(issuetime)
                                .resolve("raw_forecast")
                                .resolve("ens_3").resolve("Results")
                                .resolve("Outlet_wjb_Results.csv").toFile()
                ));
            }

            if (name.equals("ens_4")) {
                output.setDataServiceId(DataUtils.upload(
                        workSpace
                                .resolve("Rolling_result")
                                .resolve(issuetime)
                                .resolve("raw_forecast")
                                .resolve("ens_4").resolve("Results")
                                .resolve("Outlet_wjb_Results.csv").toFile()
                ));
            }
        }
    }
}
