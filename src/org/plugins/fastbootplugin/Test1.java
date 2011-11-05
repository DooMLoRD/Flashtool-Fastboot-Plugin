package org.plugins.fastbootplugin;

import org.adb.FastbootUtility;
import org.logger.MyLogger;
import org.plugins.PluginDefaults;

public class Test1 extends PluginDefaults implements org.plugins.PluginInterface {


	public String getName() {
		return "DooMLoRD Fastboot Plugin Test1";
	}


	public void run() throws Exception {
		try {
			MyLogger.info("Fastboot Plugin Test1 is running");
			
			showToolbox();
			
			MyLogger.info("Fastboot Plugin Test1 has finished");

		}
		catch (Exception e) {
			MyLogger.error(e.getMessage());
		}
	}


	public void showToolbox() {
		Toolbox tb = new Toolbox();
		tb.setVisible(true);
	}

	
	public void showAbout() {
		About about = new About();
		about.setVisible(true);
	}

}