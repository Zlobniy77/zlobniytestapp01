package com.zlobniy.domain.folder;

import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.folder.view.FolderView;
import com.zlobniy.domain.survey.entity.Survey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Integration tests with database.
 * */
@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class FolderITest {

    @Autowired
    private FolderService service;

    @Test
    public void loadAllFoldersByClient(){

        List<Folder> folders = service.findByClientId( 1L );
        Assert.assertTrue( "load folders for default user", folders.size() == 1 );

    }

    @Test
    public void loadSurveysFromFolder() {

        List<Folder> folders = service.findByClientId( 1L );
        Assert.assertTrue( "load folders for default user", folders.size() == 1 );

        Folder folder = folders.get( 0 );
        List<Survey> surveys = folder.getSurveys();

        Assert.assertTrue( "load folders for default user", surveys.size() > 1 );

    }

    @Test
    public void loadFoldersAndMappingToView(){

        List<Folder> folders = service.findByClientId( 1L );
        List<FolderView> folderViews = folders.stream().map( FolderView::new ).collect( Collectors.toList());


        Assert.assertTrue( "mapping folder to folder view", folderViews.size() == 1 );

    }

    @Test
    public void mapRootFolderViewToFolder(){

        FolderView folderView = getView();

        Folder folder = new Folder( folderView );

        Assert.assertTrue( Objects.equals( folderView.getId(), folder.getId() ) );
        Assert.assertEquals( folderView.getTitle(), folder.getTitle() );
        Assert.assertEquals( folderView.isExpanded(), folder.isExpanded() );
        Assert.assertEquals( folderView.isSelected(), folder.isSelected() );

    }

    @Test
    public void mapRootFolderToFolderView(){

        Folder folder1 = getFolder();

        FolderView folder = new FolderView( folder1 );

        Assert.assertTrue( Objects.equals( folder1.getId(), folder.getId() ) );
        Assert.assertEquals( folder1.getTitle(), folder.getTitle() );
        Assert.assertEquals( folder1.isExpanded(), folder.isExpanded() );
        Assert.assertEquals( folder1.isSelected(), folder.isSelected() );

    }

    @Test
    public void mapFromViewToFolderWithChildren(){

        FolderView folderView = getView();
        List<FolderView> childs = getChildrenView();
        folderView.setChildren( childs );

        FolderView childView = childs.get( 0 );

        Folder folder = new Folder( folderView );

        Assert.assertTrue( folder.getChildren().size() > 0 );
        Folder children = folder.getChildren().get( 0 );

        Assert.assertTrue( Objects.equals( childView.getId(), children.getId() ) );
        Assert.assertEquals( childView.getTitle(), children.getTitle() );
        Assert.assertEquals( childView.isExpanded(), children.isExpanded() );
        Assert.assertEquals( childView.isSelected(), children.isSelected() );

    }

    @Test
    public void mapFromFolderToViewWithChildren(){

        Folder folder = getFolder();
        List<Folder> childs = getChildren();
        folder.setChildren( childs );

        Folder children = childs.get( 0 );
        FolderView folderView = new FolderView( folder );
        FolderView childView = new FolderView( children );

        Assert.assertTrue( folderView.getChildren().size() > 0 );

        Assert.assertTrue( Objects.equals( children.getId(), childView.getId() ) );
        Assert.assertEquals( children.getTitle(), childView.getTitle() );
        Assert.assertEquals( children.isExpanded(), childView.isExpanded() );
        Assert.assertEquals( children.isSelected(), childView.isSelected() );

    }

    private List<Folder> getChildren(){

        List<Folder> folders = new ArrayList<>(  );

        Folder folder1 = new Folder(  );
        folder1.setParent( null );
        folder1.setExpanded( false );
        folder1.setId( 2L );
        folder1.setSelected( true );
        folder1.setTitle( "vasia children test" );

        folders.add( folder1 );

        return folders;
    }

    private List<FolderView> getChildrenView(){

        List<FolderView> folders = new ArrayList<>(  );

        FolderView folder1 = new FolderView(  );
        folder1.setParentId( 0 );
        folder1.setExpanded( false );
        folder1.setId( 2L );
        folder1.setSelected( true );
        folder1.setTitle( "vasia children test" );

        folders.add( folder1 );

        return folders;
    }

    private Folder getFolder(){
        Folder folder1 = new Folder(  );
        folder1.setParent( null );
        folder1.setExpanded( false );
        folder1.setId( 1L );
        folder1.setSelected( true );
        folder1.setTitle( "vasia test" );

        return folder1;
    }

    private FolderView getView(){
        FolderView folderView = new FolderView(  );
        folderView.setParentId( 0 );
        folderView.setExpanded( false );
        folderView.setId( 1L );
        folderView.setSelected( true );
        folderView.setTitle( "vasia test" );

        return folderView;
    }

}
