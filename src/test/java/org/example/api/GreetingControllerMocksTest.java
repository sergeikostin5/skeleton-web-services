package org.example.api;

import org.junit.*;
import org.example.AbstractControllerTest;
import org.example.model.Greeting;
import org.example.service.EmailService;
import org.example.service.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sergeikostin on 3/6/16.
 */
@Transactional
public class GreetingControllerMocksTest extends AbstractControllerTest {

    @Mock // Instructs Mockito to analyze the class or interface and produce a test stub with the same public method signatures
    private EmailService emailService;

    @Mock
    private GreetingService greetingService;

    @InjectMocks // Tells mockito to inject mocked objects to Greeting Controller
    private GreetingController controller;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this); // prepares mock objects for test execution

        setUp(controller);
    }

    private Collection<Greeting>  getEntityListStubData(){
        Collection<Greeting> list = new ArrayList<Greeting>();
        list.add(getEntityStubData());
        return list;
    }

    private Greeting getEntityStubData(){
        Greeting entity = new Greeting();
        entity.setId(1L);
        entity.setText("hello");
        return entity;
    }

    @Test
    public void testGetGreetings() throws Exception{

        // Create some test data
        Collection<Greeting> list = getEntityListStubData();

        // Stub the GreetingService.findAll() method return value
        when(greetingService.findAll()).thenReturn(list);

        String uri = "/api/greetings";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        //Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(greetingService, times(1)).findAll();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    @Test
    public void testGetGreeting() throws Exception{

        // Create some test data
        Long id = new Long(1);
        Greeting greeting = getEntityStubData();

        // Stub the GreetingService.findOne() method return value
        when(greetingService.findOne(id)).thenReturn(greeting);

        // Preform the behavior being tested
        String uri = "/api/greeting/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        //Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(greetingService, times(1)).findOne(id);

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    @Test
    public void testGetGreetingNotFound() throws Exception{

        // Create some test data
        Long id = Long.MAX_VALUE;

        // Stub the GreetingService.findOne() method return value
        when(greetingService.findOne(id)).thenReturn(null);

        // Preform the behavior being tested
        String uri = "/api/greeting/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        //Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(greetingService, times(1)).findOne(id);

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
    }

    @Test
    public void testCreateGreeting() throws Exception{

        Greeting entity = getEntityStubData();

        // Stub the GreetingService.findOne() method return value
        when(greetingService.create(any(Greeting.class))).thenReturn(entity);

        // Preform the behavior being tested
        String uri = "/api/greeting";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).
                andReturn();

        //Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the GreetingService.findAll method was invoked once
        verify(greetingService, times(1)).create(any(Greeting.class));

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        Greeting createdEntity = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("Failure - expected greeting not null", createdEntity);
        Assert.assertNotNull("Failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("Failure - expected text attribute not null", entity.getText(), createdEntity.getText());
    }

    //TODO
    public void testUpdateGreeting() throws Exception{}

    //TODO
    public void testDeleteGreeting() throws Exception{}





}
