package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InterfaceBookRepo extends JpaRepository<Book, Integer>
{

    /** Не ищет часть имени, а ишет только имя целиком. **/
    @Query(value = "SELECT * FROM book JOIN book2author ON book.id=book2author.book_id JOIN author " +
            "ON author.id=book2author.author_id where author.name= ?1", nativeQuery = true)
    List<Book> findBookByAuthorName(String name);


    List<Book> findBooksByTitleContaining(String title);

    List<Book> findBooksByPriceBetween(int min, int max);

    List<Book> findBooksByPriceIs(int price);

    @Query("from Book where isBestseller=1")
    List<Book> findBestsellers();

    @Query(value = "SELECT * FROM book WHERE discount=(SELECT MAX(discount) FROM book)", nativeQuery = true)
    List<Book> getBooksMaxDiscount();

    Page<Book> findBooksByTitleContaining(String title, Pageable nextPage);

    Page<Book> findBooksByPubDateBetween(Date dateFrom, Date to, Pageable nextPage);

    @Query(value = "SELECT b.id, b.title, b.discount, b.price, b.description, b.image, b.pub_date, b.is_bestseller, b.slug " +
            "FROM book b JOIN book2genre ON b.id=book2genre.book_id  JOIN genre ON genre.id=book2genre.genre_id where genre.id= ?1", nativeQuery = true)
    Page<Book> findBookByGenreBuId(Integer id, Pageable nextPage);

    @Query("SELECT b FROM Book b JOIN b.tagSet t WHERE t.id = ?1")
    Page<Book> findBooksByTags(Integer id, Pageable page);

    @Query(value = "SELECT * from Book b where b.is_bestseller=1", nativeQuery = true )
    Page<Book> findBooksPageIsBestseller(Pageable page);

    @Query("SELECT b FROM Book b JOIN b.book2AuthorEntitySet t WHERE t.pk.author.id = ?1")
    Page<Book> findBooksByAuthorId(Integer id, Pageable page);

    Book findBookBySlugContaining(String slugBook);

    List<Book> findBooksBySlugIn(String[] cookieSlugs);
}
