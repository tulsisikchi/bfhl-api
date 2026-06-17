package com.dypatil.bfhl.service;

import com.dypatil.bfhl.dto.BfhlRequest;
import com.dypatil.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Set<Character> VOWELS = Set.of('A','E','I','O','U');

    @Override
    public BfhlResponse process(BfhlRequest request, String requestId) {
        List<Object> rawData = request.getData();
        int totalReceived = rawData.size();

        List<String> validRaw = rawData.stream()
                .filter(o -> o != null)
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());

        int invalidIgnored = totalReceived - validRaw.size();

        Set<String> seen = new LinkedHashSet<>();
        boolean containsDuplicates = false;
        for (String s : validRaw) {
            if (!seen.add(s)) containsDuplicates = true;
        }

        List<String> deduplicated = new ArrayList<>(seen);
        int uniqueCount = deduplicated.size();

        List<BigDecimal> numbers = new ArrayList<>();
        List<String> alphabetStrings = new ArrayList<>();
        List<Character> allAlphaChars = new ArrayList<>();
        List<String> specialChars = new ArrayList<>();

        for (String element : deduplicated) {
            if (isNumber(element)) {
                numbers.add(new BigDecimal(element));
            } else if (isPureAlpha(element)) {
                alphabetStrings.add(element.toUpperCase());
                for (char c : element.toUpperCase().toCharArray()) allAlphaChars.add(c);
            } else if (isAlphanumeric(element)) {
                StringBuilder digits = new StringBuilder();
                StringBuilder letters = new StringBuilder();
                for (char c : element.toCharArray()) {
                    if (Character.isDigit(c)) digits.append(c);
                    else if (Character.isLetter(c)) letters.append(c);
                }
                if (digits.length() > 0) {
                    try { numbers.add(new BigDecimal(digits.toString())); } catch (Exception ignored) {}
                }
                if (letters.length() > 0) {
                    for (char c : letters.toString().toUpperCase().toCharArray()) allAlphaChars.add(c);
                    alphabetStrings.add(letters.toString().toUpperCase());
                }
            } else {
                specialChars.add(element);
            }
        }

        BfhlResponse response = new BfhlResponse();
        response.setSuccess(true);
        response.setRequestId(requestId);
        response.setContainsDuplicates(containsDuplicates);
        response.setUniqueElementCount(uniqueCount);

        List<BigDecimal> sortedNums = numbers.stream().sorted().collect(Collectors.toList());
        List<String> oddList = new ArrayList<>();
        List<String> evenList = new ArrayList<>();
        for (BigDecimal n : sortedNums) {
            try {
                long longVal = n.longValueExact();
                if (longVal % 2 == 0) evenList.add(formatNumber(n));
                else oddList.add(formatNumber(n));
            } catch (ArithmeticException e) {}
        }

        BigDecimal sum = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setSum(formatNumber(sum));
        response.setOddNumbers(oddList);
        response.setEvenNumbers(evenList);
        response.setNumberCount(numbers.size());
        response.setSortedNumbers(sortedNums.stream().map(this::formatNumber).collect(Collectors.toList()));

        if (!sortedNums.isEmpty()) {
            response.setLargestNumber(formatNumber(sortedNums.get(sortedNums.size() - 1)));
            response.setSmallestNumber(formatNumber(sortedNums.get(0)));
        }

        List<String> alphaCharList = allAlphaChars.stream().map(String::valueOf).collect(Collectors.toList());
        response.setAlphabets(alphaCharList);
        response.setAlphabetCount(allAlphaChars.size());

        int vowelCount = 0, consonantCount = 0;
        Map<String, Integer> freqMap = new TreeMap<>();
        for (char c : allAlphaChars) {
            if (VOWELS.contains(c)) vowelCount++;
            else consonantCount++;
            freqMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        response.setVowelCount(vowelCount);
        response.setConsonantCount(consonantCount);
        response.setAlphabetFrequency(freqMap);

        if (!alphabetStrings.isEmpty()) {
            response.setLongestAlphabeticValue(alphabetStrings.stream().max(Comparator.comparingInt(String::length)).orElse(null));
            response.setShortestAlphabeticValue(alphabetStrings.stream().min(Comparator.comparingInt(String::length)).orElse(null));
        }

        response.setSpecialCharacters(specialChars);
        response.setSpecialCharacterCount(specialChars.size());

        BfhlResponse.Summary summary = new BfhlResponse.Summary();
        summary.setTotalElementsReceived(totalReceived);
        summary.setValidElementsProcessed(uniqueCount);
        summary.setInvalidElementsIgnored(invalidIgnored);
        response.setSummary(summary);

        return response;
    }

    private boolean isNumber(String s) {
        try { new BigDecimal(s); return true; } catch (NumberFormatException e) { return false; }
    }

    private boolean isPureAlpha(String s) {
        return s.chars().allMatch(Character::isLetter);
    }

    private boolean isAlphanumeric(String s) {
        boolean hasLetter = s.chars().anyMatch(Character::isLetter);
        boolean hasDigit = s.chars().anyMatch(Character::isDigit);
        return hasLetter && hasDigit;
    }

    private String formatNumber(BigDecimal n) {
        return n.stripTrailingZeros().toPlainString();
    }
}