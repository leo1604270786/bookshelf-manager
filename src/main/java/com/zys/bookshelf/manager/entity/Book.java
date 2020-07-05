package com.zys.bookshelf.manager.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "book")
@ToString
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图书编号
     */
    @NotNull(message = "图书编号不能为空")
    @Column(name = "bcode")
    private String code;

    @NotNull(message = "索书号不能为空")
    @Column(name = "call_no")
    private String callNo;
    /**
     * 图书名称
     */
    @NotNull(message = "图书名称不能为空")
    private String name;

    /**
     * 作者
     */
    @NotNull(message = "作者不能为空")
    private String author;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版日期
     */
    @Column(name = "publish_year")
    private String publishYear;

    private Category category;
}