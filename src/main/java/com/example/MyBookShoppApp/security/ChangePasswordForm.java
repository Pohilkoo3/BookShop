package com.example.MyBookShoppApp.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm
{

    private String name;
    private String mail;
    private String phone;
    private String password;
    private String passwordReply;
}
