package com.inteliment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inteliment.model.SearchModel;
import com.inteliment.model.SearchResults;
import com.inteliment.service.SearchTextService;

@RestController
public class CounterAPIController {

	@Autowired
	SearchTextService searchTextService;

	@RequestMapping(value = "/counter-api/search", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public SearchResults searchText(@RequestBody SearchModel searchTextModel) {
		SearchResults searchResults = searchTextService.searchTextInParagraph(searchTextModel.getSearchText());

		return searchResults;
	}

	@RequestMapping(value = "/counter-api/top/{count}", method = RequestMethod.GET)
	public void downloadCSV(@PathVariable int count, HttpServletResponse response) throws IOException {

		response.setContentType("text/csv");
		String reportName = "searchText.csv";
		response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		List<Map.Entry<String, Integer>> rows = searchTextService.searchTopText(count);

		// convert the result to the ouput format expected
		for (Entry<String, Integer> entry : rows) {
			String outputString = entry.getKey() + "|" + entry.getValue() + "\r\n";
			response.getOutputStream().print(outputString);
		}

		response.getOutputStream().flush();

	}

}
