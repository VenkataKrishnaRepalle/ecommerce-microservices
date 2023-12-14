package com.pm.spring.ema.elastic.index.client.repository;

import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticsearchIndexRepository extends ElasticsearchRepository<MessageElasticIndexModel, String> {
}
