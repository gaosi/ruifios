package com.ruifios;

import org.eclipse.jetty.server.Server;

import com.ruifios.jetty.JettyFactory;
import com.ruifios.jetty.Profiles;

public class SecurityStartServer extends Profiles  
{

	private static final int PORT = 8888;
	
	private static final int HTTPS_PORT = 8443;

	private static final String CONTEXT = "/";
	
	private static final String[] TLD_JAR_NAMES = new String[] { "sitemesh" };
	
	public static void main(String[] args)
	{
		// 设定Spring的profile
		setProfileAsSystemProperty(Profiles.PRODUCTION);
		
		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.addConnector(server, HTTPS_PORT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
		 
		try {
		    server.start();
		    
		    System.err.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
		    System.err.println("[INFO] Server running at https://localhost:" + HTTPS_PORT + CONTEXT);
		    System.err.println("[HINT] Hit Enter to reload the application " + CONTEXT);
		    
			server.join();
			
		    // 等待用户输入回车重载应用.
 			while (true) {
 				char c = (char) System.in.read();
 				if (c == '\n') {
 					JettyFactory.reloadContext(server);
 				}
 			}
 			
		} catch (Exception e) {
		    e.printStackTrace();
			System.exit(-1);
		}
	}

}