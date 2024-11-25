package vttp.batch5.ssf.day16.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class giphySearchService {

    public static final String GET_GIPHY = "https://api.giphy.com/v1/gifs/search";

    @Value("${spring.data.url.apikey}")
    public String API_KEY;

    @Value("${spring.data.url.offset}")
    public int OFFSET;

    @Value("${spring.data.url.language}")
    public String LANGUAGE;

    @Value("${spring.data.url.bundle}")
    public String BUNDLE;

    public List<String> getGiphyUrls(String searchUrl) {

        // Configure the request
        RequestEntity<Void> request = RequestEntity
                .get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // Create REST template
        RestTemplate template = new RestTemplate();

        // Get the Reponse
        ResponseEntity<String> response;

        // Create a List to contain all URLs of the GIF results
        List<String> gifUrls = new ArrayList<>();

        try {
            response = template.exchange(request, String.class);

            // Extract the payload
            String payload = response.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject result = reader.readObject();

            // Get the "data" JsonArray (of JsonObject) from the result Object
            JsonArray data = result.getJsonArray("data");

            for (int idx = 0; idx < data.size(); idx++) {

                // Extract each JsonObject (a gif search result) from the data array
                JsonObject gif = data.getJsonObject(idx);

                // For each JsonObject (a gif search result), extract "Images Json Image"
                JsonObject images = gif.getJsonObject("images");

                // In "images", get the "fixed_height" JsonObject
                JsonObject fixedHeight = images.getJsonObject("fixed_height");

                // In "fixed_height", get the "url"
                String gifUrl = fixedHeight.getString("url");

                // Add url to List
                gifUrls.add(gifUrl);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }

        return gifUrls;

    }

    public String generateSearchUrlFromForm(String query, int limit, String rating) {

        return UriComponentsBuilder
                .fromUriString(GET_GIPHY)
                .queryParam("api_key", API_KEY)
                .queryParam("q", query)
                .queryParam("limit", limit)
                .queryParam("offset", OFFSET)
                .queryParam("lang", LANGUAGE)
                .queryParam("bundle", BUNDLE)
                .toUriString();

    }
}
