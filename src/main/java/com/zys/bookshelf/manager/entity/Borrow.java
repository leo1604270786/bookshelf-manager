package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "borrow")
@ToString
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prop_no")
    @NotNull(message = "条形码号不能为空")
    private String propNo;

    @Column(name = "call_no")
    @NotNull(message = "索书号不能为空")
    private String callNo;

    /**
     * 读者编号
     */
    private User reader;

    /**
     * 图书编号
     */
    private Book book;

    private Location location;

    private Dictionary status;
    /**
     * 借阅日期
     */
    @Column(name = "borrow_date")
    @NotNull(message = "借阅日期不能为空")
    private Date borrowDate;
    /*
    *  归还日期
    * */
    @Column(name = "return_date")
    private Date returnDate;

}