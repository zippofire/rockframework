package net.woodstock.rockframework.web.config;

import java.io.IOException;
import java.net.URISyntaxException;

import net.woodstock.rockframework.config.AbstractConfig;

public class WebConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-web.properties";

	private static WebConfig	coreConfig;

	private WebConfig() throws URISyntaxException, IOException {
		super(WebConfig.CONFIG_FILE);
	}

	public static WebConfig getInstance() {
		if (WebConfig.coreConfig == null) {
			synchronized (WebConfig.class) {
				if (WebConfig.coreConfig == null) {
					try {
						WebConfig.coreConfig = new WebConfig();
					} catch (URISyntaxException e) {
						throw new RuntimeException(e);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return WebConfig.coreConfig;
	}
}
