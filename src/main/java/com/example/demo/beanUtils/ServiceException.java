package com.example.demo.beanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Service层公用的Exception.
 * <p>
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
@Slf4j
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 3583566093089790852L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        log.error(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
        log.error("抛出异常！", cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
