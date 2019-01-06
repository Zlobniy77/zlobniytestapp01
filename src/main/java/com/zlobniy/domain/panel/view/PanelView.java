package com.zlobniy.domain.panel.view;

import java.util.List;

public class PanelView {

    private Long id;
    private String title;
    private List<String> header;
    private List<PanelRowView> body;

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

    public List<String> getHeader() {
        return header;
    }

    public void setHeader( List<String> header ) {
        this.header = header;
    }

    public List<PanelRowView> getBody() {
        return body;
    }

    public void setBody( List<PanelRowView> body ) {
        this.body = body;
    }
}
