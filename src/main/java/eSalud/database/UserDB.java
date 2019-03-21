package eSalud.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import eSalud.domains.Cuestionario;
import eSalud.domains.Doctor;
import eSalud.domains.ListCuestionario;
import eSalud.domains.ListProtocolo;
import eSalud.domains.Password;
import eSalud.domains.Protocolo;
import eSalud.domains.User;
import eSalud.domains.WoundTracks;


public class UserDB {
	
	int size = 0;
	String nombre = null;
	String result = "notOk";

	/*
	public void insertUserDB(User user){
		
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			Class.forName(InformacionProperties.getStrClassDriver());
			Connection conexion = DriverManager.getConnection(url);
			Statement myStatement = conexion.createStatement();
	    	
			myStatement.executeUpdate("SET NAMES UTF8");
			myStatement.executeUpdate("INSERT INTO USERS (username,pass) values ('" + user.getUsername() + "','" + user.getPass() + "');");
			myStatement.close();
			conexion.close();

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/

	@SuppressWarnings("finally")
	public String insertTrack(WoundTracks track, String string) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			
			int dolorP = Integer.parseInt(track.getDolorPierna());
			int dolorE = Integer.parseInt(track.getDolorEspalda());
			double temperatura = Double.parseDouble(track.getTemperatura());
			temperatura = temperatura/10;
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			myStatement.executeUpdate("SET NAMES UTF8");
			
			myStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
		
			//myStatement.executeUpdate("INSERT INTO IMAGES (img_id,img,file_name) values ('" + track.getPhoto() + "','" + x + "','" + y + "');");
			
			myStatement.executeUpdate("INSERT INTO WOUNDTRACKS (fecha,dolorPierna,dolorEspalda,temperatura,emailUser,img,resultado) values ('" + string + "','" + dolorP + "','" + dolorE + "','" + temperatura + 
					"',AES_ENCRYPT('" + track.getEmailUser() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + 1 + "','" + "No realizado" + "');");
					//+ track.getPhoto() + "');");
			
			result = "ok";
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public String updateTrack(WoundTracks track) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			myStatement.executeUpdate("SET NAMES UTF8");
			
			myStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
		
			//myStatement.executeUpdate("INSERT INTO IMAGES (img_id,img,file_name) values ('" + track.getPhoto() + "','" + x + "','" + y + "');");
			
			myStatement.executeUpdate("UPDATE WOUNDTRACKS SET resultado='" + track.getScore() + "' WHERE emailUser=AES_ENCRYPT('" + track.getEmailUser() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
					//+ track.getPhoto() + "');");
			
			result = "ok";
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public String checkUserDB(User user) {
		System.out.print("------------"+user);
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost:3306/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&allowPublicKeyRetrieval=true&useSSL=false";
		System.out.println("------------"+url);

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT COUNT(*) AS total, CAST(AES_DECRYPT(pass,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as pass FROM test_tfg.USERS WHERE email=AES_ENCRYPT('" + user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			String pass = null;
			
			if (rs != null)   
			{  
				while(rs.next()){
					size = rs.getInt("total");	
					pass = rs.getObject("pass").toString();
				}
				
			}
			if (size == 1) {  
				if(user.getPass().equals(pass)){
					result = "ok";
				}
			}
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	public String checkDoctorDB(Doctor doctor) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);
			
			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT COUNT(*) AS total, pass FROM test_tfg.DOCTORS WHERE email='" + doctor.getEmail() + "';");
			
			String pass = null;
			
			if (rs != null)   
			{  
				while(rs.next()){
					size = rs.getInt("total");	
					pass = rs.getObject("pass").toString();
				}
				
			}
			if (size == 1) {  
				if(doctor.getPass().equals(pass)){
					result = "ok";
				}
			}
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<User> getPacientes(Doctor doctor) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<User> listUsers = new ArrayList<User>();
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			ResultSet rs = myStatement.executeQuery("SELECT CAST(AES_DECRYPT(nombre,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as nombre, apellido1,apellido2,edad,username,CAST(AES_DECRYPT(email,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as email FROM test_tfg.USERS WHERE doctorEmail='" + doctor.getEmail() + "';");
			
			if (rs != null)   
			{  
				while(rs.next()){
					User user = new User();
					user.setNombre(rs.getString("nombre"));
					user.setApellido1(rs.getString("apellido1"));
					user.setApellido2(rs.getString("apellido2"));
					user.setEdad(Integer.parseInt(rs.getString("edad")));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					listUsers.add(user);
				}
				
			}
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return listUsers;
		}
	}

	@SuppressWarnings("finally")
	public String createPaciente(User user) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			/*
			EncryptData ed = new EncryptData();
			ed.initEncryption();
			user.setNombre(ed.encryptMyData(user.getNombre()));*/
			
			myStatement.executeUpdate("SET NAMES UTF8");
		
			myStatement.executeUpdate("INSERT INTO USERS (nombre,apellido1,apellido2,edad,username,email,pass,doctorEmail) values (AES_ENCRYPT('" + user.getNombre() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + user.getApellido1() + "','" + user.getApellido2() + 
					"','" + user.getEdad() + "','" + user.getUsername() + "',AES_ENCRYPT('" + user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),AES_ENCRYPT('" + user.getPass() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + user.getDoctor() + "');");
			result = "ok";
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<WoundTracks> infoPaciente(User user) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<WoundTracks> list = new ArrayList<WoundTracks>();
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT dolorPierna, dolorEspalda, temperatura, fecha, resultado FROM test_tfg.WOUNDTRACKS WHERE emailUser=AES_ENCRYPT('" + user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			
			if (rs != null)   
			{  
				while(rs.next()){
					WoundTracks wound = new WoundTracks();
					wound.setEmailUser(user.getEmail());
					wound.setDolorPierna(rs.getString("dolorPierna"));
					wound.setDolorEspalda(rs.getString("dolorEspalda"));
					wound.setTemperatura(rs.getString("temperatura"));
					wound.setFecha(rs.getDate("fecha"));
					wound.setScore(rs.getString("resultado"));
					list.add(wound);
				}
				
			}
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return list;
		}
	}

	@SuppressWarnings("finally")
	public String findNameByEmail(User user) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		
		String nombre = null;
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT CAST(AES_DECRYPT(nombre,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as nombre, apellido1, apellido2 FROM test_tfg.USERS WHERE email=AES_ENCRYPT('" + user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			
			if (rs != null)   
			{  
				while(rs.next()){
					nombre = rs.getString("nombre") + " " + rs.getString("apellido1") + " " + rs.getString("apellido2");
				}
				
			}
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return nombre;
		}
	}

	@SuppressWarnings("finally")
	public String changePass(Password pass) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			myStatement.executeUpdate("UPDATE test_tfg.USERS SET pass=AES_ENCRYPT('" + pass.getNewPass() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')) WHERE pass=AES_ENCRYPT(''" + pass.getOriginalPass() +"',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			
			result = "ok";
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings({ "finally", "null" })
	public ArrayList<Map<String,String>> getPreguntas() {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		
		ArrayList<Map<String,String>> preguntas = new ArrayList<Map<String,String>>();
		
		try {
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT * FROM test_tfg.QUESTIONS;");

			if (rs != null)   
			{  
				while(rs.next()){
					
					Map<String,String> pregunta = new HashMap<String,String>();
					
					for(int j=1; j<7; j++){
						pregunta.put(String.valueOf(j), rs.getString("respuesta" + (j-1)));
					}
					
					preguntas.add(pregunta);
				}
				
			}
			
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return preguntas;
		}
	}

	@SuppressWarnings("finally")
	public ListCuestionario getCuestionarios() {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ListCuestionario listCuestionarios = new ListCuestionario();
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT nombre FROM test_tfg.QUESTIONNAIRES;");
			
			ArrayList<Cuestionario> cL = new ArrayList<Cuestionario>();
			
			if (rs != null)   
			{  
				while(rs.next()){
					Cuestionario c = new Cuestionario();
					c.setNombre(rs.getString("nombre"));
					cL.add(c);
				}
				
			}
			
			listCuestionarios.setCuestionarios(cL);
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return listCuestionarios;
		}
	}
	
	@SuppressWarnings("finally")
	public ListProtocolo getProtocolos() {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ListProtocolo listProtocolos = new ListProtocolo();
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
		
			ResultSet rs = myStatement.executeQuery("SELECT protocolo, cuestionario, frecuencia FROM test_tfg.PROTOCOLS;");
			
			ArrayList<Protocolo> pL = new ArrayList<Protocolo>();
			
			if (rs != null)   
			{  
				while(rs.next()){
					Protocolo p = new Protocolo();
					p.setNombre(rs.getString("protocolo"));
					p.setCuestionario(rs.getString("cuestionario"));
					p.setFrecuencia(rs.getInt("frecuencia"));
					pL.add(p);
				}
				
			}
			
			listProtocolos.setProtocolo(pL);
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return listProtocolos;
		}
	}

	@SuppressWarnings("finally")
	public String createProtocol(Protocolo protocolo) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			myStatement.executeUpdate("SET NAMES UTF8");
			
			myStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
		
			myStatement.executeUpdate("INSERT INTO test_tfg.PROTOCOLS (protocolo,cuestionario,frecuencia) values ('" + protocolo.getNombre() +
					"','" + protocolo.getCuestionario() + "','" + protocolo.getFrecuencia() + "');");
			result = "ok";
			myStatement.close();
			conexion.close();
	

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public String  getIfTrack(User user) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		result = "notOk";
		
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = new Date();
			dateFormat.format(fecha);
		
			ResultSet rs = myStatement.executeQuery("SELECT resultado FROM test_tfg.WOUNDTRACKS WHERE fecha='" + dateFormat.format(fecha) + "' AND emailUser=AES_ENCRYPT('" + user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			
			
					
			if (rs != null)   
			{  
				while(rs.next())
				{
					if (rs.getString("resultado").equals("No realizado")){
						result = "ok";
					} else {
						result = "okQ";
					}
				}
			}
			
			myStatement.close();
			conexion.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} finally {
			return result;
		}
	}
	
}
