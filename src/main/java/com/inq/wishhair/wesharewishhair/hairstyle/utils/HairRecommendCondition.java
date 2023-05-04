package com.inq.wishhair.wesharewishhair.hairstyle.utils;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class HairRecommendCondition {

    private List<Tag> tags;
    private Tag userFaceShape;
    private Sex sex;

    public static HairRecommendCondition mainRecommend(List<Tag> tags, Tag userFaceShape, Sex sex) {
        return new HairRecommendCondition(tags, userFaceShape, sex);
    }

    public static HairRecommendCondition subRecommend(Tag userFaceShape, Sex sex) {
        return new HairRecommendCondition(null, userFaceShape, sex);
    }
}