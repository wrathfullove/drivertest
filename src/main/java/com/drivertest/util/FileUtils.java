package com.drivertest.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jaxw on 12/27/2018.
 */
@Slf4j
public class FileUtils {

    public static void saveFile(InputStream is, String basePath, String fileName) {
        try {
            File file = new File(basePath);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            file = new File(basePath + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
            }
            fos.flush();
            fos.close();

        } catch (IOException e) {
            log.error("存文件失败！");
            e.printStackTrace();
        }
    }

}
