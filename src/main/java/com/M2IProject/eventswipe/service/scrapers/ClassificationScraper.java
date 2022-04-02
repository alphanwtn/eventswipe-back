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

/**
 * Objet permettant la récupération de tous les genre, segments et subgenre de
 * TicketMaster et de les stocker sous forme d'objets utilisables par Java.
 * 
 * @author Julien Bessac
 *
 */

@Service
public class ClassificationScraper {

	@Autowired
	private com.M2IProject.eventswipe.repository.SegmentEntityRepository segmentEntityRepository;
	@Autowired
	private com.M2IProject.eventswipe.repository.GenreEntityRepository genreEntityRepository;
	@Autowired
	private com.M2IProject.eventswipe.repository.SubGenreEntityRepository subGenreEntityRepository;

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
	 * G�n�re le string correspondant � la requ�te � envoyer � TicketMaster
	 * 
	 * @param beginInts correspond � l'attribut startDateTime de la requete
	 * @param endInts   correspond � l'attribut endDateTime de la requete
	 * @param page      correspond � la page qu'on veut afficher apr�s la requ�te
	 * @return renvoie un string de la requ�te
	 */
	private String requestBuilder() {

		StringBuilder customRequest = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications?");
		customRequest.append("apikey=" + CONSUMER_K);
		customRequest.append("&locale=" + languageCode);

		return customRequest.toString();
	}

	/**
	 * R�cup�re les donn�es issue du JSON de la requ�te et les stocke dans les
	 * listes d'objets qui sont en param�tre de l'instance
	 * 
	 * @param jsonBodyResponse
	 * @return renvoie le nombre d'�v�nements contenus dans le JSON
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
	 * Ajoute � la BDD toutes les listes d'objets stock� en params de l'instance,
	 * flush � la fin.
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
	 * Lance le scraping en fonction des param�tres de l'instance
	 * 
	 * @throws IOException          Exception li�e � la requ�te http
	 * @throws InterruptedException Exception li�e � la requ�te http
	 */
	public void run() throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		String jsonBodyResponse = "";
		String stringRequest = "";

		System.err.println("Scanning all segments, genres and subgenres");

		// Creation URL API
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

		//// Convert JSON to objects and add them in instance attributs arrays
		storeDataFromJSON(jsonBodyResponse);

		// Ecrit les sets dans la DB dont la params sont dans le sessionFactory
		setsToDatabase();

		System.out.println("Every segments, genres and subgenres scrapped !");
		System.out.println("-------------------------------");

	}
}
