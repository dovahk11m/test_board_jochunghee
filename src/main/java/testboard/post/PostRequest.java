package testboard.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import testboard.category.Category;
import testboard.member.Member;


public class PostRequest {

    @Getter
    @Setter
    public static class SaveDTO {

        @NotEmpty(message = "제목을 입력해주세요.")
        @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요.")
        @Size(max = 1000, message = "내용은 1000자 이하로 입력해주세요.")
        private String content;

        @NotNull(message = "카테고리를 선택해주세요.")
        private Long categoryId;

        public Post toEntity(Member member, Category category) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .category(category)
                    .build();
        }

    }//SaveDTO

    @Getter
    @Setter
    public static class UpdateDTO {
        @NotEmpty(message = "제목을 입력해주세요.")
        @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요.")
        @Size(max = 1000, message = "내용은 1000자 이하로 입력해주세요.")
        private String content;

        @NotNull(message = "카테고리를 선택해주세요.")
        private Long categoryId;

    }//UpdateDTO

}//PostRequest
