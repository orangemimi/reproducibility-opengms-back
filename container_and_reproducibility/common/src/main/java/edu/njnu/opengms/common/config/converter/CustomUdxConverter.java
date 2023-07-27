package edu.njnu.opengms.common.config.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @ClassName CustomUdxConverter
 * @Description todo
 * @Author sun_liber
 * @Date 2019/11/19
 * @Version 1.0.0
 */
public class CustomUdxConverter extends AbstractHttpMessageConverter<Object> {

    public CustomUdxConverter() {
        super(Charset.forName("UTF-8"), new MediaType("application", "udx"));
    }

    @Override
    protected boolean supports(Class aClass) {
        return true;
    }

    @Override
    protected Object readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String s = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        return s;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getBody().write("hello,udx for text".getBytes());
    }
}
