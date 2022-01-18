package edu.nus.ssfassessment.controller;


import edu.nus.ssfassessment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.logging.Logger;

import static edu.nus.ssfassessment.Constants.*;

@Controller
public class BookController {
    private final Logger logger = Logger.getLogger(BookController.class.getName());
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{works_id}")
    public String bookDetails(@PathVariable String works_id, Model model) throws IOException {

        logger.info("Book cover id >>> " + bookService.bookDetails(works_id).getCover());
        logger.info("Book coverURL >>> " +
                BOOK_COVER_URL + bookService.bookDetails(works_id).getCover() + ".jpg");
        model.addAttribute("coverUrl",
                BOOK_COVER_URL + bookService.bookDetails(works_id).getCover() + ".jpg");
        model.addAttribute("title", bookService.bookDetails(works_id).getTitle());
        model.addAttribute("description", bookService.bookDetails(works_id).getDescription());
        model.addAttribute("excerpt", bookService.bookDetails(works_id).getExcerpt());
        return "book-detail";
    }

}
