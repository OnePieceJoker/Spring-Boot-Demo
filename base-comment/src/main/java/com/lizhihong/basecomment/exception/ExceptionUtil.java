package com.lizhihong.basecomment.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/24
 * @time 22:51:43
 * @description 异常信息文件工具类
 */
@Slf4j
public class ExceptionUtil {

    /**
     * 打印异常信息
     */
    public static String getMessage(Exception e) {
        String swStr = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            swStr = sw.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return swStr;
    }

}