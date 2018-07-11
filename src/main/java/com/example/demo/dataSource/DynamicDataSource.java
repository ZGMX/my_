package com.example.demo.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 项目名称：demo
 * 类名称：${TYPE_NAME}
 * 类描述：动态数据源
 * 创建人：zgh
 * 创建时间：2018/7/5 14:23
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }

}
