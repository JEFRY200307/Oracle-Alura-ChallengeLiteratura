package oracle.alura.literaturachallenge.service;

import oracle.alura.literaturachallenge.client.GutendexClient;
import oracle.alura.literaturachallenge.dto.GutendexBookDto;
import oracle.alura.literaturachallenge.dto.GutendexSearchResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GutendexService {

    private final GutendexClient gutendexClient;

    public GutendexService(GutendexClient gutendexClient) {
        this.gutendexClient = gutendexClient;
    }

    public Optional<GutendexBookDto> fetchFirstMatch(String title) {
        GutendexSearchResponse response = gutendexClient.searchBooksByTitle(title);
        return response.getResults().stream().findFirst();
    }
}

