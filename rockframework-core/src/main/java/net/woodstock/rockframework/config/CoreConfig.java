package net.woodstock.rockframework.config;

public final class CoreConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-core.properties";

	private static CoreConfig	instance	= new CoreConfig();

	private CoreConfig() {
		super(CoreConfig.CONFIG_FILE);
	}

	public static CoreConfig getInstance() {
		return CoreConfig.instance;
	}
}
