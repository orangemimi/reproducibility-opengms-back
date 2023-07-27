package edu.njnu.opengms.r2.zaqizaba.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomEventListener
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/22
 * @Version 1.0.0
 */
@Component
public class CustomEventListener {
    @EventListener
    public void listenEvent(CustomEvent<String> str) {
        System.out.println(str);
    }

    @EventListener
    public void listenEvent1(CustomEvent<Integer> str) {
        System.out.println(str + "1");
    }
}
