package com.zlobniy.domain.panel.view;

import java.util.List;

public class PanelView {

    private Long id;
    private String title;
    private List<PanelHeaderView> headers;
    private List<PanelRowView> rows;

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
