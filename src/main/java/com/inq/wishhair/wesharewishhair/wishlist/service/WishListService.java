package com.inq.wishhair.wesharewishhair.wishlist.service;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleFindService;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishListRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserFindService userFindService;
    private final HairStyleFindService hairStyleFindService;
    private final WishListFindService wishListFindService;

    @Transactional
    public Long createWishList(Long hairStyleId, Long userId) {

        HairStyle hairStyle = hairStyleFindService.findById(hairStyleId);
        User user = userFindService.findByUserId(userId);

        WishList wishList = WishList.createWishList(user, hairStyle);

        hairStyle.plusWishListCount();

        return wishListRepository.save(wishList).getId();
    }

    @Transactional
    public void deleteWishList(Long wishListId) {
        WishList wishList = wishListFindService.findByIdWithHairStyle(wishListId);
        wishList.getHairStyle().minusWishListCount();

        wishListRepository.deleteById(wishListId);
    }
}