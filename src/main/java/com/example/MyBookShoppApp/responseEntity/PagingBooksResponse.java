package com.example.MyBookShoppApp.responseEntity;

import com.example.MyBookShoppApp.model.book.links.Book2AuthorEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PagingBooksResponse {
    private Integer count;

    private List<Book> books;

    public PagingBooksResponse(List<Book> books) {
        this.count = books.size();
        books.stream().forEach(b -> b.setAuthors(
                b.getBook2AuthorEntitySet().size() != 0 ?
                        b.getBook2AuthorEntitySet().stream().map(a -> a.getAuthor().getName()).collect(Collectors.joining()) :
                        "author not defined"));
        this.books = books;
    }

}
