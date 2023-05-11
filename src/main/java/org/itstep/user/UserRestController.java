package org.itstep.user;

import org.itstep.role.Role;
import org.itstep.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public List<User> users(){
        return userService.findAll();
    }
}
