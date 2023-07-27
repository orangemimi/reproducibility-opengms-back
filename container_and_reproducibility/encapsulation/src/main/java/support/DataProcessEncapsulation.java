package support;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import support.model.ModelEvent;
import support.process.DataProcessService;
import support.process.ProcessBehavior;
import support.utils.DataUtils;
import support.utils.PathUtils;

import java.io.File;
import java.nio.file.Path;

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
    public void run(String id) {
        ProcessBehavior behavior = dataProcessService.getBehavior();
        Path workSpace= PathUtils.getWorkSpace(id);
        Path assemblySpace=PathUtils.getAssembly();
        FileUtil.copyContent(assemblySpace.toFile(), workSpace.toFile(), true);

        Path inputDir = workSpace.resolve("input");
        Path outDir = workSpace.resolve("output");

        for (ModelEvent input : behavior.getInputs()) {
            String name = input.getName();
            String dataServiceId = input.getDataServiceId();
            File file = inputDir.resolve(name).toFile();
            DataUtils.download(dataServiceId,file);
        }

        String cmd;
        cmd="python start.py "+inputDir+" "+outDir;
        Process process = RuntimeUtil.exec(null, workSpace.toFile(), new String[]{cmd});
        RuntimeUtil.getResult(process);

        for (ModelEvent output : behavior.getOutputs()) {
            String name = output.getName();
            File file = outDir.resolve(name).toFile();
            output.setDataServiceId(DataUtils.upload(file));
        }
    }
}
