package oracle.alura.literaturachallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexBookDto {
    private Integer id;
    private String title;
    private List<GutendexAuthorDto> authors = new ArrayList<>();
    private List<String> summaries = new ArrayList<>();
    private List<String> editors = new ArrayList<>();
    private List<GutendexAuthorDto> translators = new ArrayList<>();
    private List<String> subjects = new ArrayList<>();
    private List<String> bookshelves = new ArrayList<>();
    private List<String> languages = new ArrayList<>();
    private Boolean copyright;
    @JsonAlias("media_type")
    private String mediaType;
    private Map<String, String> formats;
    @JsonAlias("download_count")
    private Integer downloadCount;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<GutendexAuthorDto> getAuthors() { return authors; }
    public void setAuthors(List<GutendexAuthorDto> authors) { this.authors = authors == null ? new ArrayList<>() : authors; }
    public List<String> getSummaries() { return summaries; }
    public void setSummaries(List<String> summaries) { this.summaries = summaries == null ? new ArrayList<>() : summaries; }
    public List<String> getEditors() { return editors; }
    public void setEditors(List<String> editors) { this.editors = editors == null ? new ArrayList<>() : editors; }
    public List<GutendexAuthorDto> getTranslators() { return translators; }
    public void setTranslators(List<GutendexAuthorDto> translators) { this.translators = translators == null ? new ArrayList<>() : translators; }
    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects == null ? new ArrayList<>() : subjects; }
    public List<String> getBookshelves() { return bookshelves; }
    public void setBookshelves(List<String> bookshelves) { this.bookshelves = bookshelves == null ? new ArrayList<>() : bookshelves; }
    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages == null ? new ArrayList<>() : languages; }
    public Boolean getCopyright() { return copyright; }
    public void setCopyright(Boolean copyright) { this.copyright = copyright; }
    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    public Map<String, String> getFormats() { return formats; }
    public void setFormats(Map<String, String> formats) { this.formats = formats; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    @Override
    public String toString() {
        return "GutendexBookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", summaries=" + summaries +
                ", editors=" + editors +
                ", translators=" + translators +
                ", subjects=" + subjects +
                ", bookshelves=" + bookshelves +
                ", languages=" + languages +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", formats=" + formats +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
