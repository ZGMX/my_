package com.example.demo.beanUtils;


import com.example.demo.beanUtils.annotation.ExcelField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
    private List<String> headers = null;

    public List<String> operateBeans(Object object, int falg) {
        headers = new ArrayList<String>();
        Class<?> orderVoClass = object.getClass();
        Field[] declaredFields = orderVoClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field fieldName = declaredFields[i];
            ExcelField annotation = fieldName.getAnnotation(ExcelField.class);
            if (annotation != null) {
                if (falg == 1) {
                    if ((annotation.title() != null && annotation.type() == 1) || (annotation.title() != null && annotation.completeChannel().equals("ins"))) {
                        headers.add(annotation.title());
                    }
                } else if (falg == 2) {
                    if (annotation.title() != null && annotation.complete().equals("ok")) {
                        if (annotation.title().equals("返佣费(元)")) {
                            headers.add("保险公司返佣费(元)");
                        } else {
                            headers.add(annotation.title());
                        }
                    }
                } else if (falg == 3) {
                    if (annotation.title() != null && annotation.completeChannel().equals("no")) {
                        if (annotation.title().equals("返佣费(元)")) {
                            headers.add("渠道返佣费(元)");
                        } else {
                            headers.add(annotation.title());
                        }
                    }
                } else if (falg == 4) {
                    if ((annotation.title() != null && annotation.type() == 1) || (annotation.title() != null && annotation.completeChannel().equals("channel"))) {
                        if (annotation.title().equals("返佣费(元)")) {
                            headers.add("渠道返佣费(元)");
                        } else {
                            headers.add(annotation.title());
                        }

                    }
                } else {
                    if (annotation.title() != null && annotation.type() == 1) {
                        headers.add(annotation.title());
                    }
                }

            }
        }
        return headers;
    }


}
