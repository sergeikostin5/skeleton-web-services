package org.example;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * Created by sergeikostin on 3/5/16.
 */

@WebAppConfiguration // tell spring to create web application context instead of standard application context
public abstract class AbstractControllerTest extends AbstractTest  {

    protected MockMvc mvc; // class form Spring mock packages that simulates https interactions

    @Autowired //inject web application context to this class
    protected WebApplicationContext webApplicationContext;

    // make mvc attribute aware of all application components
    protected void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // endpoints receive and consume JSON data
    // Transform data between Java objects and JSON
    protected String mapToJson(Object obj) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    protected  <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

}
