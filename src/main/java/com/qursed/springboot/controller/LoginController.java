package com.qursed.springboot.controller;


import com.qursed.springboot.model.User;
import com.qursed.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {

        return "login";
    }

    @GetMapping(value = "user")
    public String printUserInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findUserByLogin(auth.getName()));
        return "user";
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid User user,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "admin/new";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "admin/edit";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }


}
