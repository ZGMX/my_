package com.example.demo.dao;

import com.example.demo.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "productMapper")
public interface ProductMapper {
    int deleteByPrimaryKey(Long tid);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> queryProductList();
}