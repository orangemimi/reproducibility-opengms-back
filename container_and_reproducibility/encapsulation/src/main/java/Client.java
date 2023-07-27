import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import support.DataProcessEncapsulation;
import support.InstanceEnum;
import support.ModelEncapsulation;
import support.ServiceInstance;
import support.model.ModelService;
import support.process.DataProcessService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName njnu.opengms.modelclient.Client
 * @Description
 * @Author sun_liber
 * @Date 2019/11/5
 * @Version 1.0.0
 */
public class Client {
    private static final int BUFFERSIZE = 1024*32;
    private ByteBuffer readBuffer ;
    private ByteBuffer writeBuffer;


    public Client() {
        this.readBuffer=ByteBuffer.allocate(BUFFERSIZE);
        this.writeBuffer=ByteBuffer.allocate(BUFFERSIZE);
    }

    public static void main(String[] args){
        SocketAddress socketAddress = new InetSocketAddress(args[0],Integer.valueOf(args[1]));
        new Client().start(socketAddress, args[2]);
    }


    public void sendMessage(SocketChannel socketChannel,String value) throws IOException {
        writeBuffer.clear();
        writeBuffer.put(value.getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
    }


    public void start(SocketAddress socketAddress,String id){
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(socketAddress);
            System.out.println("传输实例ID");
            sendMessage(socketChannel,"instanceId:"+id);
            System.out.println("获取实例信息");
            String message = receiveMessage(socketChannel);
            ServiceInstance instance = JSONUtil.parseObj(message).toBean(ServiceInstance.class);
            InstanceEnum instanceEnum = instance.getInstanceEnum();
            if(instanceEnum.equals(InstanceEnum.MODEL)){
                ModelEncapsulation modelEncapsulation =new ModelEncapsulation(JSONUtil.toBean((JSONObject) instance.getService(),ModelService.class));
                System.out.println("运行模型");
                modelEncapsulation.run(id);
                ModelService modelService = modelEncapsulation.modelService;
                instance.setService(modelService);
                sendMessage(socketChannel, JSONUtil.toJsonStr(instance));
                System.out.println("传输模型实例");
            }else if(instanceEnum.equals(InstanceEnum.PROCESS)){
                DataProcessEncapsulation dataProcessEncapsulation =new DataProcessEncapsulation(JSONUtil.toBean((JSONObject) instance.getService(), DataProcessService.class));
                System.out.println("运行数据处理方法");
                dataProcessEncapsulation.run(id);
                DataProcessService dataProcessService=dataProcessEncapsulation.dataProcessService;
                instance.setService(dataProcessService);
                sendMessage(socketChannel, JSONUtil.toJsonStr(instance));
                System.out.println("传输数据处理方法实例");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage(SocketChannel socketChannel) throws IOException {
        int len = socketChannel.read(readBuffer);
        readBuffer.flip();
        byte[] bytes = new byte[len];
        readBuffer.get(bytes);
        readBuffer.clear();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}

