package oracle.alura.literaturachallenge.repository;

import oracle.alura.literaturachallenge.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findFirstByTitleIgnoreCase(String title);

    List<Book> findAllByOrderByTitleAsc();

    List<Book> findByLanguageIgnoreCaseOrderByTitleAsc(String language);

    long countByLanguageIgnoreCase(String language);

    @Query("SELECT b FROM Book b JOIN FETCH b.author ORDER BY b.title ASC")
    List<Book> findAllWithAuthorOrderByTitleAsc();
}
