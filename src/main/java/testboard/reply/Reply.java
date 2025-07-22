package testboard.reply;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import testboard.member.Member;
import testboard.post.Post;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreationTimestamp
    private Timestamp createdAt;

    ///

    @Builder
    public Reply(String comment, Member member, Post post) {
        this.comment = comment;
        this.member = member;
        this.post = post;
    }

    /**
     * 댓글 권한 확인
     */
    public boolean isOwner(Long memberId) {
        if (memberId == null) {
            return false;
        }
        return this.member.getId().equals(memberId);
    }

    /**
     * 댓글 수정 메서드
     */
    public void update(String comment) {
        this.comment = comment;
    }
}
