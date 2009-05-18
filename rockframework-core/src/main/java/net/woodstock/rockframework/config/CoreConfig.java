package net.woodstock.rockframework.config;

import java.io.IOException;
import java.net.URISyntaxException;

public class CoreConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-core.properties";

	private static CoreConfig	coreConfig;

	private CoreConfig() throws URISyntaxException, IOException {
		super(CoreConfig.CONFIG_FILE);
	}

	public static CoreConfig getInstance() {
		if (CoreConfig.coreConfig == null) {
			synchronized (CoreConfig.class) {
				if (CoreConfig.coreConfig == null) {
					try {
						CoreConfig.coreConfig = new CoreConfig();
					} catch (URISyntaxException e) {
						throw new RuntimeException(e);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return CoreConfig.coreConfig;
	}
}
