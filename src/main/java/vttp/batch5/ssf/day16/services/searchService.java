package vttp.batch5.ssf.day16.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class searchService {

    @Autowired
    private giphySearchService giphySvc;
    
    public String generateSearchString(MultiValueMap<String, String> form) {

        String query = form.getFirst("query").trim();
        int limit = Integer.parseInt(form.getFirst("limit").trim());
        String rating = form.getFirst("rating").trim();

        // Pass the form info to HttpService
        return giphySvc.generateSearchUrlFromForm(query, limit, rating);
    }

    public List<String> getGifUrlResults(String searchUrl) {
        return giphySvc.getGiphyUrls(searchUrl);
    }

}
