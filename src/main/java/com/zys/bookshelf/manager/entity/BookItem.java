package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Leo
 * @program bookshelf-manager
 * @description
 * @create 2020-04-14 22:30
 **/
@Table(name = "bookitems")
@Data
@ToString
public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Book book;
    @NotNull(message = "条形码号不能为空")
    @Column(name = "prop_no")
    private String propNo;

    private double price;
    @NotNull(message = "索书号不能为空")
    @Column(name = "call_no")
    private String callNo;

    private Location location;
    @NotNull(message = "入库日期不能为空")
    @Column(name = "in_date")
    private Date inDate;

    private Dictionary status;
}