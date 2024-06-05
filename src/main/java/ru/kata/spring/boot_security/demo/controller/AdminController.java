package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String addUser (@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult,
                           @RequestParam(value = "rolesList") String [] roles,
                           @ModelAttribute("pass") String pass) {
        if (bindingResult.hasErrors()){
            return "/new";
        }
        userService.addUser(user, roles, pass);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String edit (Model model, @PathVariable("id") int id) {

        model.addAttribute("user", userService.getUser(id));
        return "update";
    }

    @PostMapping("/{id}")
    public String update (@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          @PathVariable("id") int id,
                          @RequestParam(value = "rolesList") String [] roles,
                          @ModelAttribute("pass") String pass) {
        System.out.println("UPDATE USER!!! = " + user);
        if (bindingResult.hasErrors()){
            return "/update";
        }
        System.out.println("UPDATE USER!!! = " + user);
        userService.updateUser(user, roles, pass);
        return "redirect:/admin/"+user.getId()+"/update";
    }

    @PostMapping("/{id}/delete")
    public String delete (@PathVariable("id") int id) {
        System.out.println("DELETE USER!!! = " + id);
        userService.deleteUser(id);

        return "redirect:/admin";
    }
}