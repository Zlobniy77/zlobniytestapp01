//package com.zlobniy.jmh.cases;
//
//import com.zlobniy.BoosterApplication;
//import com.zlobniy.config.ContextProvider;
//import com.zlobniy.domain.survey.dao.SurveyDao;
//import com.zlobniy.domain.survey.entity.Survey;
//import com.zlobniy.domain.survey.service.SurveyService;
//import com.zlobniy.domain.survey.view.SurveySettingsView;
//import com.zlobniy.domain.survey.view.SurveyView;
//import com.zlobniy.example.DummyQuestionnaire;
//import org.openjdk.jmh.annotations.*;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//
//@State( Scope.Benchmark )
//public class SurveysCase {
//
//    List<Survey> surveys;
//    private SpringApplication app;
//    private ConfigurableApplicationContext conf;
//
//    private SurveyService surveyService;
//
//    //@Setup
//    public void setup() throws Exception{
//
//        app = new SpringApplication(
//                BoosterApplication.class);
//        conf = app.run("--spring.jmx.enabled=false",
//                        "--spring.application.name=zlobniy");
//
//
//        //WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//        surveyService = new SurveyService( ContextProvider.getBean(SurveyDao.class) ) ;
//
//        this.surveys = new ArrayList<>(  );
//
//        for( int i = 0; i < 100 ; i++ ){
//
//            final DummyQuestionnaire dummyQuestionnaire = new DummyQuestionnaire();
//            final SurveyView surveyView = new SurveyView();
//            surveyView.setTitle( "Dummy survey " + i );
//            surveyView.setQuestionnaire( dummyQuestionnaire.createDummyQuestionnaire( 50 ) );
//            surveyView.setSurveySettings( new SurveySettingsView() );
//            surveyView.setCreationDate( new Date() );
//
//            final Survey survey = new Survey( surveyView );
//            surveys.add( survey );
//        }
//
//
//    }
//
//    //@TearDown
//    public void clean() {
//        this.conf.stop();
//        this.conf.close();
//    }
//
//    @Benchmark
//    @BenchmarkMode( Mode.AverageTime )
//    @OutputTimeUnit( TimeUnit.SECONDS )
//    public List<SurveyView> mappingSurveyToSurveyView() {
//
//        List<SurveyView> surveyViews = surveys.stream().map( SurveyView::new ).collect(Collectors.toList());
//
//        return surveyViews;
//    }
//
//    @Benchmark
//    @BenchmarkMode( Mode.AverageTime )
//    @OutputTimeUnit( TimeUnit.NANOSECONDS )
//    public List<Survey> getSurveysFromDataBase() {
//
//        List<Survey> surveyViews = surveyService.findSurveysByFolder( 2L );
//
//        return surveyViews;
//    }
//
//    @Benchmark
//    @BenchmarkMode( Mode.AverageTime )
//    @OutputTimeUnit( TimeUnit.SECONDS )
//    public List<SurveyView> getSurveysFromDataBaseAndMapping() {
//
//        List<SurveyView> surveyViews = surveyService.findSurveyViewsByFolder( 2L );
//
//        return surveyViews;
//    }
//
//}
