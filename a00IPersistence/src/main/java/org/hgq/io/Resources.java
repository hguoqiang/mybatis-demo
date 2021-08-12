package org.hgq.io;

import java.io.InputStream;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 14:56
 **/
public class Resources {
    public static InputStream getResourceAsStream(String path){

        //根据配置文件的路径，将配置文件转成字节流，存到内存中
        InputStream in = Resources.class.getClassLoader().getResourceAsStream(path);
        return in;

    }
}
