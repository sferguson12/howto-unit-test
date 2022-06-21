package com.liveperson.howto;

import java.time.Instant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.liveperson.howto.persons.contracts.PersonRequest;
import com.liveperson.howto.persons.contracts.RequestType;
import com.liveperson.howto.persons.controllers.PersonController;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main( String[] args )
    {
        PersonController controller = context.getBean(PersonController.class);
        PersonRequest personRequest = new PersonRequest(
            RequestType.ADD, null, "John", null, "Deaux", Instant.parse("2001-03-19T00:00:00-05:00"));
        controller.savePerson(personRequest);
    }
}
