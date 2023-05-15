package com.github.akagawatsurunaki.novappro.model.frontend;

import lombok.Builder;

@Builder
public class ServiceResult {

    ServiceMessage serviceMessage;

    Object model;

}
