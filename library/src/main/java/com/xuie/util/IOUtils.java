package com.xuie.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭文件IO流
 * Created by xuie on 2017/5/17 0017.
 */

public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
