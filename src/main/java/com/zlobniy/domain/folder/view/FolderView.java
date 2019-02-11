package com.zlobniy.domain.folder.view;

import com.zlobniy.domain.folder.entity.Folder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FolderView {

    private Long id;
    private String title;
    private boolean expanded;
    private boolean selected;
    private boolean folder = true;
    private long parentId;
    private List<FolderView> children = new ArrayList<>(  );

    public FolderView( Folder folder ){
        if (folder == null) return;

        this.id = folder.getId();
        this.title = folder.getTitle();
        this.folder = true;
        this.expanded = folder.isExpanded();
        this.selected = folder.isSelected();
        this.children = folder.getChildren().stream().map( FolderView::new ).collect(Collectors.toList());
        this.parentId = folder.getParent() != null ? folder.getParent().getId() : 0L;
    }

    public FolderView(){

    }

    public Long getId(){
        return id;
    }

    public void setId( Long id ){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public boolean isExpanded(){
        return expanded;
    }

    public void setExpanded( boolean expanded ){
        this.expanded = expanded;
    }

    public boolean isFolder(){
        return folder;
    }

    public void setFolder( boolean folder ){
        this.folder = folder;
    }

    public List<FolderView> getChildren(){
        return children;
    }

    public void setChildren( List<FolderView> children ){
        this.children = children;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected( boolean selected ) {
        this.selected = selected;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId( long parentId ) {
        this.parentId = parentId;
    }
}
