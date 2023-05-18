package com.radsoltan.util;

import com.radsoltan.config.Config;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.Coordinates;

public class What3WordsApi {
    private static What3WordsV3 instance;

    private What3WordsApi() {
    }

    public static What3WordsV3 getInstance() {
        if (instance == null) {
            instance = new What3WordsV3(Config.API_KEY);
        }

        return instance;
    }

    public static Coordinates getCoordinatesBasedOnAddress(What3WordsV3 api, String address) {
        return api.convertToCoordinates(address)
                .execute()
                .getCoordinates();
    }

    public static String getAddressBasedOnCoordinates(What3WordsV3 api, com.what3words.javawrapper.request.Coordinates coordinates, String language) {
        return api.convertTo3wa(coordinates)
                .language(language)
                .execute()
                .getWords();
    }
}
