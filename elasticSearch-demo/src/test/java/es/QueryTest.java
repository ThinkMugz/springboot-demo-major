package es;

import com.alibaba.fastjson.JSONObject;
import com.es.EsDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/7/27 11:30
 * @description ES条件查询
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = EsDemoApplication.class)
@Slf4j
public class QueryTest {
    //    @Autowired
//    private RestHighLevelClient client;

    private static final String PERSON_INDEX = "person";

    private static final int START_OFFSET = 0;

    private static final int MAX_COUNT = 2000;

    /**
     *
     */
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("82.157.165.196", 9200)));

    SearchRequest searchRequest = new SearchRequest(PERSON_INDEX);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    /**
     * 通过id查询文档
     *
     * @throws IOException
     */
    @Test
    public void queryDocumentById() throws IOException {
        GetRequest getRequest = new GetRequest(PERSON_INDEX, "1");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("geqPersonById================》" + JSONObject.toJSON(response));
    }

    /**
     * term等值查询，等同于 = 查询
     *
     * @throws IOException
     */
    @Test
    public void termQueryTest() throws IOException {

        searchSourceBuilder.query(QueryBuilders.termQuery("sect.keyword", "明教"));
        System.out.println("searchSourceBuilder=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * constantScoreQuery，不打分查询
     *
     * @throws IOException
     */
    @Test
    public void constantScoreQueryTest() throws IOException {
        // 这样构造的查询条件，将不进行score计算，从而提高查询效率
        searchSourceBuilder.query(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("sect.keyword", "明教")));
        System.out.println("searchSourceBuilder=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * terms多值查询，等同于 in 查询
     *
     * @throws IOException
     */
    @Test
    public void termsQueryTest() throws IOException {
        searchSourceBuilder.query(QueryBuilders.termsQuery("sect.keyword", Arrays.asList("明教", "武当派")));
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 范围查询，等同于between...and...
     * select * from persons where age between 10 and 30
     *
     * @throws IOException
     */
    @Test
    public void rangeQueryTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(10).lte(30));
        System.out.println("查询语句=====================" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 前缀查询，等同于 李%
     * select * from person where sect like ‘武当%’
     *
     * @throws IOException
     */
    @Test
    public void prefixQueryTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.prefixQuery("sect.keyword", "武当"));
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 通配符查询，等同于 %无%
     *
     * @throws IOException
     */
    @Test
    public void wildcardQueryTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("sect.keyword", "张*忌"));
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 布尔查询，构造多条件查询
     *
     * @throws IOException
     */
    @Test
    public void boolQueryTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("sex", "女"))
                .must(QueryBuilders.termQuery("sect.keyword", "明教"))

        );
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    @Test
    public void boolQueryTest2() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("sex", "女"))
                .should(QueryBuilders.termQuery("address.word", "峨眉山"))
                .should(QueryBuilders.termQuery("sect.keyword", "明教"))
                .minimumShouldMatch(1)
        );
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    @Test
    public void boolQueryTest3() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("age").gte(10).lte(30))
                .must(QueryBuilders.termQuery("sect.keyword", "明教"))
                .filter(QueryBuilders.termQuery("sex", "男"))
        );

        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * filter直接包含查询语句
     *
     * @throws IOException
     */
    @Test
    public void filterTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("sex", "男"))
        );// 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("sex", "男"))
        );
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * filter与must等同级使用
     *
     * @throws IOException
     */
    @Test
    public void filterTest2() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("sect.keyword", "明教"))
                .filter(QueryBuilders.termQuery("sex", "女"))
        );
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * filter包含must等
     *
     * @throws IOException
     */
    @Test
    public void filterTest3() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("sect.keyword", "明教"))
                        .must(QueryBuilders.rangeQuery("age").gte(20).lte(35))
                        .mustNot(QueryBuilders.termQuery("sex.keyword", "女")))
        );
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * filter
     *
     * @throws IOException
     */
    @Test
    public void filterTest4() throws IOException {
        // 构建查询语句
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("sex", "女"))
                .must(QueryBuilders.rangeQuery("age").gte(30).lte(40))
                .mustNot(QueryBuilders.termQuery("sect.keyword", "明教"))
                .should(QueryBuilders.termQuery("address.keyword", "峨眉山"))
                .should(QueryBuilders.rangeQuery("power.keyword").gte(50).lte(80))
                .minimumShouldMatch(1);

        searchSourceBuilder.query(boolQueryBuilder);

        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * match：单条件匹配
     *
     * @throws IOException
     */
    @Test
    public void matchQueryTest() throws IOException {
        // 构建查询语句
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "殷素素"));
        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * multiMatch：多field对同一个text匹配
     *
     * @throws IOException
     */
    @Test
    public void multiMatchQueryTest() throws IOException {
        /**
         * operator传入AND或OR，决定了多个匹配条件之间的关系是与还是或
         */
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("武当", "sect", "address")
                .operator(Operator.valueOf("AND"))
                .minimumShouldMatch("50%"));

        System.out.println("查询语句=====================》" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

}
