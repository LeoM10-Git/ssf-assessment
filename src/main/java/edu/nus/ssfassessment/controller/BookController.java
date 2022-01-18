package edu.nus.ssfassessment.controller;


import edu.nus.ssfassessment.exception.ApiRequestException;
import edu.nus.ssfassessment.model.Book;
import edu.nus.ssfassessment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
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
        Optional<Book> book = bookService.getBook(works_id);
        String title = null;
        String description = null;
        String excerpt = null;
        String cover = null;
        Boolean cached = false;

        if (book.isPresent()){
            title = book.get().getTitle();
             description = book.get().getDescription();
            excerpt = book.get().getExcerpt();
            cover = book.get().getCover();
            cached = true;
        }else{
            try {
                title = bookService.bookDetails(works_id).getTitle();
                description = bookService.bookDetails(works_id).getDescription();
                excerpt = bookService.bookDetails(works_id).getExcerpt();
                cover = bookService.bookDetails(works_id).getCover();
                bookService.saveBook(bookService.bookDetails(works_id));
            }catch (Exception e){
                logger.log(Level.WARNING, "Warning: %s".formatted(e.getMessage()));
            }
        }
        if (cover.equals("NO")){
            model.addAttribute("coverUrl", cover);
            logger.info("No Cover image");
        }else{model.addAttribute("coverUrl",
                BOOK_COVER_URL + cover + ".jpg");}

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("excerpt", excerpt);
        model.addAttribute("cached", cached);
        return "book-detail";
    }

}
