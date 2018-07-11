package com.example.demo.beanUtils;

import com.example.demo.beanUtils.annotation.ExcelField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtil {

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel


    public XSSFWorkbook getXlsxWorkbook(List<String> headers, List<?> datalist, String fileName, int flag) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(fileName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 10);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.size(); i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }
        int index = 0;
        // 遍历集合数据，产生数据行
        for (int i = 0; i < datalist.size(); i++) {
            index++;
            row = sheet.createRow(index);
            Object object = datalist.get(i);
            Class<?> aClass = object.getClass();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] declaredFields = object.getClass().getDeclaredFields();
            if (declaredFields != null) {
                int columnindex = 0;
                for (int j = 0; j < declaredFields.length; j++) {
                    ExcelField annotation = declaredFields[j].getAnnotation(ExcelField.class);
                    if (annotation != null) {
                        if (flag == 2) {
                            if (annotation.title() != null && annotation.complete().equals("ok")) {
                                columnindex = this.operetaBean(declaredFields, aClass, row, columnindex, j, object);
                            }
                        } else if (flag == 1) {
                            if ((annotation.title() != null && annotation.type() == 1) || (annotation.title() != null && annotation.completeChannel().equals("ins"))) {
                                columnindex = this.operetaBean(declaredFields, aClass, row, columnindex, j, object);
                            }
                        } else if (flag == 3) {
                            if (annotation.title() != null && annotation.completeChannel().equals("no")) {
                                columnindex = this.operetaBean(declaredFields, aClass, row, columnindex, j, object);
                            }
                        } else if (flag == 4) {
                            if ((annotation.title() != null && annotation.type() == 1) || (annotation.title() != null && annotation.completeChannel().equals("channel"))) {
                                columnindex = this.operetaBean(declaredFields, aClass, row, columnindex, j, object);
                            }
                        } else {
                            columnindex = this.operetaBean(declaredFields, aClass, row, columnindex, j, object);
                        }

                    }
                }
            }
        }
        return workbook;
    }

    public void setJspHeader(HttpServletRequest request, HttpServletResponse response, Object object, List<?> orderList, String excelName, String fileName, int flag) {
        List<String> stringStringMap = new BeanUtils().operateBeans(object, flag);
        XSSFWorkbook workbook = this.getXlsxWorkbook(stringStringMap, orderList, excelName, flag);
        //String fileName = "保险公司对账数据导出 " + DateUtils.dateToStr(new Date(), DateEnum.DATE_BANK_SEQ);
        response.reset();
        try {
            //导出支持中文名，兼容IE、火狐
            if (request.getHeader("user-agent").indexOf("MSIE") != -1) {
                fileName = java.net.URLEncoder.encode(fileName, "utf-8") + ".xlsx";
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1") + ".xlsx";
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int operetaBean(Field[] declaredFields, Class<?> aClass, XSSFRow row, int columnindex, int j, Object object) {
        XSSFCell cell = row.createCell(columnindex++);
        log.info("columnindex" + columnindex + "j" + j);
        String name = declaredFields[j].getName();
        String methodname = name.substring(0, 1).toUpperCase() + name.substring(1);
        String gettername = "get" + methodname;
        try {
            Method getmethod = aClass.getMethod(gettername);
            Object value = getmethod.invoke(object);
            if (StringUtils.isBlank(value + "")) {
                XSSFRichTextString richString = new XSSFRichTextString("");
                cell.setCellValue(richString);
            } else if ("null".equals(value + "")) {
                XSSFRichTextString richString = new XSSFRichTextString("");
                cell.setCellValue(richString);
            } else if ("undefined".equals(value + "")) {
                XSSFRichTextString richString = new XSSFRichTextString("");
                cell.setCellValue(richString);
            } else {
                XSSFRichTextString richString = new XSSFRichTextString(value + "");
                cell.setCellValue(richString);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return columnindex;
    }


    /*************************************文件上传****************************/
    public static List<Object[]> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<Object[]> list = null;

        //创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<Object[]>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //遍历所有的列
                Object[] li = new Object[row.getLastCellNum()];
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li[y] = getCellValue(cell);
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        value = df.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        value = df2.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                default:
                    break;
            }
        }
        return value;
    }

}
