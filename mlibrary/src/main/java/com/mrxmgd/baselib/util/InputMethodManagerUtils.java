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
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/*
 *
 *   输入法工具类
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class InputMethodManagerUtils {
    // toogle输入法
    public static void toggleInputMethod(View view, Context context) {

        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 进行取反
        inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 显示输入法
    public static void showInputMethod(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        //同时再使用该方法之前，view需要获得焦点，可以通过requestFocus()方法来设定。
        view.requestFocus();
        inputMethodManager.showSoftInput(view, inputMethodManager.SHOW_FORCED);
    }

    //隐藏输入法
    public static void hidenInputMethod(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //判断输入法是否已经打开
    public static boolean isInputMethodOpened(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }
}
