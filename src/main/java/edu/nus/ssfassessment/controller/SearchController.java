package edu.nus.ssfassessment.controller;


import edu.nus.ssfassessment.config.AppConfig;
import edu.nus.ssfassessment.model.Book;
import edu.nus.ssfassessment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
public class SearchController {
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private BookService bookService = new BookService();

    @GetMapping("/result")
    public String getResult(Model model, @RequestParam("title")String title) throws IOException {
        logger.info("Books found>>>>   %s".formatted(String.valueOf(bookService.search(title).size())));
        for (Book book : bookService.search(title)){
            logger.info("Books works_id>>  "+ book.getId());
        }

        if (bookService.search(title).size() == 0){
            model.addAttribute("info",
                    "No book found in the library, please try other titles");
        }
        model.addAttribute("searchTitle", title.toLowerCase());
        model.addAttribute("books", bookService.search(title));


        return "search-result";
    }


}
