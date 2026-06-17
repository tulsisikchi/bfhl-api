package com.dypatil.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BfhlResponse {
    @JsonProperty("is_success") private boolean isSuccess;
    @JsonProperty("request_id") private String requestId;
    @JsonProperty("odd_numbers") private List<String> oddNumbers;
    @JsonProperty("even_numbers") private List<String> evenNumbers;
    @JsonProperty("alphabets") private List<String> alphabets;
    @JsonProperty("special_characters") private List<String> specialCharacters;
    @JsonProperty("sum") private String sum;
    @JsonProperty("largest_number") private String largestNumber;
    @JsonProperty("smallest_number") private String smallestNumber;
    @JsonProperty("alphabet_count") private int alphabetCount;
    @JsonProperty("number_count") private int numberCount;
    @JsonProperty("special_character_count") private int specialCharacterCount;
    @JsonProperty("contains_duplicates") private boolean containsDuplicates;
    @JsonProperty("unique_element_count") private int uniqueElementCount;
    @JsonProperty("processing_time_ms") private long processingTimeMs;
    @JsonProperty("sorted_numbers") private List<String> sortedNumbers;
    @JsonProperty("vowel_count") private int vowelCount;
    @JsonProperty("consonant_count") private int consonantCount;
    @JsonProperty("alphabet_frequency") private Map<String, Integer> alphabetFrequency;
    @JsonProperty("longest_alphabetic_value") private String longestAlphabeticValue;
    @JsonProperty("shortest_alphabetic_value") private String shortestAlphabeticValue;
    @JsonProperty("summary") private Summary summary;

    public static class Summary {
        @JsonProperty("total_elements_received") private int totalElementsReceived;
        @JsonProperty("valid_elements_processed") private int validElementsProcessed;
        @JsonProperty("invalid_elements_ignored") private int invalidElementsIgnored;
        public void setTotalElementsReceived(int t) { this.totalElementsReceived = t; }
        public void setValidElementsProcessed(int v) { this.validElementsProcessed = v; }
        public void setInvalidElementsIgnored(int i) { this.invalidElementsIgnored = i; }
    }

    public void setSuccess(boolean s) { this.isSuccess = s; }
    public void setRequestId(String r) { this.requestId = r; }
    public void setOddNumbers(List<String> o) { this.oddNumbers = o; }
    public void setEvenNumbers(List<String> e) { this.evenNumbers = e; }
    public void setAlphabets(List<String> a) { this.alphabets = a; }
    public void setSpecialCharacters(List<String> s) { this.specialCharacters = s; }
    public void setSum(String s) { this.sum = s; }
    public void setLargestNumber(String l) { this.largestNumber = l; }
    public void setSmallestNumber(String s) { this.smallestNumber = s; }
    public void setAlphabetCount(int a) { this.alphabetCount = a; }
    public void setNumberCount(int n) { this.numberCount = n; }
    public void setSpecialCharacterCount(int s) { this.specialCharacterCount = s; }
    public void setContainsDuplicates(boolean c) { this.containsDuplicates = c; }
    public void setUniqueElementCount(int u) { this.uniqueElementCount = u; }
    public void setProcessingTimeMs(long p) { this.processingTimeMs = p; }
    public void setSortedNumbers(List<String> s) { this.sortedNumbers = s; }
    public void setVowelCount(int v) { this.vowelCount = v; }
    public void setConsonantCount(int c) { this.consonantCount = c; }
    public void setAlphabetFrequency(Map<String, Integer> a) { this.alphabetFrequency = a; }
    public void setLongestAlphabeticValue(String l) { this.longestAlphabeticValue = l; }
    public void setShortestAlphabeticValue(String s) { this.shortestAlphabeticValue = s; }
    public void setSummary(Summary s) { this.summary = s; }
}