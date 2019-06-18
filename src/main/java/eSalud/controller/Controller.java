package eSalud.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eSalud.database.UserDB;
import eSalud.domains.Doctor;
import eSalud.domains.ListCuestionario;
import eSalud.domains.ListProtocolo;
import eSalud.domains.ListWounds;
import eSalud.domains.Password;
import eSalud.domains.Protocolo;
import eSalud.domains.QuestionnaireResponse;
import eSalud.domains.Result;
import eSalud.domains.User;
import eSalud.domains.WoundTracks;

@RestController
@CrossOrigin
public class Controller {
	@RequestMapping("/")
	public String home() {
		return "REST service application running!";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json")
	public Result logIn(@RequestBody User user) {

		UserDB database = new UserDB();
		String result = database.checkUserDB(user);

		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}
		return res;
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST, produces = "application/json")
	public Result logOut() {
		System.out.println("logout....");
		Result res = new Result();
		res.setResult(200);
		return res;
	}

	@RequestMapping(value = "changePass", method = RequestMethod.POST, produces = "application/json")
	public Result changePass(@RequestBody Password pass) {

		UserDB database = new UserDB();

		String result = database.changePass(pass);

		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}
		return res;
	}

	@RequestMapping(value = "loginDoctor", method = RequestMethod.POST, produces = "application/json")
	public Result logInDoctor(@RequestBody Doctor doctor) {

		UserDB database = new UserDB();

		String result = database.checkDoctorDB(doctor);

		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}
		return res;
	}

	@RequestMapping(value = "woundInfo", method = RequestMethod.POST, produces = "application/json")
	public Result getInfo(@RequestBody WoundTracks track) {

		UserDB database = new UserDB();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date fecha = new Date();
		Result res = new Result();

		try {
			String result = database.insertSeguimientoHerida(track, dateFormat.format(fecha));
			if (result.equals("ok")) {
				res.setResult(200);
			} else {
				res.setResult(400);
			}
			return res;

		} catch (Exception e) {
			System.out.println(e);
			res.setResult(400);

		}
		return res;

	}

	@RequestMapping(value = "ifTrack", method = RequestMethod.POST, produces = "application/json")
	public Result getIf(@RequestBody User user) {

		UserDB database = new UserDB();
		String result = database.getIfTrack(user);

		Result res = new Result();
		if (result.equals("doWountrack")) {
			res.setResult(200);
			res.setContent("doWountrack");
		} else if (result.equals("doneWountrack")) {
			res.setResult(200);
			res.setContent("doneWountrack");
		}
		return res;

	}

	@RequestMapping(value = "pacientes", method = RequestMethod.POST, produces = "application/json")
	public Result getPacientes(@RequestBody Doctor doctor) {

		UserDB database = new UserDB();
		ArrayList<User> listUsers = new ArrayList<User>();

		listUsers = database.getPacientes(doctor);

		Result res = new Result();
		res.setResult(200);
		res.setListUsers(listUsers);

		return res;

	}

	@RequestMapping(value = "cuestionarios", method = RequestMethod.GET, produces = "application/json")
	public ListCuestionario getCuestionarios() {

		UserDB database = new UserDB();
		ListCuestionario listCuestionarios = new ListCuestionario();

		listCuestionarios = database.getCuestionarios();

		return listCuestionarios;

	}

	@RequestMapping(value = "userQuestionnaire", method = RequestMethod.GET, produces = "application/json")
	public ArrayList<String> getUserProtocols(@RequestParam("email") String email) {
		UserDB database = new UserDB();
		ArrayList<String> protocoloList = new ArrayList<String>();
		ArrayList<String> questionnaireDoneList = new ArrayList<String>();
		protocoloList = database.get("PROTOCOLS_USERS", email, "nombre");
		ArrayList<String> questionnaireList = new ArrayList<String>();
		for (String protocolo : protocoloList) {
			int frecuencia = database.getFrecuenciaProtocolo("PROTOCOLS", protocolo, "frecuencia");
			questionnaireList = database.getQUESTIONNAIRES("PROTOCOLS_QUESTIONNAIRES", protocolo, "questionnaire",
					questionnaireList, frecuencia, email);
			System.out.println(protocolo + frecuencia + questionnaireList);
		}
		// questionnaireDoneList = database.getQuestionnaireDone(email);
		//if (!questionnaireDoneList.isEmpty()) {
		//	questionnaireList = database.getDifferenceBetweenTwoArray(questionnaireList, questionnaireDoneList);
		//}
		return questionnaireList;
	}

	@RequestMapping(value = "protocolos", method = RequestMethod.GET, produces = "application/json")
	public ListProtocolo getProtocolos() {

		UserDB database = new UserDB();
		ListProtocolo listProtocolos = new ListProtocolo();
		listProtocolos = database.getProtocolos();
		return listProtocolos;
	}

	@RequestMapping(value = "protocol", method = RequestMethod.POST, produces = "application/json")
	public Result postProtocolo(@RequestBody Protocolo protocolo) {

		UserDB database = new UserDB();

		String result = database.createProtocol(protocolo);

		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}
		return res;
	}

	@RequestMapping(value = "registroPaciente", method = RequestMethod.POST, produces = "application/json")
	public Result createPaciente(@RequestBody User user) {

		UserDB database = new UserDB();

		String result = database.createPaciente(user);

		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}
		return res;

	}

	@RequestMapping(value = "infoUser", method = RequestMethod.POST, produces = "application/json")
	public ListWounds infoPaciente(@RequestBody User user) {
		
		UserDB database = new UserDB();
		String email = user.getEmail();
		ArrayList<WoundTracks> list = database.infoPaciente(email);
		String nombre = database.findNameByEmail(email);
		
		ListWounds listwounds = new ListWounds();
		listwounds.setList(list);
		listwounds.setNombre(nombre);
		
		return listwounds;
		
	}

	@RequestMapping(value = "getWoundtrackImage", method = RequestMethod.GET, produces = "application/json")
	public String woundtrackImage(@RequestParam("img_id") String img) {

		UserDB database = new UserDB();
		String image = database.getImage(img);
		return image;
	}

	@RequestMapping(value = "questions", method = RequestMethod.GET, produces = "application/json")
	public ArrayList<Map<String, String>> getPreguntas(@RequestParam("protocolo") String protocolo) {

		UserDB database = new UserDB();
		ArrayList<Map<String, String>> preguntas = database.getPreguntas(protocolo);

		return preguntas;
	}

	@RequestMapping(value = "resultQuest", method = RequestMethod.POST, produces = "application/json")
	public Result postResultQuest(@RequestBody QuestionnaireResponse response) {

		UserDB database = new UserDB();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date fecha = new Date();
		dateFormat.format(fecha);
		String result = database.insertQuestionnaireResult(response, dateFormat.format(fecha));
		Result res = new Result();
		if (result.equals("ok")) {
			res.setResult(200);
		} else {
			res.setResult(400);
		}

		return res;
	}

}
