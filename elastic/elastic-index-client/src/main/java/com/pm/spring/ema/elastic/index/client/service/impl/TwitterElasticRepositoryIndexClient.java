package com.pm.spring.ema.elastic.index.client.service.impl;

import com.pm.spring.ema.elastic.index.client.service.ElasticIndexClient;
import com.pm.spirng.chat.elastic.model.index.ElasticIndexModel;
import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import com.pm.spring.ema.elastic.index.client.repository.TwitterElasticsearchIndexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<MessageElasticIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;

    public TwitterElasticRepositoryIndexClient(TwitterElasticsearchIndexRepository indexRepository) {
        this.twitterElasticsearchIndexRepository = indexRepository;
    }

    @Override
    public List<String> save(List<MessageElasticIndexModel> documents) {
        List<MessageElasticIndexModel> repositoryResponse = (List<MessageElasticIndexModel>) twitterElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream().map(ElasticIndexModel::getId).collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}", ElasticIndexModel.class.getName(), ids);
        return ids;
    }
}
