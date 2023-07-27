package edu.njnu.opengms.container.domain.data;

import com.google.common.collect.Lists;
import edu.njnu.opengms.common.controller.BaseController;
import edu.njnu.opengms.common.domain.container.data.DataService;
import edu.njnu.opengms.common.dto.SplitPageDTO;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.JsonResult;
import edu.njnu.opengms.common.utils.ResultUtils;
import edu.njnu.opengms.container.domain.data.dto.AddDataServiceDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName DataController
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/4/29
 * @Version 1.0.0
 */
@RestController
@RequestMapping ("/data_service")
public class DataServiceControllerImpl implements BaseController<AddDataServiceDTO, SplitPageDTO,DataService> {
    @Autowired
    DataRepository dataRepository;

    @Value ("${storages.local.path}")
    String pathString;

    @Autowired
    FileStorageRepository fileStorageRepository;


    @RequestMapping (value = "/listByIds", method = RequestMethod.GET)
    public JsonResult listByIds(@RequestParam("ids")List<String>ids){
        return ResultUtils.success(Lists.newArrayList(dataRepository.findAllById(ids)));
    }

    @RequestMapping (value = "/all", method = RequestMethod.GET)
    public JsonResult findAll(){
        return ResultUtils.success(dataRepository.findAll());
    }


//    @RequestMapping (value = "/toGeoserver/{id}",method = RequestMethod.POST)
//    JsonResult toGeoserver(@PathVariable ("id") String   id) throws FileNotFoundException, MalformedURLException, URISyntaxException {
//        DataService dataService = dataRepository.findById(id).orElseThrow(MyException::noObject);
//        if(dataService.getType().equals(DataEnums.SHAPEFILE)||dataService.getType().equals(DataEnums.GEOTIFF)){
//            if(dataService.getGeoserverEntrance()!=null){
//                return ResultUtils.success("当前数据OGC相关服务已经发布");
//            }
//            String key=dataService.getKey();
//            List<File> files = FileUtil.loopFiles(String.valueOf(Paths.get(pathString).resolve(key)));
//            String shpFile = files.get(0).getAbsolutePath();
//            String layerName = FilenameUtils.getBaseName(shpFile);
//            if(GeoserverUtil.publishShapefile(key, layerName,shpFile)){
//                dataService.setGeoserverEntrance("container:"+layerName);
//                dataRepository.save(dataService);
//                return ResultUtils.success("发布OGC相关服务成功");
//            }else{
//                throw new MyException("发布OGC失败");
//            }
//        }else{
//           throw new MyException("该数据不支持OGC相关服务");
//        }
//    }

    @RequestMapping (value = "/fetch/{id}",method = RequestMethod.GET)
    ResponseEntity<Resource> fetch(@PathVariable ("id") String   id) throws URISyntaxException {
        String key=dataRepository.findById(id).orElseThrow(MyException::noObject).getKey();
        FileStorage fileStorage=fileStorageRepository.findByKey(key).orElse(null);
        Path file=Paths.get(pathString).resolve(key).resolve(fileStorage.getName());
        Resource resource;
        URI uri=new URI(null,null,fileStorage.getName(),null);
        try {
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                MediaType mediaType;
                if (fileStorage.getContentType() == null || fileStorage.getContentType().equals("null")) {
                    mediaType = null;
                } else {
                    mediaType = MediaType.parseMediaType(fileStorage.getContentType());
                }
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName="+uri.toASCIIString())
                        .body(resource);
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }


    @RequestMapping (value = "/addByFileToPublic",method = RequestMethod.POST)
    public JsonResult addByFileToPublic(@RequestPart ("file") MultipartFile file,
    @RequestPart ("name") String name,
//    @RequestPart ("resourceUrl") String resourceUrl,
    @RequestPart ("description") String description
    ) throws IOException {
        FileStorage insert = fileStorageRepository.insert(uploadFile(file));
        DataService dataService=new DataService();
        AddDataServiceDTO a = AddDataServiceDTO.builder().key(insert.getKey())
                .name(name)
//                .resourceUrl(resourceUrl)
                .description(description)
                .isIntermediate(false)
                .build();
        a.setKey(insert.getKey());
        a.convertTo(dataService);
        DataService  insert1= dataRepository.insert(dataService);
        JsonResult success = ResultUtils.success(insert1);
        return success;
    }

    @RequestMapping (value = "/addByFile",method = RequestMethod.POST)
    public JsonResult addByFile(@RequestPart ("file") MultipartFile file) throws IOException {
        FileStorage insert = fileStorageRepository.insert(uploadFile(file));
        DataService dataService=new DataService();
        //加入时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        String baseName = FilenameUtils.getBaseName(insert.getName());
        String extension = FilenameUtils.getExtension(insert.getName());
        AddDataServiceDTO a = AddDataServiceDTO.builder().key(insert.getKey()).name(baseName + "_" + date + "." + extension).build();
        a.setKey(insert.getKey());
        a.convertTo(dataService);
        DataService  insert1= dataRepository.insert(dataService);
        JsonResult success = ResultUtils.success(insert1);
        return success;
    }

    private FileStorage uploadFile(MultipartFile file) throws IOException {
        String key= UUID.randomUUID().toString();
        String name=file.getOriginalFilename();
        File dir=new File(String.valueOf(Paths.get(pathString).resolve(key)));
        dir.mkdir();
        Files.copy(file.getInputStream(), Paths.get(pathString).resolve(key).resolve(name), StandardCopyOption.REPLACE_EXISTING);
        return FileStorage.builder().key(key).name(name).contentType(file.getContentType()).size(file.getSize()).build();
    }


    @Override
    public JsonResult add(AddDataServiceDTO a) {
        DataService dataService=new DataService();
        a.convertTo(dataService);
        return ResultUtils.success(dataRepository.insert(dataService));
    }


    @Override
    public JsonResult list(SplitPageDTO findDTO) {
        //返回定义了描述方法的，定义为了前端可展示的数据服务
        return ResultUtils.success(dataRepository.findAllByDescriptionIsNotNull(findDTO.getPageable()));
    }

    @Override
    public JsonResult get(String id) {
        return  ResultUtils.success(dataRepository.findById(id).orElse(null));
    }


    @Override
    public JsonResult count() {
        return ResultUtils.success(dataRepository.count());
    }

    @Override
    public JsonResult delete(String id) {
        dataRepository.deleteById(id);
        return ResultUtils.success("删除成功");
    }


    @Override
    public JsonResult update(String id, DataService updateDTO) {
        //TODO
        return null;
    }
}
