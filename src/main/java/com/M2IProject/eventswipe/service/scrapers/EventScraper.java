package com.M2IProject.eventswipe.service.scrapers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.AttractionEntity;
import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.model.ImageEntity;
import com.M2IProject.eventswipe.model.SegmentEntity;
import com.M2IProject.eventswipe.model.SubGenreEntity;
import com.M2IProject.eventswipe.model.VenueEntity;
import com.M2IProject.eventswipe.utils.NwtnParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Objet permettant la récup�ration de données d'évènements de l'API
 * TicketMaster et de les stocker sous forme d'objets utilisables par Java.
 * Demarre scrape à la date du jour
 * 
 *
 */
@Service
public class EventScraper {

	@Autowired
	private com.M2IProject.eventswipe.repository.SegmentEntityRepository segmentEntityRepository;
	@Autowired
	private com.M2IProject.eventswipe.repository.GenreEntityRepository genreEntityRepository;
	@Autowired
	private com.M2IProject.eventswipe.repository.SubGenreEntityRepository subGenreEntityRepository;
	@Autowired
	private com.M2IProject.eventswipe.repository.EventEntityRepository eventEntityRepository;

	@Value("${scraper.tmkey}")
	private String CONSUMER_K;

	@Value("${scraper.language}")
	private String languageCode;

	@Value("${scraper.country}")
	private String countryCode;

	@Value("${scraper.daystoscrape}")
	private int daysToScrape;

	// INTERNAL CONFIG
	private int MAX_PAGE_LIMIT = 5;
	private int MAX_SIZE_LIMIT = 200;
	private int scrapeDayRange = 1; // By default

	// INTERNAL RUNNING VARIABLES
	private Calendar beginScrapeDateCalendar = Calendar.getInstance();
	private Calendar endScrapeDateCalendar = Calendar.getInstance();
	private Date startScrappingDate;
	private Date endScrappingDate;

	// INSTANCE STORAGE

	private Set<EventEntity> listeEvents = new HashSet<>();

	public EventScraper() {
		Date todaydate = new Date();
		this.beginScrapeDateCalendar.setTime(todaydate);
		this.endScrapeDateCalendar.setTime(todaydate);
		this.endScrapeDateCalendar.add(Calendar.DATE, scrapeDayRange);

		System.out.println(beginScrapeDateCalendar);
		System.out.println(endScrapeDateCalendar);
	}

	/**
	 * Transforme un calendrier en une table de 3 strings representant les numeros
	 * associés à la date
	 * 
	 * @param dateCalendar est le calendirer � transformer
	 * @return et les tableau de string associ� � la date � renvoyer
	 */
	private String[] calendarToIntstrings(Calendar dateCalendar) {

		int monthNumber = dateCalendar.get(Calendar.MONTH) + 1; // because january is 0

		String ye = String.valueOf(dateCalendar.get(Calendar.YEAR));
		String mo = monthNumber < 10 ? "0" + String.valueOf(monthNumber) : String.valueOf(monthNumber);
		String da = dateCalendar.get(Calendar.DATE) < 10 ? "0" + String.valueOf(dateCalendar.get(Calendar.DATE))
				: String.valueOf(dateCalendar.get(Calendar.DATE));
		String[] ints = { ye, mo, da }; // +1 car janvier vaut 0
		return ints;
	}

	/**
	 * G�n�re le string correspondant � la requ�te � envoyer � TicketMaster
	 * 
	 * @param beginInts correspond � l'attribut startDateTime de la requete
	 * @param endInts   correspond � l'attribut endDateTime de la requete
	 * @param page      correspond � la page qu'on veut afficher apr�s la requ�te
	 * @return renvoie un string de la requ�te
	 */
	private String requestBuilder(String[] beginInts, String[] endInts, int page) {

		StringBuilder customRequest = new StringBuilder("https://app.ticketmaster.com/discovery/v2/events?");
		customRequest.append("apikey=" + CONSUMER_K);
		customRequest.append("&countryCode=" + countryCode);
		customRequest.append("&locale=" + languageCode);
		customRequest.append("&size=" + MAX_SIZE_LIMIT);
		customRequest.append("&sort=id,asc");
		customRequest.append("&page=" + page);
		customRequest.append("&startDateTime=" + beginInts[0] + "-" + beginInts[1] + "-" + beginInts[2] + "T00:00:00Z");
		customRequest.append("&endDateTime=" + endInts[0] + "-" + endInts[1] + "-" + endInts[2] + "T00:00:00Z");

		return customRequest.toString();

	}

	/**
	 * R�cup�re les donn�es issue du JSON de la requ�te et les stocke dans les
	 * listes d'objets qui sont en paramètre de l'instance
	 * 
	 * @param jsonBodyResponse
	 * @return renvoie le nombre d'�v�nements contenus dans le JSON
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private int storeDataFromJSON(String jsonBodyResponse) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jnode = mapper.readTree(jsonBodyResponse);

		for (JsonNode eventInArray : jnode.at("/_embedded/events")) {
			SegmentEntity segmentEvent = new SegmentEntity();
			GenreEntity genreEvent = new GenreEntity();
			SubGenreEntity subGenreEvent = new SubGenreEntity();
			VenueEntity venueEvent = new VenueEntity();
			EventEntity eventEvent = new EventEntity();
			List<AttractionEntity> listeAttractionsEvent = new ArrayList<>();
			List<ImageEntity> listeImagesEvent = new ArrayList<>();

			/// EVENT PART
			String stringDate = "";

			eventEvent.setId(eventInArray.get("id").asText());

			if (eventInArray.get("name") != null)
				eventEvent.setName(eventInArray.get("name").asText());

			if (eventInArray.get("description") != null)
				eventEvent.setDescription(eventInArray.get("description").asText());

			if (eventInArray.get("url") != null)
				eventEvent.setUrl(eventInArray.get("url").asText());

			if (eventInArray.at("/dates/start").get("dateTime") != null) {
				stringDate = eventInArray.at("/dates/start").get("dateTime").asText();
				eventEvent.setStart_date_event(beginScrapeDateCalendar);
			}

			if (eventInArray.at("/sales/public").get("startDateTime") != null) {
				stringDate = eventInArray.at("/sales/public").get("startDateTime").asText();
				eventEvent.setStart_date_sale(beginScrapeDateCalendar);
			}

			if (eventInArray.at("/sales/public").get("endDateTime") != null) {
				stringDate = eventInArray.at("/sales/public").get("endDateTime").asText();
				eventEvent.setEnd_date_sale(NwtnParser.rawStringDateToCalendar(stringDate));
			}

			// SEGMENT, GENRE, SUBGENRE (pour chaque, get l'id du json mais affecte depuis
			// la dbb)
			if (eventInArray.at("/classifications").size() > 1)
				throw new RuntimeException("Evenement dont les classifications sont > 1 !");

			for (JsonNode classificationInArray : eventInArray.at("/classifications")) { // Accède à l'attribut

				segmentEvent = segmentEntityRepository
						.findById(classificationInArray.at("/segment").get("id").asText());

				genreEvent = genreEntityRepository.findById(classificationInArray.at("/genre").get("id").asText());

				subGenreEvent = subGenreEntityRepository
						.findById(classificationInArray.at("/subGenre").get("id").asText());
			}

			// VENUES
			if (eventInArray.at("/_embedded/venues").size() > 1)
				throw new RuntimeException("Evenement dont les venues sont > 1 !");

			for (JsonNode venueInArray : eventInArray.at("/_embedded/venues")) {

				if (venueInArray.get("id") != null)
					venueEvent.setId(venueInArray.get("id").asText());

				if (venueInArray.get("name") != null)
					venueEvent.setName(venueInArray.get("name").asText());

				if (venueInArray.get("postalCode") != null)
					venueEvent.setPostal_code(venueInArray.get("postalCode").asText());

				if (venueInArray.get("url") != null)
					venueEvent.setUrl(venueInArray.get("url").asText());

				if (venueInArray.at("/city").get("name") != null)
					venueEvent.setCity_name(venueInArray.at("/city").get("name").asText());

				if (venueInArray.at("/address").get("line1") != null)
					venueEvent.setAddress_line(venueInArray.at("/address").get("line1").asText());

				if (venueInArray.at("/location").get("longitude") != null)
					venueEvent.setGps_longitude(venueInArray.at("/location").get("longitude").asText());

				if (venueInArray.at("/location").get("latitude") != null)
					venueEvent.setGps_latitude(venueInArray.at("/location").get("latitude").asText());
			}

			// ATTRACTIONS

			for (JsonNode attractionInArray : eventInArray.at("/_embedded/attractions")) {
				AttractionEntity attraction = new AttractionEntity();

				attraction.setId(attractionInArray.get("id").asText());

				if (attractionInArray.get("name") != null)
					attraction.setName(attractionInArray.get("name").asText());

				if (attractionInArray.get("url") != null)
					attraction.setUrl(attractionInArray.get("url").asText());

				// On fait un for alors qu'on sait qu'il n'y a qu'une classif par attraction, a
				// corriger
				if (attractionInArray.at("/classifications").size() > 1)
					throw new RuntimeException("Attraction dont les classifs sont > 1 !");

				for (JsonNode classificationInArray : attractionInArray.at("/classifications")) {

					SegmentEntity segment = new SegmentEntity();
					GenreEntity genre = new GenreEntity();
					SubGenreEntity subgenre = new SubGenreEntity();

					if (classificationInArray.at("/segment").get("name") != null) {
						segment = segmentEntityRepository
								.findById(classificationInArray.at("/segment").get("id").asText());
					} else {
						segment = null;
					}

					if (classificationInArray.at("/genre").get("name") != null) {
						genre = genreEntityRepository.findById(classificationInArray.at("/genre").get("id").asText());
					} else {
						genre = null;
					}

					if (classificationInArray.at("/subGenre").get("name") != null) {
						subgenre = subGenreEntityRepository
								.findById(classificationInArray.at("/subGenre").get("id").asText());
					} else {
						subgenre = null;
					}

					attraction.setSegment(segment);
					attraction.setGenre(genre);
					attraction.setSubgenre(subgenre);
				}

				listeAttractionsEvent.add(attraction);
			}

			/// IMAGES PART

			for (JsonNode imagesInArray : eventInArray.at("/images")) {
				ImageEntity image = new ImageEntity();

				image.setUrl(imagesInArray.get("url").asText());
				image.setRatio(imagesInArray.get("ratio").asText());
				image.setWidth(imagesInArray.get("width").asInt());
				image.setHeight(imagesInArray.get("height").asInt());

				listeImagesEvent.add(image);
			}

			eventEvent.setSegment(segmentEvent);
			eventEvent.setGenre(genreEvent);
			eventEvent.setSubgenre(subGenreEvent);
			eventEvent.setVenue(venueEvent);
			eventEvent.setAttractionsinevent(listeAttractionsEvent);
			eventEvent.setImagesinevent(listeImagesEvent);

			listeEvents.add(eventEvent);

		}

		return jnode.at("/_embedded/events").size();
	}

	/**
	 * Ajoute � la BDD toutes les listes d'objets stock� en params de l'instance,
	 * flush � la fin.
	 */
	private void setsToDatabase() {

		listeEvents.forEach(g -> eventEntityRepository.save(g));
		listeEvents.clear();
	}

	/**
	 * Affiche le temps lors du demarrage du scrape
	 */
	private void displayScrapeStartTime() {
		startScrappingDate = new Date();
		System.err.println("START. Starting at : " + startScrappingDate);
	}

	/**
	 * Affiche de temps de scrape � la fin de l'execution
	 */
	private void displayScrapeEllapsedTime() {
		endScrappingDate = new Date();
		long millis = endScrappingDate.getTime() - startScrappingDate.getTime();

		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

		System.err.println("END. Total scrapping time : " + hms);
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
		String[] beginInts = new String[3];
		String[] endInts = new String[3];
		String stringRequest = "";

		displayScrapeStartTime();

		// For Each Day
		for (int deltaDay = 0; deltaDay < daysToScrape; deltaDay++) {

			beginInts = calendarToIntstrings(beginScrapeDateCalendar);
			endInts = calendarToIntstrings(endScrapeDateCalendar);

			System.err.println("Scanning events from " + beginInts[0] + "-" + beginInts[1] + "-" + beginInts[2] + " to "
					+ endInts[0] + "-" + endInts[1] + "-" + endInts[2]);

			// For Each Page
			for (int page = 0; page < MAX_PAGE_LIMIT; page++) {

				// Creation URL API
				stringRequest = requestBuilder(beginInts, endInts, page);
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(stringRequest)).GET().build();
				System.out.println(stringRequest);

				try {
					HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
					jsonBodyResponse = response.body();
					System.out.println("Status  : " + response.statusCode());
					Thread.sleep(1000);

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

				//// Convert JSON to objects and add them in instance attributs arrays
				int eventNumberInPage = storeDataFromJSON(jsonBodyResponse);

				// Ecrit les sets dans la DB dont la params sont dans le sessionFactory
				setsToDatabase();

				System.out.println("Events recensés " + eventNumberInPage + ". Page=" + page);
				System.out.println("-------------------------------");

				/// TESTS
				if (page == 4 && eventNumberInPage == 200)
					System.err.println("Quantité d'events > 1000 pour ces paramètres, certains seront omis");
				else if (eventNumberInPage < 200) {
					System.out.println("No more event this day, scanning next day");
					break;
				}
			}
			// On passe à la journée suivante
			beginScrapeDateCalendar.add(Calendar.DATE, 1);
			endScrapeDateCalendar.add(Calendar.DATE, 1);
		}
		displayScrapeEllapsedTime();
	}

}
