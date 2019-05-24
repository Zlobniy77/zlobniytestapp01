package com.zlobniy.domain.client;

import com.zlobniy.domain.client.view.RegistrationView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class RegistrationFormValidationTest {

    @Autowired
    private Validator validator;

    @Test
    public void validateFormTest(){

        RegistrationView view = new RegistrationView();
        view.setUsername( "as" );
        view.setEmail( "mail" );

        Map<String, String> errorMap = new HashMap<>(  );

        Set<ConstraintViolation<RegistrationView>> validate = validator.validate( view );
        Assert.assertTrue( "Validation should be fail here", validate.size() >= 3 );

        Iterator iter = validate.iterator();
        while ( iter.hasNext() ){
            ConstraintViolation next = (ConstraintViolation) iter.next();
            errorMap.put( next.getPropertyPath().toString(), next.getMessage() );
            System.out.println("next " + next.getPropertyPath() );

        }

        validate.stream().map( ConstraintViolation::getMessage ).forEach( System.out::println );

//        validate.stream().collect( Collectors.toMap(  ) )

    }

}
