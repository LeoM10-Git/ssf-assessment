package edu.nus.ssfassessment.service;

import edu.nus.ssfassessment.exception.ApiRequestException;
import edu.nus.ssfassessment.model.Book;
import edu.nus.ssfassessment.repository.BookRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static edu.nus.ssfassessment.Constants.*;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class.getName());
    @Autowired
    private BookRepository bookRepository;


    public List<Book> search(String title) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder
                .fromUriString(SEARCH_ENDPOINT)
                .queryParam("q", title.trim().replaceAll(" ", "+"))
                .queryParam("field", "*,availability")
                .queryParam("limit", 20)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        /*check response Http Status*/
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ApiRequestException("No found from OpenLibrary API");
        }
        try (InputStream is = new ByteArrayInputStream(response.getBody().getBytes(StandardCharsets.UTF_8))) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray docs = result.getJsonArray("docs");

            return docs.stream()
                    .map(v -> (JsonObject) v)
                    .map(Book::create)
                    .collect(Collectors.toList());
        }
    }

    public Book bookDetails(String id) throws IOException {
        String url = BOOK_ENDPOINT + id + ".json";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new ApiRequestException("No found from OpenLibrary API");
        }

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes(StandardCharsets.UTF_8))) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();

            return Book.bookDetails(result, id);
        }
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public Optional<Book> getBook(String id){
        Optional<Book> book= bookRepository.get(id);
        return book;
    }

}
