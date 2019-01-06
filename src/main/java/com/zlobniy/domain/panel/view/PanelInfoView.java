package com.zlobniy.domain.panel.view;

import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.util.DateFormatUtil;
import com.zlobniy.view.InfoView;

import java.util.Date;

public class PanelInfoView implements InfoView {

    private Long id;
    private String title;
    private Date creationDate;

    public PanelInfoView( Panel panel ){
        setId( panel.getId() );
        setCreationDate( panel.getCreationDate() );
        setTitle( panel.getTitle() );
    }

    public PanelInfoView(){

    }


    @Override
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    @Override
    public String getCreationDate(){
        return DateFormatUtil.dateToString( this.creationDate );
    }

    @Override
    public String getType() {
        return "Panel";
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }
}
