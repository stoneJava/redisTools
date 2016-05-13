package net.xinhong.modules.redis.client.service.impl;

import com.alibaba.fastjson.JSONObject;
import net.xinhong.BaseTest;
import net.xinhong.commons.utils.FileUtil;
import net.xinhong.modules.redis.client.service.ClusterClientService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/4/22.
 */
public class RedisClusterClientServiceImplTest extends BaseTest {


    @Autowired
    ClusterClientService redisClientService;
    @Autowired
    private JedisClusterConnection clusterConnection;
    @Autowired
    private DefaultStringRedisConnection stringRedisConnection;


    @Autowired
    private RedisTemplate tmp;


    @Test
    public void getClusterNodes() throws Exception {
        JedisConnection con = null;
       /* redisClientService.getClusterNodes().forEach(redisNode -> {
            System.out.println(redisNode.getHost()+":"+redisNode.getPort());
            System.out.println(redisNode.getType());
        });*/
        clusterConnection.clusterGetNodes().forEach(redisClusterNode -> {

            System.out.println(redisClusterNode.getHost() + ":" + redisClusterNode.getPort());
            System.out.println(redisClusterNode.getType());
        });
        //System.out.println(clusterConnection.clusterGetClusterInfo());
    }


    @Test
    public void loadKeys() {
        JedisCluster cluster = clusterConnection.getNativeConnection();
        cluster.getClusterNodes().entrySet().forEach(stringJedisPoolEntry -> {
            Jedis jedis = stringJedisPoolEntry.getValue().getResource();
            try {
                if (stringJedisPoolEntry.getKey().equals("114.112.96.173:5001")) {
                    System.out.println(stringJedisPoolEntry.getKey());
                    ScanResult<String> result = jedis.scan("0");
                    List<String> list = result.getResult();
                    System.out.println(list.size());
                    for (String s : list) {
                        System.out.println(s);
                    }
                }
            } finally {
                jedis.close();
            }
        });
    }


    @Test
    public void expHash() {

       /* ScanOptions.ScanOptionsBuilder builder = ScanOptions.scanOptions();
        builder.count(1000);
        builder.match("*");

        ScanOptions scanOptions = builder.build();

        Cursor<Map.Entry<String, String>> cursor = stringRedisConnection.hScan("stationdata_surf", scanOptions);

        while (cursor.hasNext()) {
            Map.Entry<String, String> next = cursor.next();

        } */
        try {
            //   String key = "stationdata_surf";
            String key = "stationdata_cityfc";//278520
            Map<String, String> map = stringRedisConnection.hGetAll(key);
            JSONObject obj = (JSONObject) JSONObject.toJSON(map);
            FileUtil.appendFile("d:\\" + key + ".json", obj.toJSONString(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadHash() {

        String str = FileUtil.loadFile("d:\\stationdata_surf.json");
        Map<String, String> map = new HashMap<>();
        JSONObject obj = JSONObject.parseObject(str);
        obj.keySet().forEach(s -> map.put(s, obj.getString(s)));

        stringRedisConnection.hMSet("stationdata_surf", map);
    }

}