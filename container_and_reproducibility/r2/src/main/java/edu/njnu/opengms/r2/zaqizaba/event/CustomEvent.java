package edu.njnu.opengms.r2.zaqizaba.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @ClassName CustomEvent
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/22
 * @Version 1.0.0
 */
@Getter
public class CustomEvent<DATA> extends ApplicationEvent {
    private DATA data;

    public CustomEvent(DATA source) {
        super(source);
        this.data = source;
    }
}
