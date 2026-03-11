package oracle.alura.literaturachallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexSearchResponse {

    private List<GutendexBookDto> results = new ArrayList<>();

    public List<GutendexBookDto> getResults() {
        return results;
    }

    public void setResults(List<GutendexBookDto> results) {
        this.results = results == null ? new ArrayList<>() : results;
    }

    @Override
    public String toString() {
        return "GutendexSearchResponse{" +
                "results=" + results +
                '}';
    }
}

