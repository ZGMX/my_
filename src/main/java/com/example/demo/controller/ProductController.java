package com.example.demo.controller;

import com.example.demo.beanUtils.ExcelUtil;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * 项目名称：demo1
 * 类名称：${TYPE_NAME}
 * 类描述：
 * 创建人：zgh
 * 创建时间：2018/7/6 15:25
 */
@Controller
@RequestMapping("/qq")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("hellos")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView("hello");
        Product product = productService.queryProductById(15L);
        modelAndView.addObject("product",product);
        return modelAndView;
    }
    @RequestMapping("a")
    public String dd(){
        return "hello";
    }


    @RequestMapping("downLoad")
    public void downLoad(HttpServletRequest request, HttpServletResponse response){
        List<Product> productList = productService.queryProductList();
        new ExcelUtil().setJspHeader(request,response,new Product(),productList,"产品列表","产品列表导出",1);
    }

    @RequestMapping("downLoads")
    public void downLoads(HttpServletRequest request, HttpServletResponse response){
        List<Product> productList = productService.queryProductLists();
        new ExcelUtil().setJspHeader(request,response,new Product(),productList,"产品列表","产品列表导出",1);
    }

    @RequestMapping("upLoad")
    public String upLoad(MultipartFile myFile){
        try {
            InputStream in = myFile.getInputStream();
            productService.upLoad(in,myFile);
            in.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }

        return "error";
    }
}
