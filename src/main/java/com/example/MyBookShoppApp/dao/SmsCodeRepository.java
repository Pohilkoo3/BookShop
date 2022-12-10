package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceSmsRepo;
import com.example.MyBookShoppApp.model.other.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsCodeRepository {
    
    private final InterfaceSmsRepo interfaceSmsRepo;
    @Autowired
    public SmsCodeRepository(InterfaceSmsRepo interfaceSmsRepo) {
        this.interfaceSmsRepo = interfaceSmsRepo;
    }


    public SmsCode findByCode(String code) {
        return interfaceSmsRepo.findByCode(code);
    }

    public void save(SmsCode smsCode) {
        interfaceSmsRepo.save(smsCode);
    }
}
