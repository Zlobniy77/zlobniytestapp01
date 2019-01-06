package com.zlobniy;

import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Random;

public class Test {

    @BeforeClass
    public static void initBefore(){

    }

    @Before
    public void init(){

    }

    @org.junit.Test
    public void testR(){
        //System.out.println("test");



    }

    private void exampl2(){
        final String test = "test";
        System.out.println( test.chars().reduce( (left, right) -> right + left ).orElseThrow( RuntimeException::new ) );

    }

    private void rand(){
        Random random = new Random(  );
        for ( int i = 0 ; i < 3 ; i++ ){
            System.out.print( random.ints(3,6,9).findFirst().getAsInt());
        }
    }

}
