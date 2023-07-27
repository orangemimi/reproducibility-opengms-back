package edu.njnu.opengms.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @ClassName SysLog
 * @Description todo
 * @Author sun_liber
 * @Date 2019/7/5
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SysLog {
    @Id
    private String id;
    private String uid;
    private String actionName;
    private String ip;
    private String uri;
    private String params;
    private String httpMethod;
    private String classMethod;
    private String userName;
    private Date createTime;
}
