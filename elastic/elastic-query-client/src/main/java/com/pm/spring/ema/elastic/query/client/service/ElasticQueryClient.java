package com.pm.spring.ema.elastic.query.client.service;


import com.pm.spirng.chat.elastic.model.index.ElasticIndexModel;

import java.util.List;

public interface ElasticQueryClient<T extends ElasticIndexModel> {

    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModels();
}
