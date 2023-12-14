package com.pm.spring.ema.elastic.query.web.client.service;

import com.spring.chat.elastic.query.web.client.common.model.ElasticQueryWebClientAnalyticsResponseModel;
import com.spring.chat.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;

public interface ElasticQueryWebClient {

    ElasticQueryWebClientAnalyticsResponseModel getDataByText(ElasticQueryWebClientRequestModel requestModel);
}
