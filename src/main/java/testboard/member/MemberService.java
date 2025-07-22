package testboard.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import testboard._core.errors.exception.Exception401;
import testboard._core.errors.exception.Exception403;
import testboard._core.errors.exception.Exception404;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(MemberRequest.JoinDTO requestDTO) {
        // 1. 이메일 중복 확인
        memberRepository.findByEmail(requestDTO.getEmail()).ifPresent(member -> {
            throw new Exception404("이미 사용중인 이메일입니다.");
        });

        // 2. 회원가입 진행 (실제로는 비밀번호 암호화 필수)
        Member member = requestDTO.toEntity();
        return memberRepository.save(member);
    }


    @Transactional
    public Member login(MemberRequest.LoginDTO requestDTO) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new Exception401("이메일 또는 비밀번호가 일치하지 않습니다."));

        // 2. 비밀번호 일치 여부 확인 (실제로는 암호화된 비밀번호를 비교해야 합니다)
        if (!member.getPassword().equals(requestDTO.getPassword())) {
            throw new Exception401("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 성공 시 회원 정보 반환
        return member;
    }
}