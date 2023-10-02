package com.m2l.meta.utils;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateUtils {
    public static List<String> bannedWords = new ArrayList<>();

    static {
        try (FileInputStream file = new FileInputStream(CommonConstants.AppParams.FILE_PATH_BANNED_WORDS_SERVER);
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            bannedWords = sheet.read().stream().map(row -> row.getCellText(1)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean checkIfWordsValid(String checkWord) {
        System.out.println("START CHECK : " +checkWord);
        return bannedWords.stream().noneMatch(word -> {
            System.out.println(" RESULT : " + word + " " + checkWord.toLowerCase().contains(word));
            return checkWord.toLowerCase().contains(word);
        });
    }

}
