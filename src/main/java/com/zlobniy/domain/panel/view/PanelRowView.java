package com.zlobniy.domain.panel.view;

import java.util.List;

public class PanelRowView {

    private Integer index;
    private List<PanelRowValueView> cells;

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
