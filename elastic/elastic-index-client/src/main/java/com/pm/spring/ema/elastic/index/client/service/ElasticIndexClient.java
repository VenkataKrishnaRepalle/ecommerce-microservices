package com.pm.spring.ema.elastic.index.client.service;


import com.pm.spirng.chat.elastic.model.index.ElasticIndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends ElasticIndexModel> {
    List<String> save(List<T> documents);
}
