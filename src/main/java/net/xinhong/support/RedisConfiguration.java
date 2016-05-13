package net.xinhong.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: <br>
 * @author 作者 <a href=ds.lht@163.com>邓帅</a>
 * @version 创建时间：2016/4/21.
 */
@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.timeout}")
    private long timeout;
    @Value("${spring.redis.cluster.max-redirects}")
    private int redirects;

    @Bean
    public RedisClusterConfiguration getClusterConfiguration() {

        Map<String, Object> source = new HashMap<>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", redirects);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory(getClusterConfiguration());
       /* jedisConnectionFactory.setUsePool(true);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1);
        jedisConnectionFactory.setPoolConfig(poolConfig);*/

        return  jedisConnectionFactory;
    }


    @Bean
    public JedisClusterConnection getJedisClusterConnection() {
        JedisConnectionFactory clusterConnectionFactory = getConnectionFactory();
        return (JedisClusterConnection) clusterConnectionFactory.getConnection();
    }

    @Bean
    public DefaultStringRedisConnection getDefaultStringRedisConnection(){
        return new DefaultStringRedisConnection(getConnectionFactory().getConnection());
    }

    @Bean
    public RedisTemplate getRedisTemplate(){
        return new StringRedisTemplate(getConnectionFactory());
    }



}
