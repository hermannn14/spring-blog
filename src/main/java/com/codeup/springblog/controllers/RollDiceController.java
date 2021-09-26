package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    @ResponseBody
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    @ResponseBody
    public String rollDiceGuess(@PathVariable int n, Model model) {
        Random rand = new Random();
        int random = rand.nextInt((6-1) + 1) +1;

        model.addAttribute("diceRoll", random);
        model.addAttribute("userGuess", n);
        model.addAttribute("isCorrect", random);

        return "roll-dice";

    }
}
