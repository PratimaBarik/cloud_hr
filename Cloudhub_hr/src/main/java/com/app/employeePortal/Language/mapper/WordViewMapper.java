package com.app.employeePortal.Language.mapper;

import com.app.employeePortal.Language.Entity.Words;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class WordViewMapper {
    private long id;
    private String english;
    private String dutch;
    private Date creationDate;
    private String french;
    private String italian;
    private String spanish;
    private String german;

    public static WordViewMapper from(Words word) {
        return WordViewMapper.builder()
                .id(word.getId())
                .dutch(word.getDutch())
                .english(word.getEnglish())
                .french(word.getFrench())
                .german(word.getGerman())
                .italian(word.getItalian())
                .spanish(word.getSpanish())
                .creationDate(word.getCreationDate())
                .build();
    }
}
