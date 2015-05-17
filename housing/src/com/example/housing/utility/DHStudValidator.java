package com.example.housing.utility;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class DHStudValidator{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean validate(String moodlename, String moodlepassword) {
		
		String userName = moodlename.replaceAll("@dh-karlsruhe.de", "");
		Hashtable env = new Hashtable();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://aragorn:389/dc=dh-karlsruhe,dc=de");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, new String("dh-karlsruhe" + "\\" + userName));
		env.put(Context.SECURITY_CREDENTIALS, moodlepassword);

		DirContext ctx = null;
		NamingEnumeration results = null;
		
		try {
			ctx = new InitialDirContext(env);
		} catch (Throwable e) {
			return false;
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (Exception e) {
				}
				
			}
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
				} 
			} 
		} return true;
	}
}
