package demo.spring.cloud.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import demo.spring.cloud.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
public class HelloController {

    @Autowired
    private HelloRemote helloRemote;

	@Autowired
	private DiscoveryClient discovery;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.sayHello(name);
	}
	
	@RequestMapping("/all")
	public Object all() {
		System.out.println(discovery.getServices());
		return discovery.getServices();
	}
}
