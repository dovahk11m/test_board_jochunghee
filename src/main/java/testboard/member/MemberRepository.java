package testboard.member;

import org.springframework.data.jpa.repository.JpaRepository;
import testboard.category.Category;

import java.util.Optional; // [추가]

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원찾기
    Optional<Member> findByEmail(String email);
}