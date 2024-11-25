package vttp.batch5.ssf.day16.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.batch5.ssf.day16.services.searchService;

import static vttp.batch5.ssf.day16.Constants.*;

@Controller
@RequestMapping
public class searchController {

    @Autowired
    private searchService searchSvc;

    @GetMapping(path = { "/search" })
    public String getSearch(
            Model model,
            @RequestParam MultiValueMap<String, String> form) {

        // Pass the form to indexService to extract form + generate search URL
        String searchUrl = searchSvc.generateSearchString(form);
        
        // Obtain the search results
        List<String> gifUrls = searchSvc.getGifUrlResults(searchUrl);

        // Bind the list of gif Urls to model
        model.addAttribute(ATTR_QUERY, form.getFirst("query"));
        model.addAttribute(ATTR_GIF_URL_LIST, gifUrls);

        return "searchResult";
    }
}
