package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Password {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    private static final Pattern PASSWORD_MATCHER = Pattern.compile(PASSWORD_PATTERN);

    @Column(name = "pw", nullable = false)
    private String value;

    public Password(String pw) {
        validatePasswordPattern(pw);
        this.value = pw;
    }

    private void validatePasswordPattern(String pw) {
        if (isNotValidPattern(pw)) {
            throw new WishHairException(ErrorCode.USER_INVALID_PASSWORD);
        }
    }

    private static boolean isNotValidPattern(String email) {
        return !PASSWORD_MATCHER.matcher(email).matches();
    }
}