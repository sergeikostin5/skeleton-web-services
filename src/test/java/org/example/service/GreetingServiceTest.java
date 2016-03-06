package org.example.service;

import org.example.AbstractTest;
import org.example.model.Greeting;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by sergeikostin on 3/5/16.
 */
@Transactional // any distructive database operation that they perform within test method will be rolled back when method exits
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService service;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp(){
        service.evictCache();
    }

    @After
    public void teardDown(){
        //clean up after each test method
    }


    //One unit test class per Application class
    @Test
    public void testFindAll(){

        Collection<Greeting> list = service.findAll();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
    }

    @Test
    public void testFindOne(){

        Long id = new Long(1);

        Greeting entity = service.findOne(id);
        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute mach", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound(){

        Long id = Long.MAX_VALUE;
        Greeting entity = service.findOne(id);
        Assert.assertNull("failure - expected null", entity);

    }

    @Test
    public void testCreate(){

        Greeting entity = new Greeting();
        entity.setText("test");

        Greeting createEntity = service.create(entity);

        Assert.assertNotNull("failure - expected not null", createEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createEntity.getId());

    }

    @Test @Ignore
    public void testCreateWithId(){
        Exception e = null;

        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try{
            service.create(entity);

        }catch (EntityExistsException eee){
            e = eee;
        }

        Assert.assertNotNull("failure - expected exception", e);
        Assert.assertTrue("failure - expected EntityExistException", e instanceof EntityExistsException);
    }

    @Test
    public void testUpdate(){

        Long id = new Long(1);
        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = entity.getText() + "test";
        entity.setText(updatedText);
        Greeting updatedEntity = service.update(entity);

        Assert.assertNotNull("failure - expected updated entity not null", updatedEntity);
        Assert.assertEquals("failure - expected updated entity attribute id unchanged ", id, updatedEntity.getId());
        Assert.assertEquals("failure - expected updated entity text attribute match ", updatedText, updatedEntity.getText());
    }

    @Test
    public void testUpdateNotFound(){

        Exception e = null;

        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try{
            service.update(entity);
        }catch (NoResultException nre){
            e = nre;
        }

        Assert.assertNotNull("failure - expected exception", e);
        Assert.assertTrue("failure - expected NoResultException", e instanceof NoResultException);
    }

    @Test
    public void testDelete(){

        Long id = new Long(1);

        Greeting entity = service.findOne(id);
        Assert.assertNotNull("failure - expected not Null", entity);
        service.delete(id);
        Collection<Greeting> list = service.findAll();
        Assert.assertEquals("failure - expected size", 1, list.size());
        Greeting deletedEntity = service.findOne(id);
        Assert.assertNull("failure - expected entity to be deleted ", deletedEntity);

    }


}
