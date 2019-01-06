//package com.zlobniy.jmh;
//
//import org.openjdk.jmh.runner.RunnerException;
//
//public class JMHTest {
//
//    public static void main( String[] args ) throws RunnerException {
//
//        Options opt = new OptionsBuilder()
//                .include( SurveysCase.class.getSimpleName() )
//                .forks( 1 )
//                .warmupIterations( 5 )
//                .measurementIterations( 5 )
//                .resultFormat( ResultFormatType.TEXT )
//                .threads( 1 )
//                .build();
//
//        new Runner( opt ).run();
//    }
//
//}
