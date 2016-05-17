package com.ejemplo.googlemapsv2.interfaces;

import java.util.HashMap;
import java.util.List;

/**
 * Created by choqu_000 on 25/03/2016.
 */
public interface OnTaskCompleted {

    void onTaskCompleted(List<List<HashMap<String, String>>> jsonObj);
}
