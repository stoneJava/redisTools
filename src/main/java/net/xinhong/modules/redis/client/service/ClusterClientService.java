package net.xinhong.modules.redis.client.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.data.redis.connection.ClusterInfo;

/**
 * Description: <br>
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/4/22.
 */
public interface ClusterClientService {

      ClusterInfo getClusterInfo();
      JSONArray getClusterNodes();
}
