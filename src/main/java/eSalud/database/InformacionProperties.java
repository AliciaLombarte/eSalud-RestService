package eSalud.database;

import java.util.*;

public class InformacionProperties {


	private static String strUser;
	
	private static String strPassword;
	
	private static String strClassDriver;
	
	private static String strDatabaseName;

	private static final String nombreProperties = "InfoAplicacion";


	public static String getStrDatabaseName() {
		if (strDatabaseName == null)
			cagarProperties();
		return strDatabaseName;
	}
	
	public static String getStrUser() {
		if (strUser == null)
			cagarProperties();
		return strUser;
	}

	public static String getStrPassword() {
		if (strPassword == null)
			cagarProperties();
		return strPassword;
	}

	public static String getStrClassDriver() {
		if (strClassDriver == null)
			cagarProperties();
		return strClassDriver;
	}
	
	// **************************************************
	private static void cagarProperties() throws MissingResourceException {

		PropertyResourceBundle appProperties = null;

		try {

			appProperties = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(nombreProperties);

			strUser = appProperties.getString("Info.strUser");
			strPassword = appProperties.getString("Info.strPassword");
			strClassDriver = appProperties.getString("Info.strClassDriver");
			strDatabaseName = appProperties.getString("Info.strDatabaseName");
			
			
					
		} catch (MissingResourceException e) {

			throw e;
		}

	}
}