package edu.njnu.opengms.container.socket;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.njnu.opengms.common.domain.container.instance.InstanceEnum;
import edu.njnu.opengms.common.domain.container.instance.ServiceInstance;
import edu.njnu.opengms.common.domain.container.instance.StatusEnum;
import edu.njnu.opengms.common.domain.container.model.ModelService;
import edu.njnu.opengms.common.domain.container.process.DataProcessService;
import edu.njnu.opengms.common.utils.CopyUtils;
import edu.njnu.opengms.common.utils.SpringApplicationContextHolder;
import edu.njnu.opengms.container.domain.instance.ServiceInstanceRepository;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName ServerHandlerImpl
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/5
 * @Version 1.0.0
 */
public class ServerHandlerImpl implements ServerHandler {
    private static final int BUFFERSIZE = 1024 * 32;
    private static final String INSTANCEID = "instanceId:";

    private String value;
    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer;
    private String instanceId;

    private ServiceInstance serviceInstance;

    public ServerHandlerImpl() {
        this.readBuffer = ByteBuffer.allocate(BUFFERSIZE);
        this.writeBuffer = ByteBuffer.allocate(BUFFERSIZE);
    }


    @Override
    public void handleAccept(SelectionKey selectionKey) throws IOException {

        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();

        socketChannel.configureBlocking(false);

        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

        System.out.println("建立请求......");

    }

    @Override
    public void handleRead(SelectionKey selectionKey) throws IOException {
        readBuffer.clear();
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        int num;

        try {
            if ((num = socketChannel.read(readBuffer)) == -1) {
                System.out.println("无信息接入");
                handleError(socketChannel);
            } else {
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_WRITE);
                value = new String(readBuffer.array(), 0, num,System.getProperty("file.encoding"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            handleError(socketChannel);
        }

    }

    @Override
    public void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        channel.configureBlocking(false);
        Selector selector = selectionKey.selector();
        writeBuffer.clear();
        if (JSONUtil.isJson(value)) {
            JSONObject jsonObject = JSONUtil.parseObj(value);
            ServiceInstance instance = SpringApplicationContextHolder.getBean(ServiceInstanceRepository.class).findById(instanceId).orElse(null);
            ObjectMapper objectMapper=new ObjectMapper();
            instance.setStatusEnum(StatusEnum.FINISH);
            if(serviceInstance.getInstanceEnum().equals(InstanceEnum.MODEL)){
                ModelService originalService = objectMapper.convertValue(instance.getService(), ModelService.class);
                ModelService modelService = jsonObject.get("service", ModelService.class);
                CopyUtils.copyProperties(modelService,originalService);
                instance.setService(originalService);
            }else if(serviceInstance.getInstanceEnum().equals(InstanceEnum.PROCESS)){
                DataProcessService originalService = objectMapper.convertValue(instance.getService(), DataProcessService.class);
                DataProcessService dataProcessService = jsonObject.get("service", DataProcessService.class);
                CopyUtils.copyProperties(dataProcessService,originalService);
                instance.setService(originalService);
            }
            SpringApplicationContextHolder.getBean(ServiceInstanceRepository.class).save(instance);
            System.out.println("请求完成");
            channel.close();
            return;
        } else if (value.startsWith(INSTANCEID)) {
            instanceId = value.split(":")[1];
            serviceInstance = SpringApplicationContextHolder.getBean(ServiceInstanceRepository.class).findById(instanceId).orElse(null);
            writeBuffer.put(JSONUtil.toJsonStr(serviceInstance).getBytes());
            writeBuffer.flip();
            channel.write(writeBuffer);
            channel.register(selector, SelectionKey.OP_READ);
        } else {
            System.out.println("value: " + value);
            handleError(channel);
            return;
        }
    }

    public void handleError(SocketChannel socketChannel) throws IOException {
        System.out.println("模型运行出错");
        serviceInstance.setStatusEnum(StatusEnum.ERROR);
        SpringApplicationContextHolder.getBean(ServiceInstanceRepository.class).save(serviceInstance);
        socketChannel.close();
    }


}
