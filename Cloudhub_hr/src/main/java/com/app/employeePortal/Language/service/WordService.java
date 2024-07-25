package com.app.employeePortal.Language.service;

import com.app.employeePortal.Language.mapper.TargetWordMapper;
import com.app.employeePortal.Language.mapper.TargetWordViewMapper;
import com.app.employeePortal.Language.mapper.WordMapper;
import com.app.employeePortal.Language.mapper.WordViewMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WordService {
    List<WordViewMapper> saveWords(List<String> words);

    List<WordViewMapper> getAllWords();

    WordViewMapper updateWord(String id, WordMapper word);

    String deleteOrReinstate(String id,WordMapper wordMapper);

    List<TargetWordViewMapper> convertLanguage(TargetWordMapper language);

    List<WordViewMapper> navLanguageSearchByWord(String word);

    Object importWordList(MultipartFile multipartFile) throws IOException;
}
