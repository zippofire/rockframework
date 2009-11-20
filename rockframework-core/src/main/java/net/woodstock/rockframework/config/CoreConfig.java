package net.woodstock.rockframework.config;

import java.io.IOException;
import java.net.URISyntaxException;

public class CoreConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-core.properties";

	private static CoreConfig	config;

	private CoreConfig() throws URISyntaxException, IOException {
		super(CoreConfig.CONFIG_FILE);
	}

	public static CoreConfig getInstance() {
		if (CoreConfig.config == null) {
			synchronized (CoreConfig.class) {
				if (CoreConfig.config == null) {
					try {
						CoreConfig.config = new CoreConfig();
					} catch (URISyntaxException e) {
						throw new RuntimeException(e);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return CoreConfig.config;
	}
}
