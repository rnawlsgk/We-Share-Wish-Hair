package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleFindService;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.service.PointService;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final LikeReviewRepository likeReviewRepository;
    private final ReviewFindService reviewFindService;
    private final PhotoRepository photoRepository;
    private final PhotoStore photoStore;
    private final PointService pointService;
    private final UserFindService userFindService;
    private final HairStyleFindService hairStyleFindService;

    @Transactional
    public Long createReview(ReviewCreateRequest request, Long userId) {

        List<Photo> photos = photoStore.storePhotos(request.getFiles());
        User user = userFindService.findByUserId(userId);
        HairStyle hairStyle = hairStyleFindService.findById(request.getHairStyleId());

        Review review = generateReview(request, photos, user, hairStyle);
        pointService.chargePoint(100, user.getId());

        return reviewRepository.save(review).getId();
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewFindService.findById(reviewId);
        validateIsWriter(userId, review);
        likeReviewRepository.deleteAllByReview(reviewId);
        photoRepository.deleteAllByReview(reviewId);
        reviewRepository.delete(review);
    }

    private void validateIsWriter(Long userId, Review review) {
        if (!review.isWriter(userId)) {
            throw new WishHairException(ErrorCode.REVIEW_NOT_WRITER);
        }
    }

    private Review generateReview(ReviewCreateRequest request, List<Photo> photos, User user, HairStyle hairStyle) {
        return Review.createReview(
                user,
                request.getContents(),
                request.getScore(),
                photos,
                hairStyle
        );
    }
}
