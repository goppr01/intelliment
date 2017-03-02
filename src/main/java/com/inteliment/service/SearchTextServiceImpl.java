package com.inteliment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.inteliment.model.SearchResults;

@Service
public class SearchTextServiceImpl implements SearchTextService {

	@Override
	public SearchResults searchTextInParagraph(List<String> searchText) {
		
		
		SearchResults results = new SearchResults();
		LinkedHashMap<String,Integer> countMap = new LinkedHashMap<String,Integer>();
		
		
		try {
			String paragraph= new String(Files.readAllBytes(Paths.get("Paragraph.txt")));
			
	        for (String text : searchText) {
	        	int count = StringUtils.countOccurrencesOf(paragraph, text);
	        	
	        	countMap.put(text, count);
			}
			
			 results.setCounts(countMap);
			 return results;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//log the exception
			
		}
		return null;
	}
	
	

	@Override
	public List<Entry<String, Integer>>  searchTopText(int topCount) {
		
		SearchResults results = new SearchResults();
		Map<String, Integer> wordMap = new HashMap<String, Integer>(); 
		List<Entry<String, Integer>>  sortedStringsByCount=null;
		
		try {
			String paragraph= new String(Files.readAllBytes(Paths.get("Paragraph.txt")));
			StringTokenizer st = new StringTokenizer(paragraph, " .,\r\n");
	
			
			//count the occurances of each string and put in in map
             while(st.hasMoreTokens()){
                 String tmp = st.nextToken().toLowerCase();
                 if(wordMap.containsKey(tmp)){
                     wordMap.put(tmp, wordMap.get(tmp)+1);
                 } else {
                     wordMap.put(tmp, 1);
                 }
             }
             
             //sort the words based on count and return the top n
             
			sortedStringsByCount= sortByValue(wordMap);
			if(topCount<sortedStringsByCount.size())
		         sortedStringsByCount=  sortedStringsByCount.subList(0, topCount-1);
	
			
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			//log the exception
			
		}
		
		return sortedStringsByCount;
	}
	
	
	 
    private List<Entry<String, Integer>> sortByValue(Map<String, Integer> wordMap){
         
        Set<Entry<String, Integer>> set = wordMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        return list;
    }


}
