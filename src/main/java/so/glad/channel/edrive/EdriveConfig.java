package so.glad.channel.edrive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EdriveConfig {

	private final static Properties confProps = new Properties();

	static {
		Logger log = LoggerFactory.getLogger(EdriveConfig.class);
		try {
			confProps.load(EdriveConfig.class.getResourceAsStream("/edrive/config.properties"));
		}
		catch (Exception ex) {
			log.warn("Load Edrive Config file failed.", ex);
		}
	}
	
	public static String getProperty(String entry) {
		return confProps.getProperty(entry);
	}

}
