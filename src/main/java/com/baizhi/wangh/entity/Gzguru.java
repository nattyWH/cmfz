package com.baizhi.wangh.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gzguru implements Serializable {

  @Id
  private String id;
  private String user_id;
  private String guru_id;

}
