package edu.nus.ssfassessment.model;

import jakarta.json.JsonObject;

public class Book {
    private String key;
    private String id;
    private String title;
    private String cover;
    private String description;
    private String excerpt;

    public Book() {
    }




    public Book(String key, String id , String title, String cover, String description, String excerpt) {
        this.key = key;
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.excerpt = excerpt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public static Book create(JsonObject object) {
        final Book book = new Book();
        book.setTitle(object.getString("title"));
        book.setKey(object.getString("key"));
        book.setId(object.getString("key").replace("/works/", ""));
        if (object.containsKey("title") )
            book.setTitle(object.getString("title"));
        if (object.containsKey("key"))
            book.setKey(object.getString("key"));

        return book;
    }


    public static Book bookDetails(JsonObject object) {
        Book detailedBook = new Book();
        detailedBook.setTitle(object.getString("title"));
        detailedBook.setDescription(object.getString("description"));
        detailedBook.setExcerpt(object.getJsonArray("excerpts")
                .getJsonObject(0)
                .getString("excerpt"));
        detailedBook.setCover(object.getJsonArray("covers").get(0).toString());
        return detailedBook;

    }
}
