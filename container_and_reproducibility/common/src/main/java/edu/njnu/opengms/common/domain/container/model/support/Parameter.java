package edu.njnu.opengms.common.domain.container.model.support;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * @ClassName Parameter
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/3/18
 * @Version 1.0.0
 */
@Data
@JsonTypeInfo (use = JsonTypeInfo.Id.NAME, property = "type",visible =true,defaultImpl = InputParameter.class)
@JsonSubTypes ({@JsonSubTypes.Type(value = InputParameter.class, name = "input_parameter")
        , @JsonSubTypes.Type(value = RangeParameter.class, name = "range_parameter"),
        @JsonSubTypes.Type(value = SliderParameter.class, name = "slider_parameter"),
        @JsonSubTypes.Type(value = SelectParameter.class, name = "select_parameter"),
        @JsonSubTypes.Type(value = TableParameter.class, name = "table_parameter"),
})
public class Parameter {
    public String name;
    public String type;
    public String description;
    public String tooltip;
    public String value;
}
