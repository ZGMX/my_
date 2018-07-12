package com.example.demo.service.impl;

import com.example.demo.beanUtils.ConvToEntity;
import com.example.demo.beanUtils.DateEnum;
import com.example.demo.beanUtils.DateUtils;
import com.example.demo.beanUtils.ExcelUtil;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dataSource.TargetDataSource;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：demo1
 * 类名称：${TYPE_NAME}
 * 类描述：
 * 创建人：zgh
 * 创建时间：2018/7/6 16:07
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    @TargetDataSource(name = "ds1")
    public Product queryProductById(long l) {
        return productMapper.selectByPrimaryKey(l);
    }

    @Override
    @TargetDataSource(name = "ds1")
    public List<Product> queryProductList() {
        return productMapper.queryProductList();
    }

    @Override
    public List<Product> queryProductLists() {
        return productMapper.queryProductList();
    }

    @Override
    public void upLoad(InputStream in, MultipartFile myFile) {
        try {
            List<Object[]> list = ExcelUtil.getBankListByExcel(in, myFile.getOriginalFilename());
            List<Product> productList = new ArrayList<>();
            ConvToEntity.convToEntity(list, productList, "com.example.demo.domain.Product");
            System.out.println(productList.size());
            for (Product p : productList) {
                productMapper.insertSelective(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
