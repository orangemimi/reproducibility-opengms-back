package edu.njnu.opengms.r2.domain.scene.vo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import edu.njnu.opengms.r2.domain.scene.nodes.ContextDefine;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName G2SVO
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/8
 * @Version 1.0.0
 */
@Data
public class G2SVO {
    String id;
    String name;
    String description;
    String background;
    String goals;
    String creator;
    @JsonFormat (pattern = "yyyy-MM-dd",timezone = "GMT+8")
    Date createTime;
    ContextDefine contextDefine;
    JSONObject resourceCollect;
    JSONObject simulationConceptGraph;
    JSONObject computation;
    JSONArray evaluation;
}
