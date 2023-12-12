package com.pm.spring.ema.elastic.query.service.business;

import com.pm.spring.ema.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.pm.spring.ema.elastic.query.service.model.ElasticQueryServiceAnalyticsResponseModel;

import java.util.List;

public interface ElasticQueryService {

    ElasticQueryServiceResponseModel getDocumentById(String id);

    ElasticQueryServiceAnalyticsResponseModel getDocumentByText(String text, String accessToken);

    List<ElasticQueryServiceResponseModel> getAllDocuments();
}
