package com.zlobniy.domain.panel.view;

import com.zlobniy.domain.panel.entity.BgrDescription;

/**
 * Created by ans on 6/13/2019.
 */
public class PanelHeaderView {

    private Integer index;
    private String title;
    private Integer type;


    public PanelHeaderView( BgrDescription description ){
        this.index = description.getIndex();
        this.title = description.getValue();
        this.type = description.getType().getType();
    }

    public PanelHeaderView(){

    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex( Integer index ) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType( Integer type ) {
        this.type = type;
    }

}
