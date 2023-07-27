package edu.njnu.opengms.r2.zaqizaba;


import edu.njnu.opengms.common.exception.MyException;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSocketServer
 * @Description todo
 * @Author sun_liber
 * @Date 2019/7/17
 * @Version 1.0.0
 */
@ServerEndpoint ("/websocket/{sid}")
@Component
public class WebSocketServer {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    private Session session;
    private String sid = "";

    public static void sendInfo(String message, String sid) {
        for (WebSocketServer item : webSocketSet) {
            try {
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam ("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();           //在线数加1
        this.sid = sid;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            throw new MyException("连接失败");
        }
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }



    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
