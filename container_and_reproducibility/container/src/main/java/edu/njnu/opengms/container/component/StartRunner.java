package edu.njnu.opengms.container.component;


import edu.njnu.opengms.container.socket.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName StartRunner
 * @Description 该类在程序运行之初，可以做一些初始化的工作
 * @Author sun_liber
 * @Date 2018/11/28
 * @Version 1.0.0
 */
@Component
public class StartRunner implements CommandLineRunner {
    @Override
    public void run(String... args){
        new Server().start();
    }
}
