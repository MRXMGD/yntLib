/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.base;
/*
 *
 *
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    private int code = -1;
    @SerializedName("msg")
    private String message = "";
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
