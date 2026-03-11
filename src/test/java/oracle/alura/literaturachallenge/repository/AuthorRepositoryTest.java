package oracle.alura.literaturachallenge.repository;

import oracle.alura.literaturachallenge.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findsAuthorsAliveInYear() {
        Author author1 = new Author("Autor Histórico", 1900, 1980);
        Author author2 = new Author("Autor Actual", 1950, null);
        Author author3 = new Author("Autor Fallecido", 1800, 1850);
        authorRepository.saveAll(List.of(author1, author2, author3));

        List<Author> alive = authorRepository.findAuthorsAliveInYear(1960);

        assertThat(alive)
                .extracting(Author::getName)
                .containsExactlyInAnyOrder("Autor Histórico", "Autor Actual")
                .doesNotContain("Autor Fallecido");
    }
}

