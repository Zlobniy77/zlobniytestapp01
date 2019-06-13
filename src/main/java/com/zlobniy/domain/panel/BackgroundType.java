package com.zlobniy.domain.panel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ans on 6/13/2019.
 */
public enum BackgroundType {

    EMAIL(1),
    PHONE(2),
    STANDARD(0);

    private final Integer type;
    private static final Map<Integer, BackgroundType> types;

    static {
        types = new HashMap<>();
        types.put(1, EMAIL);
        types.put(2, PHONE);
        types.put(0, STANDARD);
    }

    BackgroundType( Integer type ) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public synchronized static BackgroundType fromString( String type ) {
        return types.get( Integer.parseInt( type ) );
    }

    public synchronized static BackgroundType fromInt( Integer type ) {
        return types.get( type );
    }
}
