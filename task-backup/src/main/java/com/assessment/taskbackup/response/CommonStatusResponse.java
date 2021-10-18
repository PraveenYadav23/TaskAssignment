package com.assessment.taskbackup.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonStatusResponse {

    private String status;

    public CommonStatusResponse(String status) {
        this.status = status;
    }

    public CommonStatusResponse() {
    }
}
