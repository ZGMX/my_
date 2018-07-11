package com.example.demo.beanUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public class ConvToEntity {
    public static <T> void convToEntity(List<Object[]> srcLst, List<T> lst, String clsName) {

        try {
            Class cls = Class.forName(clsName);


            for (Object[] srcArr : srcLst) {
                //反射动态创建实例。
                Object obj = cls.newInstance();

                //实体中定义的全部属性。
                Field[] fedAtt = cls.getDeclaredFields();

                for (int i = 0; i < srcArr.length; i++) {
                    //属性名
                    String attName = fedAtt[i].getName();
                    if (attName.equals("tid")) {
                        continue;
                    }
                    //单词的首字母
                    String firChar = String.valueOf(attName.charAt(0));

                    //将属性单词的首字母变大写
                    char[] strChar = attName.toCharArray();
                    strChar[0] -= 32;
                    String attWord = String.valueOf(strChar);

                    //属性的setter方法
                    Method mth = cls.getDeclaredMethod("set" + attWord, fedAtt[i].getType());
                    String type = fedAtt[i].getType().getName();
                    if (srcArr[i] == null || srcArr[i] == "") {
                        continue;
                    }
                    switch (type) {
                        case "java.lang.Long":
                            mth.invoke(obj, Long.parseLong(srcArr[i].toString()));
                            break;
                        case "java.lang.String":
                            mth.invoke(obj, srcArr[i].toString());
                            break;
                        case "java.util.Date":
                            mth.invoke(obj, DateUtils.toDate(srcArr[i].toString(), DateEnum.DATE_SIMPLE));
                            break;
                        case "java.lang.Integer":
                            mth.invoke(obj, Integer.parseInt(srcArr[i].toString()));
                            break;
                        case "java.math.BigDecimal":
                            mth.invoke(obj, new BigDecimal(Integer.parseInt(srcArr[i].toString())));
                            break;
                        case "java.time.LocalDateTime":
                            mth.invoke(obj, DateUtils.stringToLocalDateTime(srcArr[i].toString()));
                            break;
                        case "java.time.LocalDate":
                            mth.invoke(obj, DateUtils.stringToLocalDate(srcArr[i].toString()));
                            break;
                        default:
                            break;
                    }

                    //属性的setter方法
                    Method mth1 = cls.getDeclaredMethod("get" + attWord, null);

                }

                lst.add((T) obj);
            }


        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
