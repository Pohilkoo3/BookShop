package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;


@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ControllerAdviceCommonsModel controllerAdviceCommonsModel;



    @PostMapping(value = "/payment")
    public RedirectView handlePayment(@RequestBody String sum, HttpServletRequest request) throws NoSuchAlgorithmException {
        String paymentUrl = paymentService.getPaymentToBalance(sum);
        paymentService.makeDeposit(sum, request);
        return new RedirectView(paymentUrl);
    }

    @GetMapping("/books/pay")
    public String handlePay(@CookieValue(value = "cartContents", required = false) String cartContents,
                            @CookieValue(value = "token", required = false) String token,
                           HttpServletResponse response,
                            Model model) {
       if (!paymentService.payForAllBooksFromCart(cartContents, token)){
           model.addAttribute("isMoneyEnough", false);
           controllerAdviceCommonsModel.setIsMoneyEnough(false);
       } else {
           Cookie cookie = new Cookie("cartContents", "");
           cookie.setPath("/");
           response.addCookie(cookie);
           model.addAttribute("isMoneyEnough", true);
           controllerAdviceCommonsModel.setIsMoneyEnough(true);
        }
        return "redirect:/books/cart";
    }


}
