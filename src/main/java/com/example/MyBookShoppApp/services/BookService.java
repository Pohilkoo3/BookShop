package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.BooksDao;
import com.example.MyBookShoppApp.data.google.api.books.Item;
import com.example.MyBookShoppApp.data.google.api.books.Root;
import com.example.MyBookShoppApp.errs.BookStoreApiWrongParameterException;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    private BooksDao booksDao;
    private RestTemplate restTemplate;

    @Autowired
    public BookService(BooksDao booksDao, RestTemplate restTemplate) {
        this.booksDao = booksDao;
        this.restTemplate = restTemplate;
    }

    public List<Book> getBooksData() {
        return booksDao.getAllBooks();
    }

    /**
     * Book's DAO for APIBook'sController
     **/

    public List<Book> getBooksDataByAuthor(String authorsName) {
        return booksDao.getBooksDataByAuthor(authorsName);
    }

    public List<Book> getBooksDataByTitle(String title) throws BookStoreApiWrongParameterException {
        return booksDao.getBooksDataByTitle(title);
    }

    public List<Book> getBooksDataByPriceBetween(int min, int max) {
        return booksDao.getBooksDataByPriceBetween(min, max);
    }

    public List<Book> getBooksDataByPriceIs(int price) {
        return booksDao.getBooksDataByPriceIs(price);
    }

    public List<Book> getBestsellers() {
        return booksDao.getBestsellers();
    }

    public List<Book> getBooksMaxDiscount() {
        return booksDao.getBooksMaxDiscount();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        return booksDao.getBooksPaging(offset, limit);
    }

    public Page<Book> getPageOfSearchResult(String search, Integer offset, Integer limit) {
        return booksDao.getPageOfSearchResult(search, offset, limit);
    }

    public Page<Book> getNewBooks(Date dateFrom, Date dateTo, Integer offset, Integer limit) {
        return booksDao.getNewBooks(dateFrom, dateTo, offset, limit);
    }

    public Page<Book> getPopularBooks(Integer offset, Integer limit) {
        return booksDao.getPopularBooks(offset, limit);
    }

    public Page<Book> getBooksByGenreId(Integer id, Integer offset, Integer limit) {
        return booksDao.getBooksByGenreId(id, offset, limit);
    }

    public Page<Book> getBooksByTagsId(Integer tagsId, Integer offset, Integer limit) {
        return booksDao.getBooksByTagsId(tagsId, offset, limit);
    }

    public Page getPagePopularBook(int offset, int limit) {
        return booksDao.getPagePopularBooks(offset, limit);
    }

    public Page<Book> getBooksDataByAuthorId(Integer idAuthor, int offset, int limit) {
        return booksDao.getBooksByAuthorId(idAuthor, offset, limit);
    }

    public Book getBookBySlug(String slugBook) {
        return booksDao.getBooksBySlug(slugBook);
    }

    public void save(Book book) {
        booksDao.saveBook(book);
    }

    public List<Book> getBooksByListSlug(String[] cookieSlugs) {
        return booksDao.getBooksByListSlug(cookieSlugs);
    }

    @Value("${google.books.api.key}")
    private String apiKey;

    public List<Book> getPageOfGoogleBooksApiSearchResult(String searchWord, Integer offset, Integer limit) {
        String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?" +
                "q=" + searchWord +
                "&key=" + apiKey +
                "&filter=paid-ebooks" +
                "&startIndex=" + offset +
                "&maxResults=" + limit;

        Root root = restTemplate.getForEntity(REQUEST_URL, Root.class).getBody();
        ArrayList<Book> list = new ArrayList<>();
        if (root != null) {
            for (Item item : root.getItems()) {
                Book book = new Book();
                if (item.getVolumeInfo() != null) {
//                    book.setAuthors(item.getVolumeInfo().getAuthors());
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setImage(item.getVolumeInfo().getImageLinks().getThumbnail());
                }

                if (item.getSaleInfo() != null) {
                    book.setPrice((int) item.getSaleInfo().getRetailPrice().getAmount());
                }
                list.add(book);
            }
        }
        return list;
    }

}
