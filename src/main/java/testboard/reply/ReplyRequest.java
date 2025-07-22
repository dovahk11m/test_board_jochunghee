package testboard.reply;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import testboard.member.Member;
import testboard.post.Post;

public class ReplyRequest {

    @Getter
    @Setter
    public static class SaveDTO {

        @NotEmpty(message = "댓글을 입력해주세요.")
        @Size(max = 200, message = "댓글은 200자 이하로 입력해주세요.")
        private String comment;

        public Reply toEntity(Member member, Post post) {
            return Reply.builder()
                    .comment(comment)
                    .member(member)
                    .post(post)
                    .build();
        }

    }//SaveDTO

    @Getter
    @Setter
    public static class UpdateDTO {

        @NotEmpty(message = "댓글을 입력해주세요.")
        @Size(max = 200, message = "댓글은 200자 이하로 입력해주세요.")
        private String comment;

    }//UpdateDTO

}//ReplyRequest
