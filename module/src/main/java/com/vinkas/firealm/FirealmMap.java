package com.vinkas.firealm;

import com.vinkas.firealm.model.FirealmModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinoth on 31-5-16.
 */
public class FirealmMap extends HashMap<Class<? extends FirealmModel>, String> {

    public FirealmMap() {
    }

    public FirealmMap(int capacity) {
        super(capacity);
    }

    public FirealmMap(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    public FirealmMap(Map<? extends Class<? extends FirealmModel>, ? extends String> map) {
        super(map);
    }
}