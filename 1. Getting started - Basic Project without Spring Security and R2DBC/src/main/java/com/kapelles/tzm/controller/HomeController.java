package com.kapelles.tzm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("message", "Hello from TZm");
        return "index";
    }
}