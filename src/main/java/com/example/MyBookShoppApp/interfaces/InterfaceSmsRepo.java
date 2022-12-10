package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.other.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceSmsRepo extends JpaRepository<SmsCode, Long> {

    public SmsCode findByCode(String code);

}
