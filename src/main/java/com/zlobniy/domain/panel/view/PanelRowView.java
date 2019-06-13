package com.zlobniy.domain.panel.view;

import com.zlobniy.domain.panel.entity.BgrData;

import java.util.List;
import java.util.stream.Collectors;

public class PanelRowView {

    private Integer index;
    private List<PanelRowValueView> cells;


    public PanelRowView( BgrData bgrData){
        this.index = bgrData.getIndex();
        this.cells = bgrData.getValues().stream().map( PanelRowValueView::new ).collect( Collectors.toList() );
    }

    public PanelRowView(){

    }

    public List<PanelRowValueView> getCells() {
        return cells;
    }

    public void setCells( List<PanelRowValueView> cells ) {
        this.cells = cells;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex( Integer index ) {
        this.index = index;
    }
}
