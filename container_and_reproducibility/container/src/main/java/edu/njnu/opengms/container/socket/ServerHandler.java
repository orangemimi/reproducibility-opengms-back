package edu.njnu.opengms.container.socket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @InterfaceName ServerHandler
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/5
 * @Version 1.0.0
 */
public interface ServerHandler {
    void handleAccept(SelectionKey selectionKey) throws IOException;

    void handleRead(SelectionKey selectionKey) throws IOException;

    void handleWrite(SelectionKey selectionKey) throws IOException;
}
