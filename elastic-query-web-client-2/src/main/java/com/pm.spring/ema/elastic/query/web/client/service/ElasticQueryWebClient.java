package com.pm.spring.ema.elastic.query.web.client.service;

import com.spring.chat.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.spring.chat.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel);
}
