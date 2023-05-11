package org.itstep.user;

import org.itstep.role.Role;
import org.itstep.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping({"/index","/"})
    public String index() {
        return "index";
    }

    // Login form
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    // Login form with error
    @GetMapping("/signin-error")
    public String signinError(Model model) {
        model.addAttribute("loginError", true);
        return "signin";
    }

    @PostMapping("/signin")
    public String signinPost() {
        return "index";
    }

    //Register
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user",new User()); //Если не добавить, то не будет выполняться парсинг шаблона
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(User user, Model model,
                             @RequestParam(value = "password2" , required = true) String password2,
                             HttpServletResponse response) {
        //Передать id в заголовке ответа

//System.out.println(user.getPassword());

//System.out.println(password2);
        if (userService.getUserByUsername(user.getUsername())!=null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with this username or email already exists");

        if (!user.getPassword().equals(password2))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Passwords do not match");
        Set<Role> rolesNew = new HashSet<>();
        rolesNew.add(roleService.findById(2L));
        user.setRoles(rolesNew);
        User newUser =
                userService.save
                        (user);
        long id = newUser.getId();
        response.addHeader("id", String.valueOf(id));
        return "index";
    }

    @GetMapping(value ="/users" )
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value ="/edit_user")
    public String editSmart(Model model, @RequestParam(name="id")Long id) {
        User user = userService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",roleService.findAll());
        return "edit_user";
    }

    @PutMapping(value="/update_user")
    public String updateUser(User user, Model model,
                             @RequestParam(value = "role" , required = false) long[] roles) {
        Set<Role> rolesNew = new HashSet<>();
        if(roles != null) {
            for (int i = 0; i < roles.length; i++) {

//System.out.println(roles[i]);rolesNew.add(roleService.findById(roles[i]));
            }
        }
        User userDb = userService.findById(user.getId());
        userDb.setEnabled(user.isEnabled());
        userDb.setRoles(rolesNew);

        userService.save
                (userDb);
        model.addAttribute("user", userService.findAll());
        return "redirect:/users";
    }

    @GetMapping(value ="/delete_user")
    public String deleteUser(@RequestParam(name="id") Long id) {
        System.out.println(id);
        userService.deleteById(id);
        return "redirect:/users";
    }
}