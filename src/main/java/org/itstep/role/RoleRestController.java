package org.itstep.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/api/roles")
    public List<Role> roles(){
        return roleService.findAll();
    }




}
