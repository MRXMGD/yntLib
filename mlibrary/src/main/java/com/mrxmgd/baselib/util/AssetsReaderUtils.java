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

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 从Assets文件夹中读取文件 输出String
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */

public class AssetsReaderUtils {

    public static String getStrFromAssets(Context context, String jsonFileName) {
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(jsonFileName), "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String jsonStr;
            stringBuilder = new StringBuilder();
            while ((jsonStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}