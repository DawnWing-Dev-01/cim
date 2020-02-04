package com.dawnwing.framework.core;

public class ErrorCode {

    public final static int BASE_START_CODE = 2010;

    /**
     * 成功的
     */
    public final static int SUCCESS = 0;

    /**
     * 失败的
     */
    public final static int FAILING = -1;

    /**
     * 系统错误
     */
    public final static int ERRCODE_SYS_ERROR = BASE_START_CODE + 1;

    /**
     * 系统失效
     */
    public final static int ERRCODE_SYS_INVALID = BASE_START_CODE + 2;

    /**
     * 数据冗余重复
     */
    public final static int ERRCODE_DATA_REPEAT = BASE_START_CODE + 3;
    
    /**
     * 审核流程没有权限
     */
    public final static int ERRCODE_VERIFY_FLOW_UN_AUTHORITY = BASE_START_CODE + 4;
}
