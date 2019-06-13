package com.zlobniy.domain.panel.view;

import com.zlobniy.domain.panel.entity.BgrDataValue;

/**
 * Created by ans on 6/13/2019.
 */
public class PanelRowValueView {

    private Integer index;
    private String title;

    public PanelRowValueView( BgrDataValue bgrDataValue ){
        this.index = bgrDataValue.getColumnIndex();
        this.title = bgrDataValue.getValue();
    }

    public PanelRowValueView(){

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

}
