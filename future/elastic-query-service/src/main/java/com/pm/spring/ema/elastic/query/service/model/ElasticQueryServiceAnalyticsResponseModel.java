package com.pm.spring.ema.elastic.query.service.model;

import com.pm.spring.ema.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticQueryServiceAnalyticsResponseModel {
    private List<ElasticQueryServiceResponseModel> queryResponseModels;
    private Long wordCount;
}
