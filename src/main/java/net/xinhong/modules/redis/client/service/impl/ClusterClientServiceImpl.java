package net.xinhong.modules.redis.client.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xinhong.modules.redis.client.service.ClusterClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Description: <br>
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/4/22.
 */
@Service
public class ClusterClientServiceImpl implements ClusterClientService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultStringRedisConnection stringRedisConnection;


    @Autowired
    private JedisClusterConnection clusterConnection;


    public ClusterInfo getClusterInfo() {
        ClusterInfo clusterInfo = clusterConnection.clusterGetClusterInfo();
        return clusterInfo;
    }

    public JSONArray getClusterNodes() {
        Set<RedisClusterNode> nodeSet = clusterConnection.clusterGetNodes();
        JSONArray treeNodes = new JSONArray();
        JSONObject rootNode = new JSONObject();
        rootNode.put("id", 1);
        rootNode.put("text", "redis server cluster");
        JSONArray serverNode = new JSONArray();
        nodeSet.forEach(node -> {
            if ("MASTER".equals(node.getType().name())) {
                JSONObject dbNode = new JSONObject();
                dbNode.put("id", node.getId());
                dbNode.put("text", node.getHost() + ":" + node.getPort()+"("+clusterConnection.dbSize(node)+")");
                dbNode.put("state", "closed");
                serverNode.add(dbNode);
                rootNode.put("children", serverNode);
            }
        });
        treeNodes.add(rootNode);
        return treeNodes;
    }


    public void loadKeys(){
        ScanOptions.ScanOptionsBuilder builder = ScanOptions.scanOptions();
        builder.count(10);
        ScanOptions scanOptions = builder.build();

        stringRedisConnection.scan(null);
    }

}
