package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.keys.BookUserId;
import com.example.MyBookShoppApp.model.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceBookReview extends JpaRepository<BookReviewEntity, BookUserId> {
}
