package edu.njnu.opengms.r2.domain.scene.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName G2SListVO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/8
 * @Version 1.0.0
 */
@Data
public class G2SListVO {
    String id;
    String name;
    String description;
    String creator;
    String snapshot;
    Boolean isPublish;
    @JsonFormat (pattern = "yyyy-MM-dd",timezone = "GMT+8")
     Date createTime;
}
