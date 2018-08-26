package demo.spring.cloud.remote;

import demo.spring.cloud.remote.fallback.RemoteFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by summer on 2017/5/11.
 */
@FeignClient(name= "service-zookeeper", fallback = RemoteFallback.class)
public interface HelloRemote {

    @RequestMapping(value = "/hello")
    public String sayHello(@RequestParam(value = "name") String name);


}
