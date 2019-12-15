package com.xmu.discount.hander;

import com.xmu.discount.exception.*;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouzhongjun
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object badArgumentException()
    {
        return ResponseUtil.badArgument();
    }

    @ExceptionHandler(value = BadArgumentValueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object badArgumentValueException()
    {
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(value = InvalidOperationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object invalidOperationException()
    {
        return ResponseUtil.fail();
    }

    @ExceptionHandler(value = SeriousException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object seriousException()
    {
        return ResponseUtil.serious();
    }
    @ExceptionHandler(value = UnsupportException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object unsupportException()
    {
        return ResponseUtil.unsupport();
    }
    @ExceptionHandler(value = UpdatedDataFailedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object updatedDataFailedException()
    {
        return ResponseUtil.updatedDataFailed();
    }
    @ExceptionHandler(value = UpdatedDateExpiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object updatedDateExpiredException()
    {
        return ResponseUtil.updatedDateExpired();
    }

}

