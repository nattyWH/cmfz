package com.baizhi.wangh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerDto {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Banner> rows;
}
