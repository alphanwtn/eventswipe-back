package com.M2IProject.eventswipe.service.scrapers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.model.SegmentEntity;
import com.M2IProject.eventswipe.model.SubGenreEntity;
import com.M2IProject.eventswipe.repository.GenreEntityRepository;
import com.M2IProject.eventswipe.repository.SegmentEntityRepository;
import com.M2IProject.eventswipe.repository.SubGenreEntityRepository;

/**
 * 
 * This object allows to scrape all the segments, genres and subgenres from the
 * Ticketmaster API
 *
 */
@Service
public class ClassificationScraper {

    @Autowired
    private SegmentEntityRepository segmentEntityRepository;
    @Autowired
    private GenreEntityRepository genreEntityRepository;
    @Autowired
    private SubGenreEntityRepository subGenreEntityRepository;

    // DONT TOUCH
    @Value("${scraper.tmkey}")
    private String CONSUMER_K;

    // TO TOUCH
    @Value("${scraper.language}")
    private String languageCode;

    // INSTANCE STORAGE
    private Set<SegmentEntity> listeSegments = new HashSet<>();
    private Set<GenreEntity> listeGenres = new HashSet<>();
    private Set<SubGenreEntity> listeSubGenres = new HashSet<>();

    /**
     * Build the request url from API key and language
     * 
     * @return the complete request url
     */
    private String requestBuilder() {

	StringBuilder customRequest = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications?");
	customRequest.append("apikey=" + CONSUMER_K);
	customRequest.append("&locale=" + languageCode);

	return customRequest.toString();
    }

    /**
     * Extract all segments, genres and subgenres from the JSON response
     * 
     * @param jsonBodyResponse
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void storeDataFromJSON(String jsonBodyResponse) throws JsonMappingException, JsonProcessingException {

	ObjectMapper mapper = new ObjectMapper();
	JsonNode jnode = mapper.readTree(jsonBodyResponse);

	for (JsonNode classificationInArray : jnode.at("/_embedded/classifications")) {
	    if (classificationInArray.at("/segment").get("name") != null) { // Permet de skip les trucs bizares

		SegmentEntity segment = new SegmentEntity();

		segment.setId(classificationInArray.at("/segment").get("id").asText());
		segment.setName(classificationInArray.at("/segment").get("name").asText());

		listeSegments.add(segment);

		for (JsonNode genreInArray : classificationInArray.at("/segment/_embedded/genres")) {

		    GenreEntity genre = new GenreEntity();
		    genre.setId(genreInArray.get("id").asText());
		    genre.setName(genreInArray.get("name").asText());
		    genre.setInheritedsegment(segment);

		    listeGenres.add(genre);

		    for (JsonNode subgenreInArray : genreInArray.at("/_embedded/subgenres")) {

			SubGenreEntity subgenre = new SubGenreEntity();

			if (subgenreInArray.get("name") != null) {
			    subgenre.setId(subgenreInArray.get("id").asText());
			    subgenre.setName(subgenreInArray.get("name").asText());
			    subgenre.setInheritedgenre(genre);
			} else {
			    subgenre.setId(subgenreInArray.get("id").asText());
			    subgenre.setName(null);
			    subgenre.setInheritedgenre(genre);
			}
			listeSubGenres.add(subgenre);
		    }
		}
	    }
	}
	System.out.println("Nombre total de segments : " + listeSegments.size());
	System.out.println("Nombre total de genres : " + listeGenres.size());
	System.out.println("Nombre total de subgenres : " + listeSubGenres.size());
    }

    /**
     * Put all the stored object in the database
     */
    private void setsToDatabase() {

	listeSegments.forEach(g -> segmentEntityRepository.save(g));
	listeGenres.forEach(g -> genreEntityRepository.save(g));
	listeSubGenres.forEach(g -> subGenreEntityRepository.save(g));

	listeSegments.clear();
	listeGenres.clear();
	listeSubGenres.clear();
    }

    /**
     * Run the scraper
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void run() throws IOException, InterruptedException {

	HttpClient client = HttpClient.newHttpClient();
	String jsonBodyResponse = "";
	String stringRequest = "";

	System.err.println("Scanning all segments, genres and subgenres");

	stringRequest = requestBuilder();
	HttpRequest request = HttpRequest.newBuilder().uri(URI.create(stringRequest)).GET().build();
	System.out.println(stringRequest);

	try {
	    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	    jsonBodyResponse = response.body();
	    System.out.println("Status  : " + response.statusCode());

	} catch (IOException | InterruptedException e) {
	    e.printStackTrace();
	}

	storeDataFromJSON(jsonBodyResponse);

	setsToDatabase();

	System.out.println("Every segments, genres and subgenres scrapped !");
	System.out.println("-------------------------------");
    }
}
