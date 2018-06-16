package com.bird.framework.xsy.mall.service.ansj;

import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;

@Slf4j
public class DicLibraryService {

    public static void insert(String keyword) {
        DicLibrary.insert(DicLibrary.DEFAULT, keyword);
    }

    public static void analysis(String payload) {
        Result parse = ToAnalysis.parse(payload);
        parse.getTerms().forEach(term -> {
            log.info(term.getName());
        });
    }
}
