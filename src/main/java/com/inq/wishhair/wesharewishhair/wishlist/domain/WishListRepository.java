package com.inq.wishhair.wesharewishhair.wishlist.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select distinct w from WishList w " +
            "join fetch w.hairStyle h " +
            "left outer join fetch h.photos " +
            "where w.user.id = :userId " +
            "order by w.createdDate desc")
    Slice<WishList> findByUser(@Param("userId") Long userId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "hairStyle")
    Optional<WishList> findById(Long wishListId);
}