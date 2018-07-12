package com.example.demo.dataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：demo
 * 类名称：${TYPE_NAME}
 * 类描述：
 * 创建人：zgh
 * 创建时间：2018/7/5 14:21
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
