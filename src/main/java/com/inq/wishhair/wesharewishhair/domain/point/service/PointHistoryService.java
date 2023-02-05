package com.inq.wishhair.wesharewishhair.domain.point.service;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.point.repository.PointHistoryRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public PointHistory getRecentPointHistory(Long userId) {

        Pageable pageable = PageRequest.of(0, 1); // limit 1 의 역할

        // Optional 로 처리하려면 User 에 대한 쿼리가 추가적으로 필요해서 List 로 처리
        List<PointHistory> pointHistories = pointHistoryRepository.findRecentPointByUserId(userId, pageable);
        if (pointHistories.isEmpty()) throw new WishHairException(ErrorCode.NOT_EXIST_KEY);
        return pointHistories.get(0);
    }
}
