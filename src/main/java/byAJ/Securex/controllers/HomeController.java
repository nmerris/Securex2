package byAJ.Securex.controllers;

import byAJ.Securex.UserService;
import byAJ.Securex.models.User;
import byAJ.Securex.repositories.RoleRepository;
import byAJ.Securex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;



    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("message", "Please login or create a new account");
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(Principal loggedInPerson) {

//        userService.

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ in loginPost, Principal.?: " + loggedInPerson.getName());

        // TODO send user to appropriate placed based on their ROLE

        return "index";// change this per above comment
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
                // NOTE: saving a new user does NOT log you in, you still need to login after creating a new account
                userService.saveUser(user);
                model.addAttribute("message", "ROLE_USER account successfully created!");
                // go to book list
//                return "redirect:/books/list";
            }
            else { // must be ROLE_LIBRARIAN
                userService.saveLibrarian(user);
                model.addAttribute("message", "ROLE_LIBRARIAN account successfully created!");
                // go to add book
//                return "redirect:/books/add";
            }
        }

        model.addAttribute("message", "New account was successfully created, please login now");
        return "login";

    }



}
