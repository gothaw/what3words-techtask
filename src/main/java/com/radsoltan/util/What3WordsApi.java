package com.radsoltan.util;

import com.radsoltan.config.Constants;
import com.what3words.javawrapper.What3WordsV3;

public class What3WordsApi {
    private static What3WordsV3 instance;

    private What3WordsApi(){}

    public static What3WordsV3 getInstance() {
        if (instance == null) {
            instance = new What3WordsV3(Constants.API_KEY);
        }

        return instance;
    }
}
