package com.github.akagawatsurunaki.novappro.service.base;

import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SearchService {

    @Getter
    private static final SearchService instance = new SearchService();

    public Pair<ServiceMessage, List<ApprovalFlow>> searchApprovalFlow() {
        throw new NotImplementedException("");
    }

    public Pair<ServiceMessage, List<CourseApplication>> searchCourseApplications() {
        throw new NotImplementedException("");
    }

}
