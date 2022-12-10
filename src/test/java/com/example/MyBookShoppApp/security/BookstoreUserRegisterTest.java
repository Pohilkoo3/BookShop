package com.example.MyBookShoppApp.security;

import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.services.UserContactService;
import com.example.MyBookShoppApp.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookstoreUserRegisterTest {
    private RegistrationForm registrationForm;
    private BookstoreUserRegister bookstoreUserRegister;

    private final PasswordEncoder passwordEncoder;
    private ContactConfirmationPayload payload;

    @MockBean
    private UserService userServiceMock;



    @Autowired
    public BookstoreUserRegisterTest(BookstoreUserRegister bookstoreUserRegister,
                                     PasswordEncoder passwordEncoder) {
        this.bookstoreUserRegister = bookstoreUserRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
       payload = new ContactConfirmationPayload();
        payload.setCode(passwordEncoder.encode("123456"));
        payload.setContact("yana@mail.ru");
        registrationForm = new RegistrationForm();
        registrationForm.setName("TesterNew");
        registrationForm.setEmail("testNew@mail.org");
        registrationForm.setPass("testPassword");
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerNewUser() {
        UserEntity user = bookstoreUserRegister.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPass(), user.getPass()));
        assertTrue(CoreMatchers.is(registrationForm.getName()).matches(user.getName()));
        Mockito.verify(userServiceMock, Mockito.times(1)).addNewUser(Mockito.any(UserEntity.class));
    }

    @Test
    void registerNewUserFail(){
        Mockito.doReturn(new UserEntity())
                .when(userServiceMock)
                .findUserByContact(registrationForm.getEmail());
        UserEntity user = bookstoreUserRegister.registerNewUser(registrationForm);

        assertNull(user.getId());
    }

}