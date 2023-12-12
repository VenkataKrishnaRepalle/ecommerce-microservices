package com.pm.spring.ema.elastic.query.service.common.transformer;

import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import com.pm.spring.ema.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticToResponseModelTransformer {

    public ElasticQueryServiceResponseModel getResponseModel(MessageElasticIndexModel messageElasticIndexModel) {
        return ElasticQueryServiceResponseModel
                .builder()
                .id(messageElasticIndexModel.getId())
                .userId(messageElasticIndexModel.getSenderId())
                .message(messageElasticIndexModel.getMessage())
                .createdAt(messageElasticIndexModel.getCreatedAt())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModels(List<MessageElasticIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::getResponseModel).collect(Collectors.toList());
    }
}
