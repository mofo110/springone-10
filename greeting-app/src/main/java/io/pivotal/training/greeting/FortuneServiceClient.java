package io.pivotal.training.greeting;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class FortuneServiceClient {
	 private FortuneAPI fortuneAPI;

	private final Logger logger = LoggerFactory
			.getLogger(FortuneServiceClient.class);

	public FortuneServiceClient(FortuneAPI fortuneAPI) {
		this.fortuneAPI = fortuneAPI;
	}

	@HystrixCommand(fallbackMethod = "defaultFortune")
	public String getFortune() {
		Map<String,String> result = fortuneAPI.getFortune();
		String fortune = result.get("fortune");
		logger.info("received fortune '{}'", fortune);
		return fortune;
	}

	public String defaultFortune() {
		logger.info("Default fortune used.");
		return "Your future is uncertain";
	}
}