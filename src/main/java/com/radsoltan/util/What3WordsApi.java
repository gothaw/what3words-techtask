package com.radsoltan.util;

import com.radsoltan.config.Config;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.AutosuggestInputType;
import com.what3words.javawrapper.request.AutosuggestRequest;
import com.what3words.javawrapper.response.Autosuggest;
import com.what3words.javawrapper.response.Coordinates;
import com.what3words.javawrapper.response.Suggestion;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    public static Autosuggest getAutoSuggest(What3WordsV3 api, String address, String language, String country) {
        AutosuggestRequest.Builder builder = api.autosuggest(address)
                .inputType(AutosuggestInputType.GENERIC_VOICE)
                .language(language);
        if (StringUtils.hasText(country)) {
            return builder.clipToCountry(country).execute();
        } else {
            return builder.execute();
        }
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

    public static boolean isGbAddress(String address, List<Suggestion> suggestionList) {
        return suggestionList
                .stream()
                .map(Suggestion::getWords)
                .toList()
                .contains(address);
    }
}
