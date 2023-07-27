package edu.njnu.opengms.container.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @ClassName Server
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/5
 * @Version 1.0.0
 */
public class Server {

    private static final int PORT = 7999;


    public void start() {
        System.out.println("监听7999端口");
        //创建serverSocketChannel，监听7999端口
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //为serverChannel注册selector
            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //创建消息处理器
            ServerHandler handler = new ServerHandlerImpl();

            System.out.println("服务端开始工作");

            while (true) {
                if(selector.select(3000)==0) {
                    continue;
                }
                //获取selectionKeys并处理
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                        //连接请求
                        if (key.isAcceptable()) {
                            handler.handleAccept(key);
                        }else if (key.isReadable()) {
                            handler.handleRead(key);
                        }else if(key.isWritable()&&key.isValid()){
                            handler.handleWrite(key);
                        }

                    //处理完后移除当前使用的key
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
