package com.company.courseservice.response.Appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.PaginationInfo;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppealListResponse {
    private List<AppealResponse> items;
    private PaginationInfo paginationInfo;
}
