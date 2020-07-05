package com.zys.bookshelf.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.zys.bookshelf.manager.mapper")
@SpringBootApplication
public class BookshelfManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshelfManagerApplication.class, args);
	}

}
