package com.thienloc.springboot.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareSummaryDTO {
    private String title;
    private Long shareCount;
    private Date firstShareDate;
    private Date lastShareDate;
}
