package testboard.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class MemberRequest {

    @Getter
    @Setter
    public static class JoinDTO {

        @NotEmpty(message = "이름을 입력해주세요.")
        @Size(min = 4, max = 20, message = "이름은 4~20자로 입력해주세요.")
        private String name;

        @NotEmpty(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자로 입력해주세요.")
        private String password;

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .role(Role.USER)
                    .build();
        }//toEntity

    }//JoinDTO

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자로 입력해주세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        @NotEmpty(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자로 입력해주세요.")
        private String password;
    }



}//MemberRequest
