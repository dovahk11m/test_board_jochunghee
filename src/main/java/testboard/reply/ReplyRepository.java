package testboard.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import testboard.post.Post;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 게시글로 댓글 찾기
     */
    List<Reply> findByPost(Post post);
}
