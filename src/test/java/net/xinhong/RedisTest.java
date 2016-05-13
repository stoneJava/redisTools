package net.xinhong;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import redis.clients.util.SafeEncoder;

import java.util.Map;

/**
 * Description: <br>
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/4/21.
 */
public class RedisTest extends BaseTest{


    @Autowired
    private JedisClusterConnection clusterConnection;
    @Autowired
    private RedisTemplate  redisTemplate;

    @Autowired
    private DefaultStringRedisConnection stringRedisConnection;
    @Test
    public void redisTest(){
        String str = "a";
//        SafeEncoder.encode("");
        //clusterConnection.hGet(str.toCharArray(),"");

        System.out.println( SafeEncoder.encode(clusterConnection.hGet(SafeEncoder.encode("airLines"),SafeEncoder.encode("2015-09-09_CA1203"))));;
    }

    @Test
    public void redisString(){
        System.out.println(stringRedisConnection.hGet("airLines","2015-09-09_CA1203"));
    }


    @Test
    public void redisTemplateTest(){
        Map<String,String> map = new DefaultRedisMap<String, String>("air_wni", redisTemplate);
      //  Map<String,String> map1 = new DefaultRedisMap(redisTemplate.boundHashOps("airLines"));

       /* for (String s : map.keySet()) {
            System.out.println(s);
            System.out.println(map.get(s));
        }*/
       // System.out.println(map.get("58.75_260.00_2016040412_36"));

       // System.out.println(stringRedisConnection.hLen("air_wni"));
       /* HashOperations hashOper = redisTemplate.opsForHash();
        System.out.println(hashOper.get("airLines", "2015-09-09_CA1203"));*/
       // get(map);

    }

    public void get(Map<String,String> map){
        System.out.println(map.size());

        System.out.println(map.get("-1.25_67.50_2016040512_09"));
    }

}
