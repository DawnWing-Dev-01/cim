package com.dawnwing.framework.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:
 * @author yabin.dong
 * @email: somnium@yahoo.cn
 * @Date: Dec 7, 2012
 * @version: v1.0
 */
public class Message {

    private boolean success = true;

    private String type = ConstGlobal.MESSAGE_TYPE_SUCCESS;

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message(boolean success, String type, String message) {
        this.success = success;
        this.type = type;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}