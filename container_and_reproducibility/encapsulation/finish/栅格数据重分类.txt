package support;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.ZipUtil;
import support.model.ModelEvent;
import support.process.DataProcessService;
import support.process.ProcessBehavior;
import support.utils.DataUtils;
import support.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @ClassName DataProcessEncapsulation
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
public class DataProcessEncapsulation implements Instance  {

    public DataProcessService dataProcessService;

    public DataProcessEncapsulation(DataProcessService dataProcessService) {
        this.dataProcessService = dataProcessService;
    }

    @Override
    public void run(String id) throws IOException {
        ProcessBehavior behavior = dataProcessService.getBehavior();
        Path workSpace= PathUtils.getWorkSpace(id);
        Path sagePath=PathUtils.getPublicExe("saga");
        Path resultDir=workSpace.resolve("raster_after_reclassify");


        File zipDir=workSpace.resolve("zipDir").toFile();


        File rasterFile,reclassify = null;
        for (ModelEvent input : behavior.getInputs()) {
            String dataServiceId = input.getDataServiceId();
            String name = input.getName();
            if(name.equals("raster_input")){
                rasterFile=workSpace.resolve(name).toFile();
                DataUtils.download(dataServiceId,rasterFile);
                ZipUtil.unzip(rasterFile,zipDir);
            }else{
                reclassify=workSpace.resolve(name).toFile();
                DataUtils.download(dataServiceId,reclassify);
            }
        }

        List<File> files = FileUtil.loopFiles(zipDir, (pathname) -> pathname.getName().endsWith(".sgrd"));
        File sgrdFile=files.get(0);
        resultDir.toFile().mkdir();
        String cmd;
        cmd=sagePath.toString()+" grid_tools 15 -METHOD 2  -RETAB "+reclassify.getAbsolutePath()
                +" -INPUT "+sgrdFile
                +" -RESULT "+resultDir.resolve("after_reclassify.sgrd").toString();
        Process process = RuntimeUtil.exec(null, sagePath.getParent().toFile(), new String[]{cmd});
        RuntimeUtil.getResult(process);

        for (ModelEvent output : behavior.getOutputs()) {
            File zip=ZipUtil.zip(resultDir.toString());
            output.setDataServiceId(DataUtils.upload(zip));
        }
    }
}
