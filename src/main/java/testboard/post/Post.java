package testboard.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import testboard.category.Category;
import testboard.member.Member;
import testboard.reply.Reply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_tb")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Formula("(SELECT COUNT(1) FROM reply_tb r WHERE r.post_id = id)")
    private int replyCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @CreationTimestamp
    private Timestamp createdAt;

    ///

    @Builder
    public Post(String title, String content, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
        this.postType = "공지사항".equals(category.getName()) ? PostType.NOTICE : PostType.GENERAL;
    }

    /**
     * 게시글 권한 확인 메서드
     */
    public boolean isOwner(Long memberId) {

        if (memberId == null) {
            return false;
        }
        return this.member.getId().equals(memberId);
    }

    /**
     * 게시글 수정 메서드
     */
    public void update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

}