package com.inqwise.opinion.infrastructure.systemFramework;

import java.io.File;
import java.io.FilenameFilter;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class FontsManager {
	
	private static File fontDir = null;
	static ApplicationLog logger = ApplicationLog.getLogger(FontsManager.class);
	
	static {
		fontDir = new File(BaseApplicationConfiguration.Fonts.getFontsPath());
	}
	
	private static File[] fontFiles;
	
	public static File[] getFontFiles(){
		if(null == fontFiles){
			if (fontDir.isDirectory()) {
				fontFiles = fontDir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String lower = name.toLowerCase();
						// Load TTF or OTF files
						return lower.endsWith(".otf") || lower.endsWith(".ttf");
					}
				});
			}
		}
		return fontFiles;
	}
}
