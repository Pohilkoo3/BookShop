package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceBalanceTransaction;
import com.example.MyBookShoppApp.model.payments.BalanceTransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceTransactionDao {

    private final InterfaceBalanceTransaction balanceTransaction;


    public void save(BalanceTransactionEntity transaction) {
        balanceTransaction.save(transaction);
    }

    public List<BalanceTransactionEntity> getAllTransactionsByUserId(int userId){
        return balanceTransaction.findAllByUserId(userId);
    }
}
