package com.example.demo.dataSource;

import java.lang.annotation.*;

/**
 * 项目名称：demo
 * 类名称：${TYPE_NAME}
 * 类描述：在方法上使用，用于指定使用哪个数据源
 * 创建人：zgh
 * 创建时间：2018/7/5 14:24
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
