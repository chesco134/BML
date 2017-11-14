package org.inspira.jcapiz.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private Connection connection;
	private String url;
	private String user;
	private String password;

	public String getUrl() {
		return url;
	}

	public DatabaseConnection() throws IOException {
		/*
		String host = "";
		this.user = "";//"amstrong";
		this.password = "";//"!\"#$%wszae123QWERT";
		Scanner scan = new Scanner(new FileInputStream(new File("credenciales.txt")));
		host = scan.nextLine();
		this.user = scan.nextLine();
		this.password = scan.nextLine();
		scan.close();
		url = "jdbc:mysql://"+host+"/Demanda_Unidades_Aprendizaje?useUnicode=true&characterEncoding=utf-8";
		*/
		connection = null;
	}

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// connection = DriverManager.getConnection(url, user, password);
			connection = DriverManager.getConnection("jdbc:mysql://localhost/Modismos", "jcapiz", "sharPedo319");
		} catch (SQLException e) {
			connection = null;
			url = e.toString();
			e.printStackTrace();
		} catch (InstantiationException e) {
			url = e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			url = e.toString();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			url = e.toString();
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}