package net.xinhong.modules.redis.client.controller;

import com.alibaba.fastjson.JSONArray;
import net.xinhong.modules.redis.client.service.ClusterClientService;
import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Description: <br>
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/4/22.
 */
@Controller
@RequestMapping("/cluster")
public class ClusterClientController {

    @Resource
    ClusterClientService clusterService;

    public void getClusterInfo(){
        ClusterInfo clusterInfo = clusterService.getClusterInfo();
    }


    @RequestMapping("/nodes")
   @ResponseBody
    public JSONArray getClusterNodes(){
        return clusterService.getClusterNodes();
    }



}
