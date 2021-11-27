package com.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/6/22 15:48
 * @Description es基本操作，请勿执行
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ESConfig {

    private String host;

    private Integer port;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
    }
}

