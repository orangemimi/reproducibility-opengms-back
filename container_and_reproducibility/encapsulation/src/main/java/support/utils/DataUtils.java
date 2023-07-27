package support.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.util.HashMap;

/**
 * @ClassName DataUtils
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
public class DataUtils {

    private static final String REMOTE_URL="http://localhost:8081/";

    public static void  download(String id, File file){
        String url=REMOTE_URL+"/data_service/fetch/"+id;
        HttpUtil.downloadFile(url, file);
    }

    public static String  upload(File file){
        String url=REMOTE_URL+"/data_service/addByFile";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", file);
        return JSONUtil.parseObj(HttpUtil.post(url, paramMap)).getJSONObject("data").getStr("id");
    }
}
