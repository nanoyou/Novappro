package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.constant.Constant;
import com.github.akagawatsurunaki.novappro.model.ApplicationForm;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ApplicationFormService {

    ApplicationForm applicationForm;

    public ApplicationForm initApplicationForm() {
        applicationForm = new ApplicationForm();
        applicationForm.setApplicantId(Constant.DEFAULT_APPLICATION_FORM_ID);
        return applicationForm;
    }

    public void createApplicationForm() {

    }

}
