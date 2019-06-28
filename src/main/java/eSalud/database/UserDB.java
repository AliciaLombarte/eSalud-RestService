package eSalud.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import eSalud.domains.Cuestionario;
import eSalud.domains.Doctor;
import eSalud.domains.ListCuestionario;
import eSalud.domains.ListProtocolo;
import eSalud.domains.Password;
import eSalud.domains.Protocolo;
import eSalud.domains.QuestionnaireResponse;
import eSalud.domains.User;
import eSalud.domains.WoundTracks;

public class UserDB {

	int size = 0;
	String nombre = null;
	String result = "notOk";

	@SuppressWarnings("finally")
	public String insertTrack(Connection conn, WoundTracks track, String fecha) {
		result = "notOk";
		try {
			int dolorP = Integer.parseInt(track.getDolorPierna());
			int dolorE = Integer.parseInt(track.getDolorEspalda());
			double temperatura = Double.parseDouble(track.getTemperatura());
			temperatura = temperatura / 10;
			int lastid = 0;
			int score = 0;
			Class.forName(InformacionProperties.getStrClassDriver());

			Statement myStatement = conn.createStatement();

			myStatement.executeUpdate("SET NAMES UTF8");

			myStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");

			ResultSet rs = myStatement.executeQuery("SELECT img_id FROM IMAGES ORDER BY img_id DESC LIMIT 1;");
			if (rs.next()) {
				lastid = Integer.parseInt(rs.getString(1));
			}
			rs = myStatement.executeQuery("SELECT score FROM QUESTIONNAIRE_RESPONSE WHERE date='" + fecha
					+ "' AND  emailUser=AES_ENCRYPT('" + track.getEmailUser()
					+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AND questionnaire='Oswestry';");

			if (rs.next()) {
				score = (int) rs.getObject("score");
			}
			myStatement.executeUpdate(
					"INSERT INTO WOUNDTRACKS (fecha,dolorPierna,dolorEspalda,temperatura,emailUser,img,resultado) values ('"
							+ fecha + "','" + dolorP + "','" + dolorE + "','" + temperatura + "',AES_ENCRYPT('"
							+ track.getEmailUser() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + lastid + "','"
							+ score + "');");
			result = "ok";
			myStatement.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error: " + e);
			result = "ClassNotFoundException";

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
			result = "SQLWarning";
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
			result = "SQLException";

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			result = "Exception";
			throw e;
		} catch (Error e) {
			System.out.println("Error: " + e.getMessage());
			result = "Error";
			throw e;
		} finally {
			return result;
		}
	}

	public String insert(Connection conn, WoundTracks track) {
		result = "notOk";
		try {
			String image = track.getPhoto();
			String fileName = track.getFileName();

			Class.forName(InformacionProperties.getStrClassDriver());

			Statement myStatement = conn.createStatement();

			myStatement.executeUpdate("SET NAMES UTF8");

			myStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");

			myStatement
					.executeUpdate("INSERT INTO IMAGES (img, file_name) values ('" + image + "','" + fileName + "');");

			result = "ok";
			myStatement.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			result = e.toString();
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
			result = "sqlWarning";
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
			result = "sqlException";
		} catch (Exception e) {
			System.out.println("Exceptionw: " + e);
			result = "Exception";
			throw e;
		} catch (Error e) {
			System.out.println("Error " + e.getMessage());
			result = "Error";
			throw e;
		}
		return result;
	}

	@SuppressWarnings("finally")
	public String checkUserDB(User user) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost:3306/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&allowPublicKeyRetrieval=true&useSSL=false";

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery(
					"SELECT COUNT(*) AS total, CAST(AES_DECRYPT(pass,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as pass FROM test_tfg.USERS WHERE email=AES_ENCRYPT('"
							+ user.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			String pass = null;

			if (rs != null) {
				while (rs.next()) {
					size = rs.getInt("total");
					pass = rs.getObject("pass").toString();
				}

			}
			if (size == 1) {
				if (user.getPass().equals(pass)) {
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

			ResultSet rs = myStatement.executeQuery(
					"SELECT COUNT(*) AS total, pass FROM test_tfg.DOCTORS WHERE email='" + doctor.getEmail() + "';");

			String pass = null;

			if (rs != null) {
				while (rs.next()) {
					size = rs.getInt("total");
					pass = rs.getObject("pass").toString();
				}

			}
			if (size == 1) {
				if (doctor.getPass().equals(pass)) {
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

			ResultSet rs = myStatement.executeQuery(
					"SELECT CAST(AES_DECRYPT(nombre,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as nombre, apellido1,apellido2,edad,username,CAST(AES_DECRYPT(email,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as email FROM test_tfg.USERS WHERE doctorEmail='"
							+ doctor.getEmail() + "';");

			if (rs != null) {
				while (rs.next()) {
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
			 * EncryptData ed = new EncryptData(); ed.initEncryption();
			 * user.setNombre(ed.encryptMyData(user.getNombre()));
			 */

			myStatement.executeUpdate("SET NAMES UTF8");

			myStatement.executeUpdate(
					"INSERT INTO USERS (nombre,apellido1,apellido2,edad,username,email,pass,doctorEmail) values (AES_ENCRYPT('"
							+ user.getNombre() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + user.getApellido1()
							+ "','" + user.getApellido2() + "','" + user.getEdad() + "','" + user.getUsername()
							+ "',AES_ENCRYPT('" + user.getEmail()
							+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),AES_ENCRYPT('" + user.getPass()
							+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'" + user.getDoctor() + "');");
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
	public ArrayList<WoundTracks> infoPaciente(String email) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		ArrayList<WoundTracks> list = new ArrayList<WoundTracks>();

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery("SELECT * FROM test_tfg.WOUNDTRACKS WHERE emailUser=AES_ENCRYPT('"
					+ email + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");

			if (rs != null) {
				while (rs.next()) {
					WoundTracks wounds = new WoundTracks();
					wounds.setEmailUser(rs.getString("emailUser"));
					wounds.setDolorPierna(rs.getString("dolorPierna"));
					wounds.setDolorEspalda(rs.getString("dolorEspalda"));
					wounds.setTemperatura(rs.getString("temperatura"));
					wounds.setFecha(rs.getDate("fecha"));
					wounds.setScore(rs.getInt("resultado"));
					wounds.setPhoto(rs.getString("img"));
					list.add(wounds);
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
	public String findNameByEmail(String email) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		String nombre = null;

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery(
					"SELECT CAST(AES_DECRYPT(nombre,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) AS CHAR(50)) as nombre, apellido1, apellido2 FROM test_tfg.USERS WHERE email=AES_ENCRYPT('"
							+ email + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");

			if (rs != null) {
				while (rs.next()) {
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

			myStatement.executeUpdate("UPDATE test_tfg.USERS SET pass=AES_ENCRYPT('" + pass.getNewPass()
					+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')) WHERE pass=AES_ENCRYPT(''" + pass.getOriginalPass()
					+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");

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
	public ArrayList<Map<String, String>> getPreguntas() {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<Map<String, String>> preguntas = new ArrayList<Map<String, String>>();

		try {

			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery("SELECT * FROM test_tfg.QUESTIONS;");

			if (rs != null) {
				while (rs.next()) {

					Map<String, String> pregunta = new HashMap<String, String>();

					for (int j = 1; j < 7; j++) {
						pregunta.put(String.valueOf(j), rs.getString("respuesta" + (j - 1)));
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

			if (rs != null) {
				while (rs.next()) {
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

			ResultSet rs = myStatement.executeQuery("SELECT protocolo, frecuencia FROM test_tfg.PROTOCOLS;");

			ArrayList<Protocolo> pL = new ArrayList<Protocolo>();

			if (rs != null) {
				while (rs.next()) {
					Protocolo p = new Protocolo();
					p.setNombre(rs.getString("protocolo"));
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

			myStatement.executeUpdate("INSERT INTO test_tfg.PROTOCOLS (protocolo,cuestionario,frecuencia) values ('"
					+ protocolo.getNombre() + "','" + protocolo.getCuestionario() + "','" + protocolo.getFrecuencia()
					+ "');");
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
	public ArrayList<Map<String, String>> getPreguntas(String cuestionayName) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<Map<String, String>> preguntas = new ArrayList<Map<String, String>>();
		try {

			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement
					.executeQuery("SELECT * FROM test_tfg.QUESTIONS WHERE nombre= '" + cuestionayName + "';");
			ResultSetMetaData rsmd = rs.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			if (rs != null) {

				while (rs.next()) {
					Map<String, String> pregunta = new HashMap<String, String>();
					pregunta.put("pregunta", rs.getString("pregunta"));
					for (int i = 0; i < columnsNumber - 3; i++) {
						pregunta.put(String.valueOf(i), rs.getString("respuesta" + (i)));
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
	public String getIfTrack(User user) {
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

			ResultSet rs = myStatement.executeQuery("SELECT COUNT(*) FROM test_tfg.WOUNDTRACKS WHERE fecha='"
					+ dateFormat.format(fecha) + "' AND emailUser=AES_ENCRYPT('" + user.getEmail()
					+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");

			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("COUNT(*)").equals("0")) {
						result = "doWountrack";
					} else {
						result = "doneWountrack";
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

	public String insertSeguimientoHerida(WoundTracks track, String fecha) {
		Connection conn = null;
		try {
			String userName = InformacionProperties.getStrUser();
			String password = InformacionProperties.getStrPassword();
			String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
					+ "&password=" + password + "&useSSL=false";
			Class.forName(InformacionProperties.getStrClassDriver());
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			try (Statement stmt = conn.createStatement()) {
				String imageResult = insert(conn, track);
				String wountrackResult = insertTrack(conn, track, fecha);
				if (wountrackResult.equals("ok") && imageResult.equals("ok")) {
					conn.commit();
				} else {
					conn.rollback();
					System.out.println("JDBC Transaction rolled back successfully");
				}
			} catch (Exception e) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// something goes wrong, rollback the transaction
				conn.rollback();
				System.out.println("JDBC Transaction rolled back successfully");
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback" + e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("finally")
	public ArrayList<String> get(String table, String email, String atribute) {

		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<String> protocoloList = new ArrayList<String>();

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = new Date();
			dateFormat.format(fecha);

			ResultSet rs = myStatement.executeQuery("SELECT " + atribute + " FROM test_tfg." + table
					+ " WHERE emailUser=AES_ENCRYPT('" + email + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");

			if (rs != null) {
				while (rs.next()) {
					protocoloList.add(rs.getString("nombre"));
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
			return protocoloList;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<String> getQUESTIONNAIRES(String table, String protocolo, String atribute,
			ArrayList<String> questionnairesList, int frecuencia, String email) {

		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = new Date();
			dateFormat.format(fecha);

			ResultSet rs = myStatement.executeQuery(
					"SELECT " + atribute + " FROM test_tfg." + table + " WHERE protocol='" + protocolo + "';");

			if (rs != null) {
				while (rs.next()) {

					if (!questionnairesList.contains(rs.getString(atribute))) {
						Date lastDoneDate = getLastDoneDate("QUESTIONNAIRE_RESPONSE", "date", email,
								rs.getString(atribute));
						toBeDone(fecha.getTime(), lastDoneDate.getTime(), frecuencia);
						if (toBeDone(fecha.getTime(), lastDoneDate.getTime(), frecuencia)) {
							questionnairesList.add(rs.getString(atribute));
						}
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
			return questionnairesList;
		}
	}

	private boolean toBeDone(long fechaHoy, long fechaUltimaRealizacion, int frecuencia) {
		try {
			long diffTime = fechaHoy - fechaUltimaRealizacion;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			return diffDays >= frecuencia;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;

	}

	private Date getLastDoneDate(String table, String atribute, String email, String questionnaire) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		Date date = new Date(0);
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery("SELECT " + atribute + " FROM test_tfg." + table
					+ " WHERE questionnaire='" + questionnaire + "' AND emailUser=AES_ENCRYPT('" + email
					+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')) ORDER BY '" + atribute + "' DESC;");

			if (rs != null) {
				while (rs.next()) {
					date = rs.getDate(atribute);
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
			return date;
		}
	}

	public String insertQuestionnaireResult(QuestionnaireResponse response, String date) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		Connection conn = null;
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);

			try (Statement stmt = conn.createStatement()) {
				stmt.executeUpdate("SET NAMES UTF8");

				stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");

				stmt.executeUpdate(
						"INSERT INTO QUESTIONNAIRE_RESPONSE (emailUser, questionnaire, date, score, result) values (AES_ENCRYPT('"
								+ response.getEmail() + "',UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'"
								+ response.getQuestionnaire() + "','" + date + "','" + response.getScore() + "','"
								+ Arrays.toString(response.getResult()) + "');");

				result = "ok";
				if (response.getQuestionnaire().equals("Oswestry")) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date fecha = new Date();
					dateFormat.format(fecha);
					stmt.executeUpdate("UPDATE WOUNDTRACKS SET resultado = '" + response.getScore() + "' WHERE fecha='"
							+ dateFormat.format(fecha) + "' AND emailUser=AES_ENCRYPT('" + response.getEmail()
							+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
				}
				conn.commit();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("error " + e.getMessage());

				conn.rollback();
				System.out.println("JDBC Transaction rolled back successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// something goes wrong, rollback the transaction
				conn.rollback();
				System.out.println("JDBC Transaction rolled back successfully " + e.getMessage());
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback" + e1.getMessage());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("finally")
	public ArrayList<String> getQuestionnaireDone(String email) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		ArrayList<String> questionnaireDoneList = new ArrayList<String>();
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = new Date();
			dateFormat.format(fecha);

			ResultSet rs = myStatement
					.executeQuery("SELECT questionnaire FROM test_tfg.QUESTIONNAIRE_RESPONSE  WHERE date='"
							+ dateFormat.format(fecha) + "' AND emailUser=AES_ENCRYPT('" + email
							+ "',UNHEX('F3229A0B371ED2D9441B830D21A390C3'));");
			if (rs != null) {
				while (rs.next()) {
					questionnaireDoneList.add(rs.getString("questionnaire"));
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
			return questionnaireDoneList;
		}
	}


	public String getImage(String img) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";

		String image = null;
		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery("SELECT img FROM test_tfg.IMAGES  WHERE img_id='" + img + "';");
			if (rs != null) {
				while (rs.next()) {
					image = rs.getString("img");
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
			return image;
		}
	}

	@SuppressWarnings("finally")
	public int getFrecuenciaProtocolo(String table, String protocolo, String atribute) {
		String userName = InformacionProperties.getStrUser();
		String password = InformacionProperties.getStrPassword();
		String url = "jdbc:mysql://localhost/" + InformacionProperties.getStrDatabaseName() + "?user=" + userName
				+ "&password=" + password + "&useSSL=false";
		int frecuencia = 0;

		try {
			Class.forName(InformacionProperties.getStrClassDriver());

			Connection conexion = DriverManager.getConnection(url);

			Statement myStatement = conexion.createStatement();

			ResultSet rs = myStatement.executeQuery(
					"SELECT " + atribute + " FROM test_tfg." + table + " WHERE protocolo='" + protocolo + "';");

			if (rs != null) {
				while (rs.next()) {
					frecuencia = rs.getInt(atribute);
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
			return frecuencia;
		}
	}

}
