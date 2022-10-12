package com.devmhk.restaurant.mapper;

import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewDto> selectList(ReviewParam reviewParam);
    List<ReviewDto> selectListMyReview(ReviewParam reviewParam);

    Long selectListCount(ReviewParam reviewParam);

    Double selectServiceAvg(ReviewParam reviewParam);

    Double selectDeliciousAvg(ReviewParam reviewParam);

    Double selectAmountAvg(ReviewParam reviewParam);

    Double selectSTotalAvg(ReviewParam reviewParam);

    Long mySelectListCount(ReviewParam reviewParam);
}
