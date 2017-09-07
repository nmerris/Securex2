package byAJ.Securex.controllers;

import byAJ.Securex.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("newUser", new User());
        return "register";
    }

    // the selected role comes in as a request param from the form, can't be null because User is preselected
    @PostMapping("/register")
    public String processRegistration(@RequestParam(value = "selectedRole") String role,
                                      @Valid @ModelAttribute("newUser") User user,
                                      BindingResult bindingResult,
                                      Model model) {

        System.out.println("####################### /register POST... incoming role String is: " + role);

        // always add the incoming user back to the model
        model.addAttribute("newUser", user);

        if(bindingResult.hasErrors()) {
            return "register";
        }
        else {
            if(role.equals("ROLE_USER")) {
                userService.saveUser(user);
                model.addAttribute("message", "ROLE_USER account successfully created!");
                // go to book list
                return "redirect:/books/list";
            }
            else { // must be ROLE_LIBRARIAN
                userService.saveAdmin(user);
                model.addAttribute("message", "ROLE_LIBRARIAN account successfully created!");
                // go to add book
                return "redirect:/books/add";
            }
        }

        return "index";

    }

}
