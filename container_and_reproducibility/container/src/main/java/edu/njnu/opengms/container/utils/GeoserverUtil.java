//package edu.njnu.opengms.container.utils;
//
//import it.geosolutions.geoserver.rest.GeoServerRESTManager;
//import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
//import org.apache.commons.io.FilenameUtils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
//import java.net.URL;
//
///**
// * @ClassName GeoserverUtil
// * @Description ass we see
// * @Author sun_liber
// * @Date 2020/4/29
// * @Version 1.0.0
// */
//public class GeoserverUtil {
//    private static final String url = "http://localhost:8080/geoserver";
//    private static final String admin = "admin";
//    private static final String password = "geoserver";
//    private static final String workspace = "container";
//
//    public static boolean publishShapefile(String storeName, String layerName,String shpFile) throws URISyntaxException, MalformedURLException, FileNotFoundException {
//        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(url), admin, password);
//        GeoServerRESTPublisher publisher = manager.getPublisher();
//        return publisher.publishShp(workspace, storeName,  layerName, new File(shpFile));
//    }
//}
