package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.UserInfoDTO;
import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        if (saveResult.equals("success")) {
            return ResponseEntity.ok(userEmail);
        } else {
            return ResponseEntity.ok("fail");
        }
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
    public ResponseEntity<String> initialSetting(@RequestBody Map<String, Object> request) {
        String userEmail = (String) request.get("userEmail");
        String userName = (String) request.get("userName");
        List<String> categories = (List<String>) request.get("categories");

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(userEmail);
        userInfoDTO.setName(userName);
        userInfoDTO.setCategories(categories);

        String result = userService.initialSetting(userInfoDTO);

        if ("fail".equals(result)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/mypage")
    public ResponseEntity<Map<String, String>> getUserInfo(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");

        // UserEmail을 통해 UserInfo를 찾기
        Optional<UserInfoEntity> userOpt = userService.findByEmail(userEmail);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserInfoEntity userInfo = userOpt.get();

        // UserInfo에서 필요한 데이터 추출
        String userName = userInfo.getName();
        String level = userInfo.getLevelCategory().getLevel();

        // 결과를 맵에 담아서 반환
        Map<String, String> response = new HashMap<>();
        response.put("userName", userName);
        response.put("level", level);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/levelpage")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");

        if (loginEmail == null) {
            // 세션에 로그인 정보가 없으면 UNAUTHORIZED 상태를 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<UserInfoEntity> userOpt = userService.findByEmail(loginEmail);
        if (!userOpt.isPresent()) {
            // 만약 사용자가 없다면 NOT_FOUND 상태를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserInfoEntity userInfo = userOpt.get();

        // level과 wordCount를 맵에 담아서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("level", userInfo.getLevelCategory().getLevel());
        response.put("wordCount", userInfo.getWordCount());

        return ResponseEntity.ok(response);
    }

}
