package com.baizhi.wangh.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

  @Id
  @ExcelProperty(value = {"用户","ID"})
  private String id;
  @ExcelProperty(value = {"用户","手机号"})
  private String phone;
  @ExcelProperty(value = {"用户","密码"})
  private String password;
  @ExcelProperty(value = {"用户","盐值"})
  private String salt;
  @ExcelProperty(value = {"用户","状态"})
  private String status;
  @ExcelProperty(value = {"用户","图片"},converter = UserContenver.class)
  private String photo;
  @ExcelProperty(value = {"用户","名字"})
  private String name;
  @ExcelProperty(value = {"用户","昵称"})
  private String nickName;
  @ExcelProperty(value = {"用户","性别"})
  private String sex;
  @ExcelProperty(value = {"用户","个签"})
  private String sign;
  @ExcelProperty(value = {"用户","省份"})
  private String location;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"用户","注册时间"})
  private Date rigestDate;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"用户","最后登录时间"})
  private Date lastLogin;

}
