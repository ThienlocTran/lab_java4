package com.thienloc.springboot.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareSummaryDto {
    private Long videoId;
    private String videoTitle;
    private String videoPoster;
    private Integer shareCount;
    private String firstShareDate;
    private String lastShareDate;
}
