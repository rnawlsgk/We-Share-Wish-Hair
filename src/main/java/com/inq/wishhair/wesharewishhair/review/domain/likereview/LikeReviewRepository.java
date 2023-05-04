package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

    @Modifying
    @Query("delete from LikeReview l where l.reviewId = :reviewId")
    void deleteAllByReview(@Param("reviewId") Long reviewId);

    @Modifying
    void deleteByUserIdAndReviewId(Long userId, Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
}