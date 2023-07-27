package edu.njnu.opengms.r2.domain.creator;

import edu.njnu.opengms.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName Creator
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/7
 * @Version 1.0.0
 */
@Data
@Document
@Builder
public class Creator  extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String location;
    private String website;
    private String info;
    private String avatar;
}
