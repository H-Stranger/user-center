package com.hxd.common;

public class ResultUtils {
    /**
     * 成功
     * @param data 数据
     * @return 统一对象
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,"success",data);
    }
    /**
     * 失败（失败码）
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败（无数据）
     * @param code
     * @param message
     * @param description
     * @return
     * @param <T>
     */
    public static<T> BaseResponse<T> error(int code,String message,String description){
        return new BaseResponse<>(code,message,null,description);
    }

    /**
     * 失败（系统状态码 + 自定义message）
     * @param errorCode
     * @param message
     * @param description
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getCode(),message,null,description);
    }

    /**
     * 失败（详细信息）
     * @param errorCode
     * @param description
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode,String description){
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage(),null,description);
    }

}
