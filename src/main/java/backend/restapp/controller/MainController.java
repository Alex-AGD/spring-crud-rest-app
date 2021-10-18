package backend.restapp.controller;

import backend.restapp.model.Person;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;


@Controller
public class MainController {
    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal Person person) {
        HashMap<Object, Object> dataPerson = new HashMap<>();
        dataPerson.put("profile", person);
        dataPerson.put("messages", null);
        model.addAttribute("frontData", dataPerson);
        return "main";
    }
}

