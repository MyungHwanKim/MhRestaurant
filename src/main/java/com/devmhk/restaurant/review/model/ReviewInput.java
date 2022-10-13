package com.devmhk.restaurant.review.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInput {

    private Long reviewId;
    private String reserveId;
    private String userId;

    @NotEmpty(message = "리뷰 내용은 빈 값이 올 수 없습니다.")
    @Length(min = 10, max = 500, message = "최소 10자 이상 작성하기 바랍니다.")
    private String comment;

    @Min(value = 1)
    @Max(value = 5)
    private int serviceScore;

    @Min(value = 1)
    @Max(value = 5)
    private int deliciousScore;

    @Min(value = 1)
    @Max(value = 5)
    private int amountScore;

}
