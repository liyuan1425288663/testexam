package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyException;
import com.leyou.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @author ly
 * @create 2019-03-06  16:07
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private UploadProperties prop;

    // private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpg","image/png","image/bmp");

    public String uploadImage(MultipartFile file){


        try {
            //校验文件
            String contentType = file.getContentType();
            if (!prop.getAllowTypes().contains(contentType)){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //效验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image==null){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
          /*  //准备目标路径
            File dest = new File("D:\\IMAGE\\image\\",file.getOriginalFilename());*/
            /*// 保存文件到本地
            file.transferTo(dest);*/
            //上传到FastDFS
            /* String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);*/
            //获取图片后缀
            String extension =StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //返回路径
            return prop.getBaseUrl() +storePath.getFullPath();
        } catch (IOException e) {
            //上传失败
            log.error("[文件上传]上传失败！",e);
            throw new LyException(ExceptionEnum.UPLOAD_FILE_TYPE_ERROR);
        }finally {

        }


    }

}
