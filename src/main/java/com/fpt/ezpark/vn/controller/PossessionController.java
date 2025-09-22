package com.fpt.ezpark.vn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fpt.ezpark.vn.configuration.security.PermissionService;
import com.fpt.ezpark.vn.model.entity.Possession;
import com.fpt.ezpark.vn.model.entity.User;
import com.fpt.ezpark.vn.service.PossessionService;
import com.fpt.ezpark.vn.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/possessions")
@RequiredArgsConstructor
public class PossessionController {

    private final PossessionService possessionService;

    private final UserService userService;

    private final PermissionService permissionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PostAuthorize("hasPermission(returnObject, 'READ') or hasPermission(returnObject, 'ADMINISTRATION')")
    public Possession findOne(@PathVariable("id") final Long id) {
        return possessionService.findOne(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Possession> findAll() {
        return possessionService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Possession possession, Authentication authentication) {
        possession.setOwner(userService.findUserByEmail(authentication.getName()));
        possession = possessionService.save(possession);
        System.out.println(possession);
        // permissionService.addPermissionForAuthority(possession,
        // BasePermission.ADMINISTRATION, "ADMIN");
        permissionService.addPermissionForUser(possession, BasePermission.ADMINISTRATION, authentication.getName());
        return new ModelAndView("redirect:/user?message=Possession created with id " + possession.getId());
    }

    @PreAuthorize("hasPermission(#possession, 'WRITE')")
    @PostMapping("/acl/grant_permssion/user")
    public ResponseEntity<String> grantPermissionForUser(
            @RequestParam String possessionName,
            @RequestParam String userEmail,
            @RequestParam String perm) {
        Possession possession = possessionService.findByName(possessionName);
        User user = userService.findUserByEmail(userEmail);
        switch (perm) {
            case "WRITE":
                permissionService.addPermissionForUser(possession, BasePermission.WRITE, user.getEmail());
                break;
            case "READ":
                permissionService.addPermissionForUser(possession, BasePermission.READ, user.getEmail());
                break;
            default:
                return new ResponseEntity<>("Invalid permission type", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Permission granted", HttpStatus.OK);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute final Possession possession) {
        return "tl/possession";
    }
}
