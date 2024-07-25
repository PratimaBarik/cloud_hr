package com.app.employeePortal.Language.Controller;

import com.app.employeePortal.Language.mapper.TargetWordMapper;
import com.app.employeePortal.Language.mapper.WordMapper;
import com.app.employeePortal.Language.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping("words")
@CrossOrigin(maxAge = 3600)
public class WordsController {
    @Autowired
    WordService wordService;

    @PostMapping
    public ResponseEntity<?> saveWords(@RequestHeader("Authorization") String authorization, @RequestBody List<String> words) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.saveWords(words));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/allWords")
    public ResponseEntity<?> getAllWords(@RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.getAllWords());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWord(@PathVariable("id") String id,@RequestHeader("Authorization") String authorization, @RequestBody WordMapper word) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.updateWord(id,word));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/deleteOrReinstate/{id}")
    public ResponseEntity<?> deleteOrReinstate(@PathVariable("id") String id,@RequestHeader("Authorization") String authorization,@RequestBody WordMapper wordMapper) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.deleteOrReinstate(id,wordMapper));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/convertWord")
    public ResponseEntity<?> convertLanguage(@RequestBody TargetWordMapper language,
                                                                  @RequestHeader("Authorization") String authorization) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.convertLanguage(language));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/navLangSearch/{word}")
    public ResponseEntity<?> navLanguageSearch(@RequestHeader("Authorization") String authorization,
                                               @PathVariable("word")String word) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.navLanguageSearchByWord(word));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/import/excel")
    public ResponseEntity<?> importWordList(@RequestHeader("Authorization") String authorization, HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(wordService.importWordList(multipartFile));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
