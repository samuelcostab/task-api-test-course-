 package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void shouldBeReturnedTasks() {
		RestAssured
		.given()
			
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void shouldBeSaveTask() {
		RestAssured
		.given()
			.body("{\"task\":\"TestAPI\", \"dueDate\": \"2021-08-29\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void shouldNotSaveInvalidTask() {
		RestAssured
		.given()
			//invalide dueDate (past)
			.body("{\"task\":\"TestAPI\", \"dueDate\": \"2020-08-29\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
		;
	}
}
