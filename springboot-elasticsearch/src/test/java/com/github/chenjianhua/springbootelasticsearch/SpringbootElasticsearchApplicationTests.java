package com.github.chenjianhua.springbootelasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chenjianhua.springbootelasticsearch.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.Date;

/**
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/index.html
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-query-dsl.html
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.8/java-full-text-queries.html
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.8/java-term-level-queries.html
 */
@Slf4j
@SpringBootTest
class SpringbootElasticsearchApplicationTests {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void createDocument() throws Exception {
        Book book = new Book();
        book.setBookName("elasticSearch");
        book.setBookDate(new Date());
        IndexRequest request = new IndexRequest("book_index3");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        //将我们的数据放入请求，json
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(book);
        request.source(jsonStr, XContentType.JSON);
        //客服端发送请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info(index.toString());
        //对应我们的命令返回状态
        log.info(index.status().toString());
    }

    @Test
    public void testSearch() throws Exception {
        // 通过id获取文档
        final GetRequest request = new GetRequest("book_index3", "1");
        final GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info(response.toString());
        // 判断文档是否存在
        log.info("文档是否存在:{}", restHighLevelClient.exists(request, RequestOptions.DEFAULT));

        //1、创建查询请求，规定查询的索引
        SearchRequest searchRequest = new SearchRequest("book_index3");
        //2、创建条件构造
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //3、DSL查询语言对应的是QueryBuilders.*
        // 全文查询:QueryBuilders.matchQuery("bookName", "算法");
        // QueryBuilders.multiMatchQuery("算法", "bookName");
        // 短语查询:QueryBuilders.termQuery("bookName", "算法");
        // QueryBuilders.termsQuery("bookName", "算法","爱");
        // QueryBuilders.rangeQuery("price").from(5).to(10)
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("bookName", "算法");
        builder.query(termQueryBuilder);
        //分页
        builder.from(0);
        builder.size(10);
        // 排序
        // builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        builder.sort(new FieldSortBuilder("bookDate").order(SortOrder.DESC));
        //4、将构造好的条件放入请求中
        searchRequest.source(builder);

        //5、开始执行发送request请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //6、开始处理返回的数据
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            log.info(hit.toString());
        }
    }

}
