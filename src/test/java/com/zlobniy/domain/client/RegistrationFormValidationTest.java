package com.zlobniy.domain.client;

//@RunWith( SpringJUnit4ClassRunner.class )
//@Transactional
//@SpringBootTest
//public class RegistrationFormValidationTest {
//
//    @Autowired
//    private Validator validator;
//
//    @Test
//    public void validateFormTest(){
//
//        RegistrationView view = new RegistrationView();
//        view.setUsername( "as" );
//        view.setEmail( "mail" );
//
//        Map<String, String> errorMap = new HashMap<>(  );
//
//        Set<ConstraintViolation<RegistrationView>> validate = validator.validate( view );
//        Assert.assertTrue( "Validation should be fail here", validate.size() >= 3 );
//
//        Iterator iter = validate.iterator();
//        while ( iter.hasNext() ){
//            ConstraintViolation next = (ConstraintViolation) iter.next();
//            errorMap.put( next.getPropertyPath().toString(), next.getMessage() );
//            System.out.println("next " + next.getPropertyPath() );
//
//        }
//
//        validate.stream().map( ConstraintViolation::getMessage ).forEach( System.out::println );
//
////        validate.stream().collect( Collectors.toMap(  ) )
//
//    }
//
//}
