package testboard.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import testboard.member.Member;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 회원으로 게시글 찾기
     */
    List<Post> findByMember(Member member);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.member LEFT JOIN FETCH p.category ORDER BY p.id DESC")
    Page<Post> findAllWithDetails(Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.member m LEFT JOIN FETCH p.category c WHERE p.id = :id")
    Optional<Post> findByIdWithDetails(@Param("id") Long id);


}
