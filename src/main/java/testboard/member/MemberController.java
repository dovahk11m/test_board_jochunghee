package testboard.member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final HttpSession session;

    /**
     * 로그인 페이지를 보여주는 메서드
     */
    @GetMapping("/login-form")
    public String loginForm() {
        return "member/login-form"; // templates/member/login-form.mustache
    }

    /**
     * 로그인 요청을 처리하는 메서드
     */
    @PostMapping("/login")
    public String login(MemberRequest.LoginDTO requestDTO) {
        // 1. 서비스 호출하여 로그인 시도
        Member sessionUser = memberService.login(requestDTO);

        // 2. 성공 시, 세션에 사용자 정보 저장
        session.setAttribute("sessionUser", sessionUser);

        // 3. 메인 페이지로 리다이렉트
        return "redirect:/";
    }

    /**
     * 로그아웃 요청을 처리하는 메서드
     */
    @GetMapping("/logout")
    public String logout() {
        // 세션 무효화
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 회원가입 페이지 요청
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "member/join-form";
    }

    /**
     * 회원가입 요청 처리
     */
    @PostMapping("/join")
    public String join(@Valid MemberRequest.JoinDTO requestDTO, Errors errors) {
        memberService.join(requestDTO);
        return "redirect:/login-form"; // 회원가입 성공 시 로그인 페이지로 이동
    }

}