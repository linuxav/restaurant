package com.alex.restaurant;

import org.springframework.core.io.ClassPathResource;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContactController {

    @GetMapping({"/contact", "/contactform"})
    public String showContactForm() {
    return "redirect:/contact.html";
    }

    private final JavaMailSender mailSender;

    public ContactController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @PostMapping("/contactform")   
    public String sendContactForm(HttpServletRequest request, Model model) throws Exception {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true);
        mail.setFrom(email);
        mail.setTo("webdevilstu@gmail.com");
        mail.setSubject("you received a message");
        mail.setText(message);

        ClassPathResource imageFile = new ClassPathResource("static/img/logo.png");       
        mail.addAttachment("logo.png", imageFile);

        mailSender.send(mimeMessage);

        model.addAttribute("name", name);  
        
        return "confirmation";
    }
}
