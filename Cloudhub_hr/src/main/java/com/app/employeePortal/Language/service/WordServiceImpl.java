package com.app.employeePortal.Language.service;

import com.app.employeePortal.Language.Entity.Words;
import com.app.employeePortal.Language.mapper.TargetWordMapper;
import com.app.employeePortal.Language.mapper.TargetWordViewMapper;
import com.app.employeePortal.Language.mapper.WordMapper;
import com.app.employeePortal.Language.mapper.WordViewMapper;
import com.app.employeePortal.Language.repository.WordsRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class WordServiceImpl implements WordService {
    @Autowired
    WordsRepository wordsRepository;

    @Override
    public List<WordViewMapper> saveWords(List<String> words) {
        List<WordViewMapper> mappers = new ArrayList<>();
        if (null != words && !words.isEmpty()) {
            for (String word : words) {
                Words eWord = wordsRepository.findByEnglish(word.trim());
                if (null == eWord) {
                    Words word1 = new Words();
                    word1.setCreationDate(new Date());
                    word1.setEnglish(word.trim());
                    word1.setLiveInd(true);
                    Words words1 = wordsRepository.save(word1);
                    mappers.add(WordViewMapper.from(words1));
                }
            }
        }
        return mappers;
    }

    @Override
    public List<WordViewMapper> getAllWords() {
        return wordsRepository.findByLiveInd(true).stream().map(w -> WordViewMapper.from(w))
                .collect(Collectors.toList());
    }

    @Override
    public WordViewMapper updateWord(String id, WordMapper word) {
        Words word1 = wordsRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "word not found by id " + id));
        if (null != word.getDutch() && !word.getDutch().isEmpty()) {
            word1.setDutch(word.getDutch());
        }
        if (null != word.getEnglish() && !word.getEnglish().isEmpty()) {
            word1.setEnglish(word.getEnglish());
        }

        if (null != word.getGerman() && !word.getGerman().isEmpty()) {
            word1.setGerman(word.getGerman());
        }

        if (null != word.getItalian() && !word.getItalian().isEmpty()) {
            word1.setItalian(word.getItalian());
        }
        if (null != word.getSpanish() && !word.getSpanish().isEmpty()) {
            word1.setSpanish(word.getSpanish());
        }
        if (null != word.getFrench() && !word.getFrench().isEmpty()) {
            word1.setFrench(word.getFrench());
        }
        Words words = wordsRepository.save(word1);
        return WordViewMapper.from(words);
    }

    @Override
    public String deleteOrReinstate(String id, WordMapper mapper) {
        Words word1 = wordsRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "word not found by id " + id));
        if (mapper.isLiveInd()) {
            word1.setLiveInd(true);
            wordsRepository.save(word1);
            return "Word reinstated successfully";
        } else {
            word1.setLiveInd(false);
            wordsRepository.save(word1);
            return "Word Deleted successfully";
        }
    }

    @Override
    public List<TargetWordViewMapper> convertLanguage(TargetWordMapper language) {
        List<String> words = language.getQ();

        String languageName = language.getTarget();
        List<TargetWordViewMapper> resultList = new ArrayList<>();
        for (String word : words) {
            System.out.println("word "+word);

            Words lan = wordsRepository.findByEnglishIgnoreCaseAndLiveInd(word.trim(), true);
            TargetWordViewMapper mapper = new TargetWordViewMapper();
            if (null != lan) {
                if (languageName.equalsIgnoreCase("Dutch")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getDutch());
                } else if (languageName.equalsIgnoreCase("English")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getEnglish());
                } else if (languageName.equalsIgnoreCase("German")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getGerman());
                } else if (languageName.equalsIgnoreCase("Italian")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getItalian());
                } else if (languageName.equalsIgnoreCase("Spanish")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getSpanish());
                } else if (languageName.equalsIgnoreCase("French")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getFrench());
                }
                else if (languageName.equalsIgnoreCase("Polish")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getPolish());
                }
                else if (languageName.equalsIgnoreCase("Arabic")) {
                    mapper.setDetectedSourceLanguage("English");
                    mapper.setTranslatedText(lan.getArabic());
                }

            } else {
                mapper.setDetectedSourceLanguage("English");
                mapper.setTranslatedText("****");
            }
            resultList.add(mapper);
        }
        return resultList;
    }

    @Override
    public List<WordViewMapper> navLanguageSearchByWord(String word) {
        String searchTerm = word.trim().toLowerCase();
        List<Words> list = new ArrayList<>();
        List<Words> eNavWord = wordsRepository.findByEnglishContaining(searchTerm);
        list.addAll(eNavWord);
        List<Words> dNavWord = wordsRepository.findByDutchContaining(searchTerm);
        list.addAll(dNavWord);
        List<Words> gNavWord = wordsRepository.findByGermanContaining(searchTerm);
        list.addAll(gNavWord);
        List<Words> iNavWord = wordsRepository.findByItalianContaining(searchTerm);
        list.addAll(iNavWord);
        List<Words> sNavWord = wordsRepository.findBySpanishContaining(searchTerm);
        list.addAll(sNavWord);
        List<Words> fNavWord = wordsRepository.findByFrenchContaining(searchTerm);
        list.addAll(fNavWord);
        return list.stream().distinct().filter(w -> w.isLiveInd()).map(n -> WordViewMapper.from(n))
                .collect(Collectors.toList());
    }

    @Override
    public Object importWordList(MultipartFile file) throws IOException {
        List<Words> words = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            // Assuming the first two sheets are the ones we need
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) { // skip header row
                        continue;
                    }

                    String eng = getCellValue(row, 0);
                    String dut=getCellValue(row,1);
                    String ger=getCellValue(row,2);
                    String fr=getCellValue(row,3);
                    String spa=getCellValue(row,4);
                    String ita=getCellValue(row,5);
                    String pol=getCellValue(row,6);
                    String arab=getCellValue(row,7);
                    // Validate that make, model, and type are not empty
                    if (isNotEmpty(eng)) {
                        System.out.println("word "+eng);
                        Words word2 = wordsRepository.findByEnglishIgnoreCase(eng.trim());
//                        System.out.println("outside if "+word2);
                        if (null == word2) {
                            System.out.println("in if "+word2);
                            Words word = new Words();
                            word.setEnglish(eng);
                            word.setDutch(dut);
                            word.setGerman(ger);
                            word.setFrench(fr);
                            word.setSpanish(spa);
                            word.setItalian(ita);
                            word.setPolish(pol);
                            word.setArabic(arab);
                            word.setLiveInd(true);
                            word.setCreationDate(new Date());
                            words.add(word);
                        }
                    }
                }
            }
        }
        wordsRepository.saveAll(words);
        return "imported successfully";
    }

    private String getCellValue(Row row, int cellIndex) {
        return row.getCell(cellIndex) != null ? row.getCell(cellIndex).getStringCellValue() : "";
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
