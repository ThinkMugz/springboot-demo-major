package es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/7/2 21:15
 * @description ES聚合查询
 */
@Slf4j
public class AggTest {
    /*
     * 初始化客户端
     */
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("", 9200)));

    // 根据索引构造查询请求
    SearchRequest searchRequest = new SearchRequest(PERSON_INDEX);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    private static final String PERSON_INDEX = "person";

    private static final int START_OFFSET = 0;

    private static final int MAX_COUNT = 2000;

    /**
     * 统计查询：max、min、sum、avg、count
     *
     * @throws IOException e
     */
    @Test
    public void maxQueryTest() throws IOException {
        AggregationBuilder maxBuilder = AggregationBuilders.max("max_age").field("age");
        // AggregationBuilder minBuilder = AggregationBuilders.min("min_age").field("age");
        // AggregationBuilder avgBuilder = AggregationBuilders.avg("min_age").field("age");
        // AggregationBuilder sumBuilder = AggregationBuilders.sum("min_age").field("age");
        // AggregationBuilder countBuilder = AggregationBuilders.count("min_age").field("age");
        searchSourceBuilder.size(5);
        searchSourceBuilder.aggregation(maxBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * stats:一并求max、min、sum、count、avg
     * extendedStats：追加方差、标准差等
     *
     * @throws IOException e
     */
    @Test
    public void statsQueryTest() throws IOException {
        AggregationBuilder aggBuilder = AggregationBuilders.stats("person_stats").field("age");
        //  AggregationBuilder aggBuilder = AggregationBuilders.extendedStats("person_stats").field("age");

        SearchRequest searchRequest = new SearchRequest(PERSON_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 先查询，再统计
     *
     * @throws IOException e
     */
    @Test
    public void queryAndAggQueryTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 对条件查询的结果进行统计
        AggregationBuilder maxBuilder = AggregationBuilders.max("max_age").field("age");
        searchSourceBuilder.query(QueryBuilders.termQuery("sect.keyword", "明教"));
        searchSourceBuilder.aggregation(maxBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 去重查询
     *
     * @throws IOException e
     */
    @Test
    public void cardinalityQueryTest() throws IOException {
        AggregationBuilder aggBuilder = AggregationBuilders.cardinality("sect_count").field("sect.keyword");

        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(aggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    /**
     * 分组查询
     *
     * @throws IOException e
     */
    @Test
    public void groupQueryTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        // 按sect分组
        AggregationBuilder aggBuilder = AggregationBuilders.terms("sect_count").field("sect.keyword");
        searchSourceBuilder.aggregation(aggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    @Test
    public void TermGroupQueryTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        // 按sex分组
        AggregationBuilder ageAggBuilder = AggregationBuilders.avg("age_avg").field("age");

        searchSourceBuilder.query(QueryBuilders.termQuery("sex", "女"));

        searchSourceBuilder.aggregation(ageAggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }


    /**
     * 多字段分组
     *
     * @throws IOException e
     */
    @Test
    public void multiGroupQueryTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        // 按sect分组
        AggregationBuilder sexAggBuilder = AggregationBuilders.terms("sex_count").field("sex.keyword");
        AggregationBuilder sectAggBuilder = AggregationBuilders.terms("sect_count").field("sect.keyword")
                .subAggregation(sexAggBuilder);

        searchSourceBuilder.aggregation(sectAggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    @Test
    public void multiGroupQueryTest2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        // 先按性别分组
        AggregationBuilder sexAggBuilder = AggregationBuilders.terms("sex_count").field("sex.keyword");
        // 再按sect分组
        AggregationBuilder sectAggBuilder = AggregationBuilders.terms("sect_count").field("sect.keyword");
        // 求平均年龄
        AggregationBuilder ageAggBuilder = AggregationBuilders.avg("age_avg").field("age");

        sexAggBuilder.subAggregation(sectAggBuilder);
        sexAggBuilder.subAggregation(ageAggBuilder);


        searchSourceBuilder.aggregation(sexAggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }

    @Test
    public void multiGroupQueryTest3() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        // 求平均年龄
        AggregationBuilder ageAggBuilder = AggregationBuilders.range("age_avg").field("age")
                .addRange(20, 40)
                .addRange(40, 60)
                .addRange(60, 120);


        searchSourceBuilder.aggregation(ageAggBuilder);
        System.out.println("查询语句=====================》" + searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果=====================》" + JSONObject.toJSON(response));
    }


    /**
     * bean转为map
     *
     * @param bean bean
     * @param <T>  T
     * @return T
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null)
                    map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
}
