package com.xuie.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭文件IO流
 *
 * @author xuie
 * @date 2017/5/17 0017
 */

public class IoUtils {
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
