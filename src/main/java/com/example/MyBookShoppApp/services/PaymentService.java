package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.BalanceTransactionDao;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.payments.BalanceTransactionEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${robokassa.pass.first.test}")
    private String pass;

    @Value("${robokassa.merchant.login}")
    private String login;

    private final BalanceTransactionDao transactionDao;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final BookService bookService;

    public String getPaymentUrl(List<Book> booksFromSlug) throws NoSuchAlgorithmException {
        int sumAllBooks = booksFromSlug.stream().map(b -> b.getPriceWithDiscount()).reduce((s1, s2) -> s1 + s2).orElse(0);
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId = "5"; //Just For Test. Need add method for generate count of order
        md.update((login + ":" + String.valueOf(sumAllBooks) + ":" + invId + ":" + pass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + login +
                "&InvId=" + invId +
                "&Culture=ru" +
                "&Encoding=utf-8" +
                "&OutSum=" + String.valueOf(sumAllBooks) +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&isTest=1";
    }

    public String getPaymentToBalance(String sum) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String[] itemsSum = sum.split("=");
        int rightSum = Integer.valueOf(itemsSum[1]);
        String invId = "5"; //TODO Just For Test. Need add method for generate count of order
        md.update((login + ":" + rightSum + ":" + invId + ":" + pass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + login +
                "&InvId=" + invId +
                "&Culture=ru" +
                "&Encoding=utf-8" +
                "&OutSum=" + rightSum +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&isTest=1";
    }

    public List<BalanceTransactionEntity> getAllTransactionsByUserId(int userId) {
        return transactionDao.getAllTransactionsByUserId(userId);
    }


    public void makeDeposit(String sum, HttpServletRequest request) {
        String email = jwtUtil.getEmailFromToken(request);
        String[] itemsSum = sum.split("=");
        int rightSum = Integer.parseInt(itemsSum[1]);

        UserEntity user = userService.findUserByContact(email);
        user.setBalance(user.getBalance() + rightSum);
        userService.addNewUser(user);

        BalanceTransactionEntity balanceTransaction = new BalanceTransactionEntity();
        balanceTransaction.setUser(user);
        balanceTransaction.setValue(rightSum);
        balanceTransaction.setTime(LocalDateTime.now());
        balanceTransaction.setDescription("Adding funds to account");
        transactionDao.save(balanceTransaction);
    }

    public boolean payForAllBooksFromCart(String cartContents, String token) {

        cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
        cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
        String[] cookieSlugs = cartContents.split("/");
        List<Book> booksFromSlug = bookService.getBooksByListSlug(cookieSlugs);
        int sumAllBooks = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("By books from cart: \n");
        for (Book book : booksFromSlug) {
            sb.append(book.getTitle() + " by " + book.getAuthors() + ". Cost: " + book.getPriceWithDiscount() + "\n");
            sumAllBooks += book.getPriceWithDiscount();
        }
        UserEntity user = userService.findUserByContact(jwtUtil.extractUsername(token));
        if (user.getBalance() < sumAllBooks) {
            return false;
        }
        user.setBalance(user.getBalance() - sumAllBooks);

        BalanceTransactionEntity balanceTransactionEntity = new BalanceTransactionEntity();
        balanceTransactionEntity.setTime(LocalDateTime.now());
        balanceTransactionEntity.setDescription(sb.toString());
        balanceTransactionEntity.setValue(sumAllBooks);
        balanceTransactionEntity.setUser(user);

        transactionDao.save(balanceTransactionEntity);
        return true;


    }
}
