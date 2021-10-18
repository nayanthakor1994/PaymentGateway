package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Reporter;

public class BasePage extends Constants {
	public Properties prop;
	
	
	public BasePage() {
		FileInputStream fi;
		try {
			prop = new Properties();
			fi = new FileInputStream(System.getProperty("user.dir") + File.separator + "Testdata" + File.separator
					+ "config.properties");
			prop.load(fi);

		} catch (FileNotFoundException e) {
			System.out.println("Exception from test Base Construction");
		} catch (IOException e) {
			System.out.println("Exception from test Base Construction");
			e.printStackTrace();
		}

	}

	public synchronized void log(String message) {
		Reporter.log(message);
		System.out.println(message);
	}
	
	
}
