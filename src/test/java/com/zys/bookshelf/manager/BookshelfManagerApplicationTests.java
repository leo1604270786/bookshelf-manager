package com.zys.bookshelf.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zys.bookshelf.manager.dto.BaseResult;
import com.zys.bookshelf.manager.dto.CategoryCountDTO;
import com.zys.bookshelf.manager.dto.UserDTO;
import com.zys.bookshelf.manager.entity.*;
import com.zys.bookshelf.manager.mapper.*;
import com.zys.bookshelf.manager.service.BookService;
import com.zys.bookshelf.manager.service.BorrowService;
import com.zys.bookshelf.manager.service.DictionaryService;
import com.zys.bookshelf.manager.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookshelfManagerApplication.class)
@Transactional
@Rollback
class BookshelfManagerApplicationTests {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private BookService bookService;
	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private BorrowMapper borrowMapper;

	@Test
	public void testDigest(){
		String s = DigestUtils.md5DigestAsHex("123".getBytes());
		System.out.println(s);
	}

	@Test
	public void testSelectAll(){
		List<User> users = userService.selectAll();
		for (User user : users) {
			System.out.println(user);
		}
	}
	@Autowired
	private BorrowService borrowService;

	@Test
	public void testPage(){
		Borrow borrow1 = new Borrow();
		Book book = new Book();
		User user = new User();
		book.setCode("");
		book.setName("");
		user.setCode("");
		user.setName("");
		borrow1.setBook(book);
		borrow1.setReader(user);
		borrow1.setCallNo("");
		PageInfo<Borrow> page = borrowService.page(1, 5, borrow1);
		List<Borrow> list = page.getList();
		for (Borrow borrow : list) {
			System.out.println(borrow);
		}
//		PageHelper.startPage(1,5);
//		PageInfo<Borrow> borrowPageInfo = new PageInfo<>(borrowMapper.selectByCondition(new Borrow()));
//		List<Borrow> borrows = borrowPageInfo.getList();
//		for (Borrow borrow : borrows) {
//			System.out.println(borrow);
//		}
//		PageInfo<Book> pageInfo = new PageInfo<>(bookMapper.selectByCondition(new Book()));
//		List<Book> books = pageInfo.getList();
//		for (Book book : books) {
//			System.out.println(book);
//		}
//		PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectAll());
//		List<User> users = pageInfo.getList();
//		for (User user : users) {
//			System.out.println(user);
//		}
//		System.out.println("总记录数：" + pageInfo.getSize());
	}
	@Test
	public void testPage2() {
		PageInfo<User> pageInfo = userService.page(2, 5, null);
		for (User user : pageInfo.getList()){
			System.out.println(user);
		}
	}
	@Test
	public void testBookPage(){
		PageInfo<Book> pageInfo = bookService.page(1, 5, null);
		for (Book book : pageInfo.getList()){
			System.out.println(book);
		}
	}
	@Test
	public void testUpdate(){
		User user = new User();
		user.setId(24);
		user.setCode("1");
		user.setName("123");
		user.setGender("女");
		Dictionary dictionary = new Dictionary();
		dictionary.setId(6);
		user.setRole(dictionary);
		user.setPhone("12312312312");
		user.setEmail("123@qq.com");
		user.setIdcard("123");
		try {
			BaseResult baseResult = userService.updateUser(user);
			System.out.println(baseResult.getStatus() + " ====" + baseResult.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetByType(){
		List<Dictionary> bookStatus = dictionaryService.getDictionaryByType("bookStatus");
		for (Dictionary status : bookStatus) {
			System.out.println(status);
		}
	}
	@Test
	public void testBorrowMapper(){
		Borrow bo = new Borrow();
		bo.setReader(new User());
		bo.setBook(new Book());
		List<Borrow> borrows = borrowMapper.selectByCondition(bo);
		for (Borrow borrow : borrows) {
			System.out.println(borrow);
		}
	}

//	@Test
//	public void testGetCountByCategory() {
//		List<CategoryCountDTO> countByCategory = bookMapper.getCountByCategory();
//		for (CategoryCountDTO categoryCountDTO : countByCategory) {
//			System.out.println(categoryCountDTO);
//		}
//	}

	@Test
	public void testLogin(){
		BaseResult res = userService.login("L0001", "123");
		System.out.println(res);
	}
	@Autowired
	private BookshelfMapper bookshelfMapper;
	@Test
	public void testBookshelfSelectAll(){
		List<Bookshelf> bookshelves = bookshelfMapper.selectAll();
		for (Bookshelf bookshelf : bookshelves) {
			System.out.println(bookshelf);
		}
	}
	@Test
	public void testGetAllPlace(){
		List<String> allPlace = bookshelfMapper.getAllPlace();
		for (String s : allPlace) {
			System.out.println(s);
		}
	}
	@Test
	public void testGetByPlace(){
		List<Bookshelf> byPlace = bookshelfMapper.getByPlace("借阅室101");
		for (Bookshelf bookshelf : byPlace) {
			System.out.println(bookshelf);
		}
	}
	@Test
	public void testByBookCode(){
		Bookshelf bookshelf = bookshelfMapper.getByBookCallNo("A002");
		System.out.println(bookshelf);
	}
	@Test
	public void testSdf(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String format = simpleDateFormat.format(new Date());
		System.out.println(format);
	}
	@Autowired
	private CategoryMapper categoryMapper;
	@Test
	public void testCategoryMapper(){
		List<Category> categories = categoryMapper.selectAll();
		for (Category category : categories) {
			System.out.println(category);
		}
	}

	@Test
	public void testSelectAllBooks(){
		List<Book> books = bookMapper.selectAllBooks();
//		for (Book book : books) {
//			System.out.println(book);
//		}
		System.out.println(books.size());
	}
	
	@Test
	public void testFormat(){
		System.out.println(String.format("%03d",1));
	}

	@Test
	public void testCount(){
		System.out.println(bookshelfMapper.selectCount(new Bookshelf()));
	}
}
