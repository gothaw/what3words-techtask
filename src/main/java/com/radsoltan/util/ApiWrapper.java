package com.radsoltan.util;

import com.radsoltan.config.Config;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.AutosuggestInputType;
import com.what3words.javawrapper.request.AutosuggestRequest;
import com.what3words.javawrapper.response.Autosuggest;
import com.what3words.javawrapper.response.ConvertTo3WA;
import com.what3words.javawrapper.response.ConvertToCoordinates;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Class includes a singleton pattern for What3Words API wrapper creation.
 * It also includes a number of methods that interact with the API.
 * See: https://developer.what3words.com/tutorial/java
 */
@Component
public class ApiWrapper {
    private static What3WordsV3 instance;

    // Preventing instantiation
    private ApiWrapper() {
    }

    /**
     * Gets instance for the Java wrapper of What3Words API. Singleton pattern.
     *
     * @return instance of the wrapper
     */
    public What3WordsV3 getInstance() {
        if (instance == null) {
            instance = new What3WordsV3(Config.API_KEY);
        }

        return instance;
    }

    /**
     * Gets auto suggestions based on the 3 word address, language and country provided.
     *
     * @param api      Java wrapper of What3Words API
     * @param address  3wa address
     * @param language string representing language
     * @param country  string representing country
     * @return AutoSuggest object that includes 3 word address suggestions
     */
    public static Autosuggest getAutoSuggest(What3WordsV3 api, String address, String language, String country) {
        return api.autosuggest(address)
                .inputType(AutosuggestInputType.GENERIC_VOICE)
                .language(language)
                .clipToCountry(country)
                .execute();
    }

    /**
     * Gets coordinates based on 3 word address provided.
     *
     * @param api     Java wrapper of What3Words API
     * @param address 3wa address
     * @return Coordinates response
     */
    public static ConvertToCoordinates getCoordinatesBasedOnThreeWordAddress(What3WordsV3 api, String address) {
        return api.convertToCoordinates(address)
                .execute();
    }

    /**
     * Gets 3 word address based on coordinates provided.
     *
     * @param api         Java wrapper of What3Words API
     * @param coordinates Request coordinates
     * @param language    string representing language
     * @return 3wa address response
     */
    public static ConvertTo3WA getThreeWordAddressBasedOnCoordinates(What3WordsV3 api, com.what3words.javawrapper.request.Coordinates coordinates, String language) {
        return api.convertTo3wa(coordinates)
                .language(language)
                .execute();
    }
}
