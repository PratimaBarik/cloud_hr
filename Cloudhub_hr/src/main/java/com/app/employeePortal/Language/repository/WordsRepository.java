package com.app.employeePortal.Language.repository;

import com.app.employeePortal.Language.Entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsRepository extends JpaRepository<Words,Long> {
    Words findByEnglish(String word);

    List<Words> findByLiveInd(boolean b);

    Words findByEnglishIgnoreCaseAndLiveInd(String word, boolean b);

    List<Words> findByEnglishContaining(String searchTerm);

    List<Words> findByDutchContaining(String searchTerm);

	List<Words> findByGermanContaining(String searchTerm);

	List<Words> findByItalianContaining(String searchTerm);

	List<Words> findBySpanishContaining(String searchTerm);

	List<Words> findByFrenchContaining(String searchTerm);

    Words findByEnglishIgnoreCase(String trim);
}
