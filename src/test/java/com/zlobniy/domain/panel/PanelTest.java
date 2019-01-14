//package com.zlobniy.domain.panel;
//
//import com.zlobniy.domain.panel.entity.BgrDataValue;
//import com.zlobniy.domain.panel.entity.Panel;
//import com.zlobniy.domain.panel.service.PanelService;
//import com.zlobniy.domain.panel.view.PanelInfoView;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.validation.annotation.Validated;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.List;
//
//@RunWith( SpringJUnit4ClassRunner.class )
//@Transactional
//@SpringBootTest
//@Ignore
//public class PanelTest {
//
//    @Autowired
//    private PanelService panelService;
//
//
//    @Test
//    public void createSimplePanelTest(){
//
//        Panel panel = new Panel();
//        panel.setTitle( "test panel" );
//
//        Panel newPanel = panelService.save( panel );
//
//        Assert.assertNotNull( "Panel not store", newPanel.getId() );
//
//    }
//
//    @Test
//    public void findAllPanelsTest(){
//
//        List<Panel> panels = panelService.findAll();
//
//        Assert.assertTrue( "find all not working", panels.size() > 0 );
//    }
//
//    @Test
//    public void findByClientId(){
//
//        Long clientId = 1L;
//
//        List<Panel> panels = panelService.findByClientId( clientId );
//
//        Assert.assertTrue( "find by client id not working", panels.size() > 0 );
//
//    }
//
//    @Test
//    public void findByFolderId(){
//
//        Long folderId = 2L;
//
//        List<Panel> panels = panelService.findByFolderId( folderId );
//
//        Assert.assertTrue( "find by client id not working", panels.size() > 0 );
//
//    }
//
//    @Test
//    public void checkBgrDataContainsBgrDescriptionId(){
//
//        Long folderId = 2L;
//
//        List<Panel> panels = panelService.findByFolderId( folderId );
//
//        Assert.assertTrue( "find by client id not working", panels.size() > 0 );
//
//        Panel panel = panelService.findFull( panels.get( 0 ).getId() );
//
//        List<BgrDataValue> dataValues = panel.getData().get( 0 ).getValues();
//
//        Assert.assertTrue( "background data not found", dataValues.size() > 0 );
//        Assert.assertNotNull( "background data don't have description id", dataValues.get( 0 ).getBgrDescription() );
//
//
//    }
//
//    @Test
//    public void getPanelInfoViewTest(){
//
//        Panel panel = new Panel();
//        panel.setCreationDate( new Date(  ) );
//        panel.setId( 1L );
//        panel.setTitle( "test panel 1" );
//
//        PanelInfoView panelInfoView = new PanelInfoView( panel );
//
//        Assert.assertNotNull( "Panel view mapping error", panelInfoView );
//        Assert.assertEquals( "View type is wrong", "Panel", panelInfoView.getType() );
//    }
//
//
//
//}
