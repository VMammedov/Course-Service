package com.company.courseservice.response.Appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import utils.PaginationInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AppealListResponse {
    private List<AppealResponse> items;
    private PaginationInfo paginationInfo;
}
