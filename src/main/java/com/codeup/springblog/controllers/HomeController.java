package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

    @Controller
    public class HomeController {

        @GetMapping("/")
        @ResponseBody
        public String landing() {
            return "This is the landing page";
        }

    @GetMapping("/weather")
    @ResponseBody
public String viewWeather(Model model) {
            model.addAttribute("temp", 98F);
            return "WeatherPage";

    }
    }

