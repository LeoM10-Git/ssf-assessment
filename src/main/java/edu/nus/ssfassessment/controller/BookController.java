package edu.nus.ssfassessment.controller;


import edu.nus.ssfassessment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{works_id}")
    public String bookDetails(@PathVariable String works_id, Model model) throws IOException {

        model.addAttribute("title", bookService.bookDetails(works_id).getTitle());
        model.addAttribute("description", bookService.bookDetails(works_id).getDescription());
        model.addAttribute("excerpt", bookService.bookDetails(works_id).getExcerpt());
        return "book-detail";
    }

}
