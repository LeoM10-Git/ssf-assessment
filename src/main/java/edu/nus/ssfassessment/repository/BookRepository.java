package edu.nus.ssfassessment.repository;

import edu.nus.ssfassessment.model.Book;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class BookRepository {
    public static final String HASH_KEY = "Book";
    private RedisTemplate redisTemplate;

    public BookRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Book book){
        redisTemplate.opsForHash().put(HASH_KEY, book.getId(), book);
        redisTemplate.expire(HASH_KEY, 10L, TimeUnit.MINUTES);
    }

    public Optional<Book> get(String id){
       return Optional.ofNullable((Book)redisTemplate.opsForHash().get(HASH_KEY, id));
    }












}
