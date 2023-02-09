package com.demo.springboot.restapi.Survey;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

//buisness logic
@Service
public class SurveyService {
    private static List<Survey> surveys = new ArrayList<>();

    static {

        Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3", "Most Popular DevOps Tool",
                Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey getSurveyById(String surveyId) {

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equalsIgnoreCase(surveyId);
        // findFirst method returns an Optional back
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
        if(optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get();
    }

    public List <Question>getAllSurveyQuestions(String surveyid) {
        Survey survey = getSurveyById(surveyid);

        if (survey == null) return null;

        return survey.getQuestions();
    }

    public Question getSpecificSurveyQuestion(String surveyid, String questionId) {

        List<Question> surveyQuestions = getAllSurveyQuestions(surveyid);

        if(surveyQuestions==null) return null;

        Optional<Question> optionalQuestion = surveyQuestions.stream().filter
                (q -> q.getId().equalsIgnoreCase(questionId)).findFirst();

        if(optionalQuestion.isEmpty()) return null;

        return optionalQuestion.get();
    }


    public String addNewSurveyQuestions(String surveyId, Question question) {
        List<Question> questions = getAllSurveyQuestions(surveyId);

        question.setId(generateRandomId());
        questions.add(question);

        return question.getId();
    }

    public String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        String randomID = new BigInteger(32, secureRandom).toString();

        return randomID;
    }

    public String deleteSurveyQuestion(String surveyId, String questionId) {
        List<Question> surveyQuestions = getAllSurveyQuestions(surveyId);

        if(surveyQuestions == null)
            return null;

        Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
        //removeIf returns a boolean
        boolean removed = surveyQuestions.removeIf(predicate);

        if(!removed) return null;

        return questionId;
    }

    public String updateSurveyQuestion(String surveyId, String questionId, Question question) {
        List<Question> questions = getAllSurveyQuestions(surveyId);

        //removeIf returns a boolean
        boolean removed = questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
        questions.add(question);

        return questionId;
    }

}
