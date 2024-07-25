package com.app.employeePortal.Language.mapper;

import java.time.LocalDateTime;

import com.app.employeePortal.Language.Entity.Languages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageDTO {
    private String languageId;
    private String language;
    private LocalDateTime creationDate;
    private boolean mandatoryInd;
    private boolean activeInd;
    private boolean baseInd;
    private String languageCode;

    public static LanguageDTO from(Languages languages) {
        return LanguageDTO.builder()
                .language(languages.getLanguage())
                .activeInd(languages.isActiveInd())
                .mandatoryInd(languages.isMandatoryInd())
                .languageCode(languages.getLanguageCode())
                .baseInd(languages.isBaseInd())
                .languageId(String.valueOf(languages.getLanguageId()))
                .build();
    }
}
