package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.UserInfoDTO;
import KonKuk.OTeam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/email-check")
    public ResponseEntity<String> emailCheck(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String checkResult = userService.emailCheck(userEmail);
        return ResponseEntity.ok(checkResult);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String userPassword = request.get("userPassword");

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(userEmail);
        userInfoDTO.setPassword(userPassword);
        userInfoDTO.setLevel(1L);
        userInfoDTO.setWordCount(0L);

        String saveResult = userService.save(userInfoDTO);
        return ResponseEntity.ok(saveResult);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpSession session) {
        String userEmail = request.get("userEmail");
        String userPassword = request.get("userPassword");

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
    public ResponseEntity<String> initialSetting(@RequestBody Map<String, Object> request, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");

        if (loginEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail");
        }

        String userName = (String) request.get("userName");
        List<String> categories = (List<String>) request.get("categories");

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(loginEmail);
        userInfoDTO.setName(userName);
        userInfoDTO.setCategories(categories);

        String result = userService.initialSetting(userInfoDTO);
        return ResponseEntity.ok(result);
    }
}
