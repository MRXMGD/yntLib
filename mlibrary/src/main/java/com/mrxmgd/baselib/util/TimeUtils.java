/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *
 *   时间工具类
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class TimeUtils {

    /**
     * 返回MM-dd类型
     *
     * @param time
     * @return
     */
    public static String getTimeMMDD(Long time) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        return dateFormat.format(new Date(time));
    }

    /**
     * 返回yy-MM-dd类型
     *
     * @param time
     * @return
     */
    public static String getTimeYYMMDD(Long time) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date(time));
    }

    /**
     * 返回HH-mm类型
     *
     * @param time
     * @return
     */
    public static String getTimeHHMM(Long time) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(new Date(time));
    }

    /**
     * 返回 MM月dd日 HH:mm:ss类型
     *
     * @param time
     * @return
     */
    public static String getTimeMMDDHHMMSS(Long time) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");
        return dateFormat.format(new Date(time));
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String getTimeYYYYMMDDHHMMSS(Long time) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(time));
    }

    /**
     * 获取指定格式
     *
     * @param time
     * @param format
     * @return
     */
    public static String getFormatTime(Long time, String format) {
        if (time == null) {
            return "error";
        }
        if (time.toString().length() == 10) {
            time *= 1000;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }
}
