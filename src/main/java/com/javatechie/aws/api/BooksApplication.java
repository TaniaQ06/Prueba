package com.javatechie.aws.api;

import com.amazonaws.util.StringUtils;
import com.book.service.BookServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
@RequestMapping("/book")
public class BooksApplication {

    private BookServiceImpl bookService = new BookServiceImpl();

    @PostMapping
    @ResponseBody
    public Object addBook(@RequestBody Book book) {
        if (StringUtils.isNullOrEmpty(book.getId()) ||
                StringUtils.isNullOrEmpty(book.getName()) ||
                StringUtils.isNullOrEmpty(book.getPrice()) ){
            return new ResponseEntity<>("The body request is not complete", HttpStatus.BAD_REQUEST);
        }
        try {
            bookService.save(book);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving the book" + e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return book;
    }

	@GetMapping
    @ResponseBody
    public Object getBooks() {
		try {
			return bookService.getAllBooks();
		} catch (Exception e) {
            return new ResponseEntity<>("Error getting all the books " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}

    @GetMapping("/{id}")
    @ResponseBody
    public Object getBook(@PathVariable("id") String id) {
        try {
            return bookService.get(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting the book with the id: " + id  + e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Object deleteBook(@PathVariable("id") String id) {

        try {
            return bookService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting a book" ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Object updateBook(@PathVariable("id") String id, @RequestBody Book book) {
        if (StringUtils.isNullOrEmpty(book.getId()) ||
                StringUtils.isNullOrEmpty(book.getName()) ||
                StringUtils.isNullOrEmpty(book.getPrice()) ){
            return new ResponseEntity<>("The body request is not complete", HttpStatus.BAD_REQUEST);
        }
        try {
            return bookService.update(id, book);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating the book" + e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

}
