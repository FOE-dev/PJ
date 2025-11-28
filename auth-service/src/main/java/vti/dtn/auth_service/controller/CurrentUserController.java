package vti.dtn.auth_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.service.UserService;

@RestController
@RequestMapping("/user")
public class CurrentUserController {

    private final UserService userService;

    public CurrentUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserEntity getMe(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        if (username == null) {
            return null;
        }

        return userService.findByUsername(username);
    }
}
