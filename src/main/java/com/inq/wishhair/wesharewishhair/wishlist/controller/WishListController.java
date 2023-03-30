package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.wishlist.controller.dto.response.PagedWishListResponse;
import com.inq.wishhair.wesharewishhair.wishlist.domain.WishList;
import com.inq.wishhair.wesharewishhair.wishlist.service.WishListService;
import com.inq.wishhair.wesharewishhair.wishlist.service.dto.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/wish_list/{hairStyleId}")
    public ResponseEntity<Success> createWishList(
            @PathVariable Long hairStyleId,
            @ExtractPayload Long userId) {

        Long wishListId = wishListService.createWishList(hairStyleId, userId);

        return ResponseEntity
                .created(URI.create("/api/wish_list/" + wishListId))
                .body(new Success());
    }

    @DeleteMapping("/wish_list/{id}")
    public ResponseEntity<Success> deleteWishList(@PathVariable Long id) {

        wishListService.deleteWishList(id);
        return ResponseEntity.ok(new Success());
    }

    @GetMapping("/wish_list")
    public ResponseEntity<PagedWishListResponse> getWishList(
            @PageableDefault(size = 4) Pageable pageable,
            @ExtractPayload Long userId) {

        Slice<WishListResponse> result = wishListService.getWishList(userId, pageable);

        return ResponseEntity.ok(new PagedWishListResponse(result));
    }
}