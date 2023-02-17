package com.demo.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    //http://localhost:8080/surveys/Survey1/questions/Question1
    String str = """
			
			{
			  "id": "Question1",
			  "description": "Most Popular Cloud Platform Today",
			  "options": [
			    "AWS",
			    "Azure",
			    "Google Cloud",
			    "Oracle Cloud"
			  ],
			  "correctAnswer": "AWS"
			}
			
			""";


    //

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

    @Autowired
    private TestRestTemplate template;

    //
    //[Content-Type:"application/json",


    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {

        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "correctAnswer":"AWS"
                }
                """;
		//Status of Respoinse is it 200
		//[Content-Type: "application/json"], note that it is possible to get multiple headers
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));


        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

        //assertEquals(expectedResponse.trim(), responseEntity.getBody());
        //System.out.println();
        //System.out.println(responseEntity.getHeaders());
    }


}