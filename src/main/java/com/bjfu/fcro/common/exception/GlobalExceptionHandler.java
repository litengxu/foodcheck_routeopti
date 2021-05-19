package com.bjfu.fcro.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 注解会自动获取controller层的异常，不用再写throws语句
 * 需要注意的是，对于404，500这样的异常不能捕获，需要实现ErrorController接口，就是另一种方式了
 * */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
//     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public  ResultBody bizExceptionHandler(BizException e){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return ResultBody.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultBody nullpointexceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    /**
     * 传递非法参数异常。
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =IllegalArgumentException  .class)
    @ResponseBody
    public ResultBody illegalargumentexceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("传递非法参数异常。！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    /**
     * 处理l类型强制转换的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =ClassCastException .class)
    @ResponseBody
    public ResultBody classcastexceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生类型强制转换异常！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }
    /**
     * 下标越界异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =IndexOutOfBoundsException .class)
    @ResponseBody
    public ResultBody indexoutofboundsexceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生类型强制转换异常！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }
    /**
     * 下标越界异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =SQLException.class)
    @ResponseBody
    public ResultBody SQLExceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生sql异常！原因是:",e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }
    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}