package com.example.demo.domain;

import com.example.demo.beanUtils.annotation.ExcelField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public @Data
class Product {
    @ExcelField(title = "主键Id",type = 1,complete = "ok")
    private Long tid;

    @ExcelField(title = "产品编号",type = 1,complete = "ok")
    private String productId;

    @ExcelField(title = "分公司id",type = 1,complete = "ok")
    private Long sonCompanyId;

    @ExcelField(title = "保险公司id",type = 1,complete = "ok")
    private Long companyId;

    @ExcelField(title = "险种",type = 1,complete = "ok")
    private Integer type;

    @ExcelField(title = "有效期",type = 1,complete = "ok")
    private LocalDate contractTime;

    @ExcelField(title = "状态",type = 1,complete = "ok")
    private Integer status;

    @ExcelField(title = "创建人",type = 1,complete = "ok")
    private Long createBy;

    @ExcelField(title = "创建日期",type = 1,complete = "ok")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @ExcelField(title = "修改人",type = 1,complete = "ok")
    private Long updateBy;

    @ExcelField(title = "修改日期",type = 1,complete = "ok")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    @ExcelField(title = "删除标记",type = 1,complete = "ok")
    private Integer delFlag;

}