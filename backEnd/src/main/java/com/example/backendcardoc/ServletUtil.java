package com.example.backendcardoc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServletUtil {

    @GetMapping(value = "/login")
    public String templateHandlerLogin(Model model) {return "login";
    }

    @GetMapping(value = "/registration")
    public String templateHandlerRegistrazione(Model model) {
        return "registration";
    }

    @GetMapping(value = "/*/.html")
    public String templateHandler(HttpServletRequest request, Model model) {
        String resource = request.getRequestURI().substring(request.getContextPath().length());
        resource = resource.substring(0, resource.indexOf(".html"));
        model.addAttribute("resource", resource);
        return resource;
    }
}