package com.pm.spring.ema.elastic.query.client.repository;

import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwitterElasticsearchQueryRepository extends ElasticsearchRepository<MessageElasticIndexModel, String> {

    List<MessageElasticIndexModel> findByText(String text);
}
