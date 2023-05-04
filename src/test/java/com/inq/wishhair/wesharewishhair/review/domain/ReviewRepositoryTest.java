package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewRepositoryTest - DataJpaTest")
public class ReviewRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HairStyleRepository hairStyleRepository;

    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
        HairStyle hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
        review = reviewRepository.save(ReviewFixture.A.toEntity(user, hairStyle));
    }

    @Test
    @DisplayName("리뷰를 아이디로 유저 정보와 함께 조회한다")
    void findWithUserById() {
        //when
        Review result = reviewRepository.findWithPhotosById(review.getId()).orElseThrow();

        //then
        assertAll(
                () -> assertThat(result).isEqualTo(review),
                () -> assertThat(result.getWriter()).isEqualTo(user)
        );
    }
}