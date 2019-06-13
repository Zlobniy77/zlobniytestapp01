package com.zlobniy.domain.panel.view;

import com.zlobniy.domain.panel.entity.Panel;

import java.util.List;
import java.util.stream.Collectors;

public class PanelView {

    private Long id;
    private String title;
    private List<PanelHeaderView> headers;
    private List<PanelRowView> rows;


    public PanelView( Panel panel ){
        this.id = panel.getId();
        this.title = panel.getTitle();
        this.headers = panel.getDescriptions().stream().map( PanelHeaderView::new ).collect( Collectors.toList() );
        this.rows = panel.getData().stream().map( PanelRowView::new ).collect( Collectors.toList() );
    }

    public PanelView(){

    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<PanelHeaderView> getHeaders() {
        return headers;
    }

    public void setHeaders( List<PanelHeaderView> headers ) {
        this.headers = headers;
    }

    public List<PanelRowView> getRows() {
        return rows;
    }

    public void setRows( List<PanelRowView> rows ) {
        this.rows = rows;
    }
}
