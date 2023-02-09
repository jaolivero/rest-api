package com.demo.springboot.restapi.Survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyResource {
    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        super();
        this.surveyService = surveyService;
    }

    // /surveys => surveys
    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys(){
        return surveyService.retrieveAllSurveys();
    }

   @RequestMapping("/surveys/{surveyid}")
    public Survey getSpecificSurvey(@PathVariable String surveyid) {

       Survey survey = surveyService.getSurveyById(surveyid);

       if(survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

       return survey;
    }

    @RequestMapping("/surveys/{surveyid}/questions")
    public List<Question> getAllSurveyQuestions(@PathVariable String surveyid) {

        List<Question> questions = surveyService.getAllSurveyQuestions(surveyid);

        if(questions == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return questions;
    }


    // localhost:8080/surveys/surveysid/questions/questions1
    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question getSpecificSurveyQuestions(@PathVariable String surveyId,@PathVariable String questionId) {

        Question question = surveyService.getSpecificSurveyQuestion(surveyId, questionId);

        if(question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return question;
    }


    @RequestMapping(value="/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {

        String questionId = surveyService.addNewSurveyQuestions(surveyId, question);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}").buildAndExpand(questionId).toUri();

        return ResponseEntity.created(location).build();

    }
















}
