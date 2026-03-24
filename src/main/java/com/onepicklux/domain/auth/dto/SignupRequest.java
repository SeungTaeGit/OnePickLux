package com.onepicklux.domain.auth.dto;

import com.onepicklux.domain.member.entity.AuthProvider;
import com.onepicklux.domain.member.entity.Gender;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private String phone;

    private Gender gender;
    private LocalDate birthDate;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .gender(gender)
                .birthDate(birthDate)
                .role(Role.USER)
                .provider(AuthProvider.LOCAL)
                .build();
    }
}