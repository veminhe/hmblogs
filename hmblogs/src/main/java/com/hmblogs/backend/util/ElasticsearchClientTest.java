package com.hmblogs.backend.util;

import com.alibaba.fastjson.JSONObject;
import com.hmblogs.backend.entity.TestUsers;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchClientTest {
    @Autowired
    private RestHighLevelClient client;

    String index = "users";

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest(index);
        CreateIndexResponse response = client.indices()
                .create(indexRequest, RequestOptions.DEFAULT);
        log.info("创建索引："+response.isAcknowledged());
    }

    /**
     * 判断索引是否存在
     *
     * @throws IOException
     */
    @Test
    public void indexExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        log.info("索引是否存在："+exists);
    }

    /**
     * 添加文档
     *
     * @throws IOException
     */
    @Test
    public void addDoc() throws IOException {
        IndexRequest request = new IndexRequest(index);
        String source = JSONObject.toJSONString(new TestUsers(10000, "逍遥", 30));
        // 手动设置id
//        request.id("10000");
        request.source(source, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("添加文档："+response.getResult());
    }


    /**
     * 批量添加文档
     */
    @Test
    public void batchAddDoc() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> requests = generateRequests();
        for (IndexRequest indexRequest : requests) {
            bulkRequest.add(indexRequest);
        }
        BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("批量添加结果："+!responses.hasFailures());
    }

    public List<IndexRequest> generateRequests() {
        List<IndexRequest> requests = new ArrayList<>();
        requests.add(generateNewsRequest(1, "小明", 22));
        requests.add(generateNewsRequest(2, "隔壁老王", 30));
        requests.add(generateNewsRequest(3, "lily", 25));
        return requests;
    }

    public IndexRequest generateNewsRequest(Integer id, String name, Integer age) {
        IndexRequest indexRequest = new IndexRequest(index);
        String source = JSONObject.toJSONString(new TestUsers(id, name, age));
        indexRequest.source(source, XContentType.JSON);
        return indexRequest;
    }

    /**
     * 更新文档
     *
     * @throws IOException
     */
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, "AmxCP40BM8-M0To1vOET");
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");
        params.put("name", "逍遥");
        params.put("age", 33);
        params.put("hobby", "唱歌,跳舞,网上冲浪,看电影,旅行");
        updateRequest.doc(params);
        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("修改文档结果："+response.getResult());
    }

    /**
     * 搜索
     *
     * @throws IOException
     */
    @Test
    public void search() throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder
                .must(new RangeQueryBuilder("age").from(20).to(30))
                .must(new TermQueryBuilder("id", 3));
        builder.query(boolQueryBuilder);
        request.source(builder);
        log.info("搜索语句为: " + request.source().toString());
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        log.info("搜索结果为："+search);
        SearchHits hits = search.getHits();
        SearchHit[] hitsArr = hits.getHits();
        for (SearchHit documentFields : hitsArr) {
            log.info(documentFields.getSourceAsString());
        }
    }


    @Test
    public void search2() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.fetchSource(new String[]{"name", "age"}, new String[]{});
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("hobby", "唱歌,跳舞,网上冲浪,看电影,旅行");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "逍遥");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gte(20);
        rangeQueryBuilder.lte(40);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
        boolBuilder.must(termQueryBuilder);
        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);
        try {
            log.info("搜索语句为: " + searchRequest.source().toString());
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("搜索结果为："+search);
            SearchHits hits = search.getHits();
            SearchHit[] hitsArr = hits.getHits();
            for (SearchHit documentFields : hitsArr) {
                log.info(documentFields.getSourceAsString());
            }
        } catch (IOException e) {
            log.error("搜索报错：{}",e);
        }
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @Test
    public void deleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, "3g8vP40Bi8WQ8ue06wWd");
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("删除结果为："+response.getResult());
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices()
                .delete(request, RequestOptions.DEFAULT);
        log.info("删除索引结果为："+response.isAcknowledged());
    }

}
