package oracle.alura.literaturachallenge.service;

import oracle.alura.literaturachallenge.dto.GutendexAuthorDto;
import oracle.alura.literaturachallenge.dto.GutendexBookDto;
import oracle.alura.literaturachallenge.exception.BusinessException;
import oracle.alura.literaturachallenge.model.Author;
import oracle.alura.literaturachallenge.model.Book;
import oracle.alura.literaturachallenge.repository.AuthorRepository;
import oracle.alura.literaturachallenge.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GutendexService gutendexService;

    @InjectMocks
    private LibraryService libraryService;

    private GutendexBookDto sampleDto;

    @BeforeEach
    void setUp() {
        GutendexAuthorDto authorDto = new GutendexAuthorDto();
        authorDto.setName("Autor API");
        authorDto.setBirthYear(1900);
        authorDto.setDeathYear(1980);

        sampleDto = new GutendexBookDto();
        sampleDto.setTitle("Libro API");
        sampleDto.setLanguages(List.of("es"));
        sampleDto.setDownloadCount(100);
        sampleDto.setAuthors(List.of(authorDto));
    }

    @Test
    void returnsExistingBookWhenAlreadyStored() {
        Book stored = new Book("Titulo", "es", 12);
        when(bookRepository.findFirstByTitleIgnoreCase("Titulo")).thenReturn(Optional.of(stored));

        Book result = libraryService.searchAndStoreBookByTitle("Titulo");

        assertThat(result).isSameAs(stored);
        verify(gutendexService, never()).fetchFirstMatch(any());
    }

    @Test
    void importsBookFromApiWhenNotFound() {
        when(bookRepository.findFirstByTitleIgnoreCase("Libro API")).thenReturn(Optional.empty());
        when(gutendexService.fetchFirstMatch("Libro API")).thenReturn(Optional.of(sampleDto));
        when(authorRepository.findByNameIgnoreCase("Autor API")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(1L);
            return book;
        });

        Book result = libraryService.searchAndStoreBookByTitle("  Libro API  ");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAuthor()).isNotNull();
        assertThat(result.getLanguage()).isEqualTo("es");
        ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(authorCaptor.capture());
        assertThat(authorCaptor.getValue().getName()).isEqualTo("Autor API");
    }

    @Test
    void countBooksByLanguageNormalizesCodes() {
        when(bookRepository.countByLanguageIgnoreCase("es")).thenReturn(2L);
        Map<String, Long> result = libraryService.countBooksByLanguages(List.of(" ES "));
        assertThat(result).containsEntry("es", 2L);
    }

    @Test
    void failsWhenLanguageListIsEmpty() {
        assertThatThrownBy(() -> libraryService.countBooksByLanguages(List.of()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("idioma");
    }
}

