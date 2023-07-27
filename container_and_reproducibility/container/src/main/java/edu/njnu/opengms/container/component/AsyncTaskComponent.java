package edu.njnu.opengms.container.component;

import cn.hutool.core.util.RuntimeUtil;
import edu.njnu.opengms.common.domain.container.instance.InstanceEnum;
import edu.njnu.opengms.common.domain.container.instance.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * @ClassName AsyncTaskComponent
 * @Description 使用了@Async注解，就会生成子线程，实现异步
 * @Author sun_liber
 * @Date 2018/11/15
 * @Version 1.0.0
 */
@Component
public class AsyncTaskComponent {

    @Value ("${web.upload-path}")
    String storePath;




    @Async
    public void executeAsyncInstance(ServiceInstance instance) throws InterruptedException {
        System.out.println("实例运行");
        String service_id = (String) ((LinkedHashMap) instance.getService()).get("id");;
        Path path;
        if(instance.getInstanceEnum().equals(InstanceEnum.MODEL)){
            path= Paths.get(storePath,"service_entity","model",service_id);
        }else {
            path= Paths.get(storePath,"service_entity","process",service_id);
        }
        Process exec = RuntimeUtil.exec(null, path.toFile(), "java -Dfile.encoding=utf-8 -jar main.jar localhost 7999 " + instance.getId());
        RuntimeUtil.getResult(exec);
    }
}
