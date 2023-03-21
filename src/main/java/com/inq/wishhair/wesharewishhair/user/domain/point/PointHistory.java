package com.inq.wishhair.wesharewishhair.user.domain.point;

import com.inq.wishhair.wesharewishhair.auditing.BaseEntity;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @Column(nullable = false, updatable = false)
    private Long dealAmount;

    @Column(nullable = false, updatable = false)
    private Long point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    //==생성 메서드==//
    public static PointHistory createJoinPointHistory(User user) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.pointType = PointType.JOIN;
        pointHistory.dealAmount = 0L;
        pointHistory.point = 0L;
        pointHistory.user = user;
        return pointHistory;
    }
}