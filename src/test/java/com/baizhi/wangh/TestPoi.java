package com.baizhi.wangh;

import com.baizhi.wangh.dao.BannerDao;
import com.baizhi.wangh.dao.GuruDao;
import com.baizhi.wangh.entity.Guru;
import com.baizhi.wangh.service.ChapterService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPoi {
    @Autowired
    GuruDao guruDao;
    @Autowired
    BannerDao bannerDao;

    @Autowired
    ChapterService chapterService;

    @Test
    public void test1(){
        //创建一个文档
        Workbook workbook = new HSSFWorkbook();
        //给工作簿创建一个名字
        Sheet sheet1 = workbook.createSheet("Sheet1");
        //创建一行(0 是下标)
        Row row = sheet1.createRow(0);
        //创建一个单元格(0 是下标)
        Cell cell = row.createCell(2);
        //给单元格设置内容
        cell.setCellValue("韩耀华");
        try {
            workbook.write(new FileOutputStream(new File("D:\\后期项目\\poi实例\\TestPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        List<Guru> gurus = guruDao.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("上师表");
        Row row = sheet.createRow(0);
        String[] str = {"ID","名字","图片","状态","所属ID"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        //获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //获取数据格式化处理
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //指定格式化样式
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //为样式对象  设置格式化处理
        cellStyle.setDataFormat(format);
        for (int i = 0; i < gurus.size(); i++) {
            Guru guru = gurus.get(i);
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(guru.getId());
            row1.createCell(1).setCellValue(guru.getName());
            row1.createCell(2).setCellValue(guru.getPhoto());
            row1.createCell(3).setCellValue(guru.getStatus());
            row1.createCell(4).setCellValue(guru.getNickName());
        }
        try {
            workbook.write(new File("D:\\后期项目\\poi实例\\上师表.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        List<Guru> gurus = guruDao.selectAll();
        for (Guru guru : gurus) {
            System.out.println(guru);
        }
    }

    @Test
    public void test4(){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }
}
