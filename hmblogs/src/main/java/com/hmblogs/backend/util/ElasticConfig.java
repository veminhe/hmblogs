package com.hmblogs.backend.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Value("${es.host}")
    public String host;
    @Value("${es.port}")
    public int port;
    @Value("${es.scheme}")
    public String scheme;

    @Value("${es.user}")
    public String user;

    @Value("${es.password}")
    public String password;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient getRestHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(host, port, scheme));
        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(30*60);
            requestConfigBuilder.setSocketTimeout(30*60);
            requestConfigBuilder.setConnectionRequestTimeout(30*60);
            return requestConfigBuilder;
        });

        // 用户认证对象
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        // 设置账号密码
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user,password));

        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(100);
            httpClientBuilder.setMaxConnPerRoute(100);

            // 设置账号密码
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });


        builder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

}
