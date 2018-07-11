package com.example.demo.service;

import com.example.demo.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 项目名称：demo1
 * 类名称：${TYPE_NAME}
 * 类描述：
 * 创建人：zgh
 * 创建时间：2018/7/6 16:07
 */
public interface ProductService {
    Product queryProductById(long l);

    List<Product> queryProductList();

    List<Product> queryProductLists();

    void upLoad(InputStream in, MultipartFile myFile);
}
