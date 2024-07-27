package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.UserInfoDTO;
import KonKuk.OTeam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/email-check")
    public ResponseEntity<String> emailCheck(@RequestParam("userEmail") String userEmail) {
        String checkResult = userService.emailCheck(userEmail);
        return ResponseEntity.ok(checkResult);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(userEmail);
        userInfoDTO.setPassword(userPassword);
        String saveResult = userService.save(userInfoDTO);
        return ResponseEntity.ok(saveResult);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword, HttpSession session) {
        String loginResultEmail = userService.login(userEmail, userPassword);
        if (loginResultEmail != null) {
            session.setAttribute("loginEmail", loginResultEmail);
            return ResponseEntity.ok(loginResultEmail);
        } else {
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            session.invalidate();
            return ResponseEntity.ok("invalidate");
        } catch (IllegalStateException e) {
            return ResponseEntity.ok("fail");
        }
    }

    @PostMapping("/initial-setting")
    public ResponseEntity<String> initialSetting(@RequestParam("userName") String userName, @RequestParam("categories") List<String> categories, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");

        if (loginEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail");
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(loginEmail);
        userInfoDTO.setName(userName);
        userInfoDTO.setCategories(categories);

        String result = userService.initialSetting(userInfoDTO);

        return ResponseEntity.ok(result);
    }
}
