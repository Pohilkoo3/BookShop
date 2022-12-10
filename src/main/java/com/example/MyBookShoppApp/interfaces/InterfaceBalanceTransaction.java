package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.payments.BalanceTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceBalanceTransaction extends JpaRepository<BalanceTransactionEntity, Integer>
{



    List<BalanceTransactionEntity> findAllByUserId(int userId);
}
