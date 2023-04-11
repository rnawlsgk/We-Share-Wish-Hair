package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleSearchServiceTest extends ServiceTest {
    //todo 검증코드 리팩토링하기

    @Autowired
    private HairStyleSearchService hairStyleSearchService;

    @BeforeEach
    void setUp() {
        //given
        hairStyleSearchRepository.save(A.toEntity());
        hairStyleSearchRepository.save(B.toEntity());
        hairStyleSearchRepository.save(C.toEntity());
        hairStyleSearchRepository.save(D.toEntity());
        hairStyleSearchRepository.save(E.toEntity());
    }

    @Nested
    @DisplayName("헤어스타일 추천 로직")
    class findRecommendedHairStyle {
        @Test
        @DisplayName("입력된 태그의 얼굴형 태그가 포함되지 않은 헤어스타일은 조회되지 않는다")
        void test1() {
            //given
            List<Tag> tags = A.getTags();
            Long userId = userRepository.save(UserFixture.B.toEntity()).getId();

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.findRecommendedHairStyle(tags, userId, getDefaultPageable());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertAll(
                    () -> assertThat(actual).hasSize(1),
                    () -> {
                        HairStyleResponse response = actual.get(0);
                        assertThat(response.getName()).isEqualTo(A.getName());
                        assertThat(response.getHashTags().stream().map(HashTagResponse::getTag).toList())
                                .containsAll(A.getTags().stream().map(Tag::getDescription).toList());
                    }
            );
        }

        @Test
        @DisplayName("입력된 태그에 얼굴형 태그가 포함되지 않으면 400 예외를 던진다")
        void test2() {
            //given
            List<Tag> tags = List.of(Tag.PERM);
            Long userId = userRepository.save(UserFixture.B.toEntity()).getId();
            ErrorCode expectedError = ErrorCode.RUN_NO_FACE_SHAPE_TAG;

            //when, then
            assertThatThrownBy(() -> hairStyleSearchService.findRecommendedHairStyle(tags, userId, getDefaultPageable()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("조회된 헤어스타일은 일치하는 해시태그 수, 찜수, 이름으로 정렬된다")
        void test3() {
            //given
            List<Tag> tags = E.getTags();
            Long userId = userRepository.save(UserFixture.B.toEntity()).getId();

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.findRecommendedHairStyle(tags, userId, getDefaultPageable());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertAll(
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual.stream().map(HairStyleResponse::getName).toList())
                            .containsExactly(E.getName(), C.getName(), D.getName())
            );
        }

        @Test
        @DisplayName("헤어스타일을 추천 후 첫번째 헤어스타일의 얼굴형 태그로 사용자의 faceShapeTag 를 업데이트 한다")
        void test4() {
            //given
            List<Tag> tags = E.getTags();
            User user = UserFixture.B.toEntity();
            userRepository.save(user);

            //before
            assertThat(user.existFaceShape()).isFalse();

            //when
            hairStyleSearchService.findRecommendedHairStyle(tags, user.getId(), getDefaultPageable());

            //then
            assertAll(
                    () -> assertThat(user.existFaceShape()).isTrue(),
                    () -> assertThat(user.getFaceShape()).isEqualTo(Tag.OBLONG) // <- E 헤어스타일의 얼굴형 태그
            );
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 기반 헤어추천 서비스 로직")
    class findHairStyleByFaceShape {
        @Test
        @DisplayName("얼굴형 태그가 저장돼 있는 유저라면 얼굴형 태그 기반으로 헤어가 찜수, 이름으로 정렬되어 조회된다")
        void test5() {
            //given
            User user = UserFixture.B.toEntity();
            userRepository.save(user);
            user.updateFaceShape(new FaceShape(Tag.OBLONG));

            //when
            ResponseWrapper<HairStyleResponse> result = hairStyleSearchService.findHairStyleByFaceShape(user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertAll(
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual.stream().map(HairStyleResponse::getName).toList())
                            .containsExactly(C.getName(), E.getName(), D.getName())
            );
        }

        @Test
        @DisplayName("얼굴형 태그가 저장돼 있지 않은 유저라면 태그 없이 헤어가 찜수, 이름으로 정렬되어 조회된다")
        void test6() {
            //given
            User user = UserFixture.B.toEntity();
            userRepository.save(user);

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.findHairStyleByFaceShape(user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertAll(
                    () -> assertThat(actual).hasSize(4),
                    () -> assertThat(actual.stream().map(HairStyleResponse::getName).toList())
                            .containsExactly(C.getName(), A.getName(), E.getName(), D.getName())
            );
        }
    }
}
