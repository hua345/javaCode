package demo.spring.cloud.remote.fallback;

import demo.spring.cloud.remote.HelloRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class RemoteFallback implements HelloRemote {

	@Override
	public String sayHello(@RequestParam(name = "name") String name) {
		return null;
	}
}