package com.aguo.blogapi.handle;

import com.aguo.blogapi.exception.UsernameOrPasswordNotExistException;
import com.aguo.blogapi.vo.AGuoResult;
import com.aguo.blogapi.enums.ErrorCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @Author: aguo
 * @DateTime: 2022/4/25 17:29
 * @Description: 所有意外异常处理类
 */
//对所有加了@Controller注解的方法进行拦截处理，其实是AOP的实现

@ControllerAdvice
@ResponseBody
public class AllExceptionHandler {
//    进行异常处理，处理Exception类型的异常
    @ExceptionHandler(value = Exception.class)
    public AGuoResult doException(Exception e) {
        e.printStackTrace();
        return AGuoResult.failed(-999,"系统异常");
    }

    /**Validation类异常处理方法，对所有表单的异常拦截处理。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class})
    public AGuoResult handleValidatedException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            return AGuoResult.failed(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());

        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            ex.printStackTrace();
            return AGuoResult.failed(ErrorCode.NOT_BLANK.getCode(), ErrorCode.NOT_BLANK.getMsg());

        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            ex.printStackTrace();
            return AGuoResult.failed(ErrorCode.NOT_BLANK.getCode(), ErrorCode.NOT_BLANK.getMsg());
        } else if (e instanceof UsernameOrPasswordNotExistException){
            return AGuoResult.failed(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        return AGuoResult.failed(-999,"系统异常");
    }


}
