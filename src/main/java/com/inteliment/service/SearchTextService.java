package com.inteliment.service;

import java.util.List;
import java.util.Map.Entry;

import com.inteliment.model.SearchResults;

public interface SearchTextService {

	public SearchResults searchTextInParagraph(List<String> searchText);

	public List<Entry<String, Integer>> searchTopText(int topCount);

}
