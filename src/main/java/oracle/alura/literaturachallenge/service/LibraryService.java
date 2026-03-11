package oracle.alura.literaturachallenge.service;

import oracle.alura.literaturachallenge.dto.GutendexAuthorDto;
import oracle.alura.literaturachallenge.dto.GutendexBookDto;
import oracle.alura.literaturachallenge.exception.BusinessException;
import oracle.alura.literaturachallenge.model.Author;
import oracle.alura.literaturachallenge.model.Book;
import oracle.alura.literaturachallenge.repository.AuthorRepository;
import oracle.alura.literaturachallenge.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexService gutendexService;

    public LibraryService(BookRepository bookRepository,
                          AuthorRepository authorRepository,
                          GutendexService gutendexService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gutendexService = gutendexService;
    }

    @Transactional
    public Book searchAndStoreBookByTitle(String rawTitle) {
        String title = Optional.ofNullable(rawTitle)
                .map(String::trim)
                .filter(t -> !t.isBlank())
                .orElseThrow(() -> new BusinessException("Debes ingresar un título válido."));

        return bookRepository.findFirstByTitleIgnoreCase(title)
                .orElseGet(() -> importBookFromApi(title));
    }

    private Book importBookFromApi(String title) {
        GutendexBookDto bookDto = gutendexService.fetchFirstMatch(title)
                .orElseThrow(() -> new BusinessException("No se encontraron resultados para " + title));

        GutendexAuthorDto authorDto = bookDto.getAuthors().stream().findFirst()
                .orElseThrow(() -> new BusinessException("El libro recibido no contiene autor."));

        Author author = authorRepository.findByNameIgnoreCase(authorDto.getName())
                .map(existing -> updateAuthorMetadata(existing, authorDto))
                .orElseGet(() -> new Author(authorDto.getName(), authorDto.getBirthYear(), authorDto.getDeathYear()));

        author = authorRepository.save(author);

        String language = bookDto.getLanguages().stream()
                .findFirst()
                .map(lang -> lang.toLowerCase(Locale.ROOT))
                .orElse("desconocido");
        int downloads = Optional.ofNullable(bookDto.getDownloadCount()).orElse(0);

        Book book = new Book(bookDto.getTitle(), language, downloads);
        author.addBook(book);
        return bookRepository.save(book);
    }

    private Author updateAuthorMetadata(Author author, GutendexAuthorDto dto) {
        if (author.getBirthYear() == null) {
            author.setBirthYear(dto.getBirthYear());
        }
        if (author.getDeathYear() == null) {
            author.setDeathYear(dto.getDeathYear());
        }
        return author;
    }

    @Transactional(readOnly = true)
    public List<Book> listAllBooks() {
        return bookRepository.findAllWithAuthorOrderByTitleAsc();
    }

    @Transactional(readOnly = true)
    public List<Book> listBooksByLanguage(String rawLanguage) {
        String language = Optional.ofNullable(rawLanguage)
                .map(String::trim)
                .filter(l -> !l.isBlank())
                .map(l -> l.toLowerCase(Locale.ROOT))
                .orElseThrow(() -> new BusinessException("Debes indicar un idioma."));
        return bookRepository.findByLanguageIgnoreCaseOrderByTitleAsc(language);
    }

    @Transactional(readOnly = true)
    public List<Author> listAllAuthors() {
        return authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional(readOnly = true)
    public List<Author> listAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> countBooksByLanguages(Collection<String> languages) {
        if (languages == null || languages.isEmpty()) {
            throw new BusinessException("Debes indicar al menos un idioma para las estadísticas.");
        }
        return languages.stream()
                .map(lang -> lang.toLowerCase(Locale.ROOT))
                .collect(Collectors.toMap(
                        lang -> lang,
                        bookRepository::countByLanguageIgnoreCase,
                        (first, ignored) -> first));
    }
}
