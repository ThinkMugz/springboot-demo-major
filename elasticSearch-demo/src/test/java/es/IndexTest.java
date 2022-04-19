package es;

import com.es.EsDemoApplication;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/11/26 17:38
 * @description 索引测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsDemoApplication.class)
public class IndexTest {
    @Autowired
    private RestHighLevelClient client;

    private static final String PERSON_INDEX = "person";

    private static final int START_OFFSET = 0;

    private static final int MAX_COUNT = 2000;

    /**
     * 查询索引
     */
    @Test
    public void queryIndex() throws IOException {
        IndicesClient indices = client.indices();

        GetIndexRequest getRequest = new GetIndexRequest("person");
        GetIndexResponse response = indices.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getMappings());
        Map mappings = response.getMappings();
        //iter 提示foreach
        for (Object key : mappings.keySet()) {
            System.out.println(key + "===" + mappings.get(key));
        }
    }

    /**
     * 添加索引
     *
     * @throws IOException e
     */
    @Test
    public void addIndex() throws IOException {
        //1.使用client获取操作索引对象
        IndicesClient indices = client.indices();
        //2.具体操作获取返回值
        //2.1 设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("persons");

        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        //3.根据返回值判断结果
        System.out.println(createIndexResponse);
    }
}
