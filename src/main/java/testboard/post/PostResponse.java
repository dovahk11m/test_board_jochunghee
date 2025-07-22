package testboard.post;

import lombok.Getter;
import testboard.category.Category;
import testboard.reply.Reply;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {

    @Getter
    public static class ListDTO {
        private final Long id;
        private final String title;
        private final String authorName;
        private final String categoryName;
        private final int replyCount;
        private final boolean isNotice;
        private final String createdAt;



        public ListDTO(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.authorName = post.getMember().getName();
            this.categoryName = post.getCategory().getName();
            this.replyCount = post.getReplies().size();
            this.isNotice = post.getPostType() == PostType.NOTICE;
            this.createdAt = post.getCreatedAt().toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
        }

    }//ListDTO

    @Getter
    public static class DetailDTO {
        private final Long id;
        private final String title;
        private final String content;
        private final String authorName;
        private final String categoryName;
        private final String createdAt;
        private final boolean isOwner; // 수정, 삭제 버튼 노출 여부
        private final List<ReplyDTO> replies;

        public DetailDTO(Post post, Long sessionUserId) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.authorName = post.getMember().getName();
            this.categoryName = post.getCategory().getName();
            this.createdAt = post.getCreatedAt().toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.isOwner = post.isOwner(sessionUserId);

            this.replies = post.getReplies().stream()
                    .map(reply -> new ReplyDTO(reply, sessionUserId))
                    .collect(Collectors.toList());
        }

        @Getter
        public static class ReplyDTO {
            private final Long id;
            private final String comment;
            private final String authorName;
            private final boolean isOwner;

            public ReplyDTO(Reply reply, Long sessionUserId) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.authorName = reply.getMember().getName();
                this.isOwner = reply.isOwner(sessionUserId);
            }
        }
    }//DetailDTO

    @Getter
    public static class UpdateFormDTO {
        private final Long id;
        private final String title;
        private final String content;
        private final Long categoryId;
        private final List<CategoryDTO> categories;

        public UpdateFormDTO(Post post, List<Category> categories) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.categoryId = post.getCategory().getId();
            this.categories = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
        }

        @Getter
        public static class CategoryDTO {
            private final Long id;
            private final String name;

            public CategoryDTO(Category category) {
                this.id = category.getId();
                this.name = category.getName();
            }
        }
    }

}
