package com.bjfu.fcro.common.utils;

import com.bjfu.fcro.common.entity.JsonResult;
import com.bjfu.fcro.common.enums.ResultCode;

import javax.xml.crypto.Data;

/**
 * @Program: ltx_web
 * @ClassName: ResultTool
 * @Author: 李腾旭
 * @Date: 2020-06-12 12:52
 * @Description: ${todo}
 * @Version: V1.0
 */
public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
    public static <T>JsonResult<T> fail(T data) {
        return new JsonResult(false, data);
    }
}