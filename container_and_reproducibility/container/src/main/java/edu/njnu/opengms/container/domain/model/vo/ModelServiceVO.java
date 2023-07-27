package edu.njnu.opengms.container.domain.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ModelServiceVO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/13
 * @Version 1.0.0
 */
@Data
public class ModelServiceVO {
    public String name;
    public String description;
    public List<String> tags;
    public String creator;
    public Date createTime;
    String id;
}
