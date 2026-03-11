package oracle.alura.literaturachallenge.view;

import oracle.alura.literaturachallenge.exception.BusinessException;
import oracle.alura.literaturachallenge.model.Author;
import oracle.alura.literaturachallenge.model.Book;
import oracle.alura.literaturachallenge.service.LibraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsoleMenu {

    private final LibraryService libraryService;
    private final List<String> defaultLanguages;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(LibraryService libraryService,
                       @Value("${app.stats.languages:es,en,pt}") String languagesProperty) {
        this.libraryService = libraryService;
        var parsed = Arrays.stream(languagesProperty.split(","))
                .map(String::trim)
                .filter(code -> !code.isBlank())
                .map(code -> code.toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
        this.defaultLanguages = parsed.isEmpty() ? List.of("es", "en") : List.copyOf(parsed);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String option = scanner.nextLine();
            try {
                running = handleOption(option);
            } catch (BusinessException ex) {
                System.out.println("⚠️  " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("⚠️  Error inesperado: " + ex.getMessage());
            }
        }
        System.out.println("¡Hasta pronto!");
    }

    private boolean handleOption(String option) {
        return switch (option) {
            case "1" -> {
                handleSearchBook();
                yield true;
            }
            case "2" -> {
                handleListBooks();
                yield true;
            }
            case "3" -> {
                handleListAuthors();
                yield true;
            }
            case "4" -> {
                handleAuthorsAlive();
                yield true;
            }
            case "5" -> {
                handleBooksByLanguage();
                yield true;
            }
            case "6" -> {
                handleLanguageStats();
                yield true;
            }
            case "0" -> false;
            default -> {
                System.out.println("Opción inválida, intenta nuevamente.");
                yield true;
            }
        };
    }

    private void handleSearchBook() {
        System.out.print("Ingresa el título del libro: ");
        String title = scanner.nextLine();
        Book book = libraryService.searchAndStoreBookByTitle(title);
        System.out.println("Libro almacenado o recuperado:");
        System.out.println(formatBook(book));
    }

    private void handleListBooks() {
        List<Book> books = libraryService.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("Aún no hay libros registrados.");
            return;
        }
        books.stream()
                .map(this::formatBook)
                .forEach(System.out::println);
    }

    private void handleListAuthors() {
        List<Author> authors = libraryService.listAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados todavía.");
            return;
        }
        authors.stream()
                .map(this::formatAuthor)
                .forEach(System.out::println);
    }

    private void handleAuthorsAlive() {
        System.out.print("Ingresa el año para la consulta: ");
        int year = readInt();
        List<Author> authors = libraryService.listAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }
        authors.stream()
                .map(this::formatAuthor)
                .forEach(System.out::println);
    }

    private void handleBooksByLanguage() {
        System.out.print("Ingresa el código de idioma (por ejemplo es, en, pt): ");
        String language = scanner.nextLine();
        List<Book> books = libraryService.listBooksByLanguage(language);
        if (books.isEmpty()) {
            System.out.println("No hay libros almacenados para ese idioma.");
            return;
        }
        books.stream()
                .map(this::formatBook)
                .forEach(System.out::println);
    }

    private void handleLanguageStats() {
        System.out.print("Idiomas a consultar (separados por coma, dejar vacío para usar " + defaultLanguages + "): ");
        String raw = scanner.nextLine();
        List<String> languages = raw.isBlank()
                ? defaultLanguages
                : Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(code -> !code.isBlank())
                .map(code -> code.toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
        Map<String, Long> stats = libraryService.countBooksByLanguages(languages);
        stats.forEach((lang, total) -> System.out.printf(Locale.ROOT, "Idioma %s -> %d libros%n", lang, total));
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.print("Valor inválido, intenta con un número entero: ");
            }
        }
    }

    private String formatBook(Book book) {
        return String.format(Locale.ROOT, "- %s | Autor: %s | Idioma: %s | Descargas: %d",
                book.getTitle(),
                book.getAuthor() != null ? book.getAuthor().getName() : "?",
                book.getLanguage(),
                book.getDownloadCount());
    }

    private String formatAuthor(Author author) {
        String lifeSpan = (author.getBirthYear() == null ? "?" : author.getBirthYear()) + " - " +
                (author.getDeathYear() == null ? "?" : author.getDeathYear());
        return String.format(Locale.ROOT, "- %s (%s)", author.getName(), lifeSpan);
    }

    private void printMenu() {
        System.out.println();
        System.out.println("================= Catálogo de Literatura =================");
        System.out.println("1) Buscar libro por título");
        System.out.println("2) Listar todos los libros");
        System.out.println("3) Listar autores registrados");
        System.out.println("4) Listar autores vivos en un año");
        System.out.println("5) Listar libros por idioma");
        System.out.println("6) Estadísticas por idioma");
        System.out.println("0) Salir");
        System.out.print("Selecciona una opción: ");
    }
}
