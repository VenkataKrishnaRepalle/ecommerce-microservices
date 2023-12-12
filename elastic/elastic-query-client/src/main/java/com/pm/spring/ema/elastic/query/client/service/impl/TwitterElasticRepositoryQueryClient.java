package com.pm.spring.ema.elastic.query.client.service.impl;

import com.pm.spring.ema.common.util.CollectionsUtil;
import com.pm.spring.ema.elastic.query.client.service.ElasticQueryClient;
import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import com.pm.spring.ema.elastic.query.client.exception.ElasticQueryClientException;
import com.pm.spring.ema.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<MessageElasticIndexModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryQueryClient.class);

    private final TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository;

    public TwitterElasticRepositoryQueryClient(TwitterElasticsearchQueryRepository repository) {
        this.twitterElasticsearchQueryRepository = repository;
    }

    @Override
    public MessageElasticIndexModel getIndexModelById(String id) {
        Optional<MessageElasticIndexModel> searchResult = twitterElasticsearchQueryRepository.findById(id);
        LOG.info("Document with id {} retrieved successfully",
                searchResult.orElseThrow(() ->
                        new ElasticQueryClientException("No document found at elasticsearch with id " + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<MessageElasticIndexModel> getIndexModelByText(String text) {
        List<MessageElasticIndexModel> searchResult = twitterElasticsearchQueryRepository.findByText(text);
        LOG.info("{} of documents with text {} retrieved successfully", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<MessageElasticIndexModel> getAllIndexModels() {
        List<MessageElasticIndexModel> searchResult =
                CollectionsUtil.getInstance().getListFromIterable(twitterElasticsearchQueryRepository.findAll());
        LOG.info("{} number of documents retrieved successfully", searchResult.size());
        return searchResult;
    }
}
