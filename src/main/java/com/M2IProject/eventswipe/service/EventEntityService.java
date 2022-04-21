package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.model.UsersEventListEntity;
import com.M2IProject.eventswipe.model.UsersGenreListEntity;
import com.M2IProject.eventswipe.repository.EventEntityRepository;
import com.M2IProject.eventswipe.repository.UserEntityRepository;
import com.M2IProject.eventswipe.repository.UsersEventListEntityRepository;
import com.M2IProject.eventswipe.repository.UsersGenreListEntityRepository;
import com.M2IProject.eventswipe.utils.DistanceCalculator;

@Service
public class EventEntityService {

    @Autowired
    private EventEntityRepository eventEntityRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private UsersGenreListEntityRepository usersGenreListEntityRepository;
    @Autowired
    private UsersEventListEntityRepository usersEventListEntityRepository;

    public Optional<EventEntity> getAnEventById(String id) {
	Optional<EventEntity> event = eventEntityRepository.findById(id);
	return event;
    }

    public List<EventEntity> getAllEventsByGenreName(List<String> searchedGenres) {

	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenres) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findByGenreName(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}

	Collections.shuffle(eventsList);

	if (eventsList.size() > 30)
	    return eventsList.subList(0, 30);
	// limitation du nombre d'event à  30 pour rapidité du test

	return eventsList;
    }

    public List<EventEntity> getAllEventsByGenreId(List<String> searchedGenresId) {

	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenresId) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findAllByGenreId(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}

	Collections.shuffle(eventsList);

	if (eventsList.size() > 30)
	    return eventsList.subList(0, 30);
	// limitation du nombre d'event à  30 pour rapidité du test

	return eventsList;
    }

    /**
     * Cette Méthode fonctionne en 5 étapes. Elle part d'une liste d'événements
     * constituée sur la base des genres selectionnés par le user, puis, les filtres
     * selon différents critères comme la distance avec le user, s'ils sont déjà
     * terminés ou si l'utilisateur les a déjà vus. Elle retourne ainsi une liste de
     * 30 événements ou moins, inédite pour l'utilisateur et qui correspond aux
     * goûts qu'il a exprimés
     * 
     * @param userid
     * @return liste d'événements soumise aux swipes de l'utilisateur
     */
    public List<EventEntity> getUserEventsPull(int userid) {
	// first step: récupérer les genres selectionnés par le user dans sa
	// usersGenreList et constituer ainsi la liste des genres recherchés :String:

	List<String> searchedGenresId = new ArrayList<String>();
	List<UsersGenreListEntity> userGenresId = usersGenreListEntityRepository.findAllByUserId(userid);

	userGenresId.forEach(x -> searchedGenresId.add(x.getGenre().getId()));

	// second step: constituer la liste des événements qui sera soumise aux autres
	// filtres, en utilisant la liste des genres recherchés par le user constituée
	// plus haut

	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenresId) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findAllByGenreId(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}
	// third step: réduire le nombre d'événements proposés en vérifiant qu'ils
	// sont bien dans le radius sollicité par le user

	List<EventEntity> eventsSubmitToUser = new ArrayList<>();
	UserEntity user = userEntityRepository.findById(userid).get();
	Integer userRadius = user.getSearchRadiusKm();

	for (EventEntity e : eventsList) {
	    double lat1 = Double.valueOf(user.getGps_latitude());
	    double lon1 = Double.valueOf(user.getGps_longitude());

	    // si nous n'avons pas les coordonnées de l'événement celui-ci est ignoré
	    // d'office)
	    if (e.getVenue().getGps_latitude() == null || e.getVenue().getGps_longitude() == null) {
		continue;
	    }
	    // si l'événement a bien des coordonnées on calcule la distance entre
	    // l'événement et le user et la compare au radius de recherche qu'a choisi le
	    // user
	    double lat2 = Double.valueOf(e.getVenue().getGps_latitude());
	    double lon2 = Double.valueOf(e.getVenue().getGps_longitude());
	    if (DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K") <= userRadius) {
		eventsSubmitToUser.add(e);
	    }
	}

	// fourth step: supprimer les events du pull soumis à  l'utilisateur s'ils sont
	// déjà  terminés (if start_date_event is before today)

	List<EventEntity> eventsSubmitToUser2 = new ArrayList<>();
	for (EventEntity event : eventsSubmitToUser) {
	    Calendar today = Calendar.getInstance();
	    if (event.getStart_date_event().after(today)) {
		eventsSubmitToUser2.add(event);

	    }
	}

	// fifth step omettre l'évenement si celui ci a déjà  était présenté au
	// user par le passé (si déjà  présent dans user eventlist sous n'importe
	// quel statut donc)
	List<UsersEventListEntity> userEventListe = usersEventListEntityRepository.findAllByUserId(userid);

	// il faut tenir compte du cas d'un nouveau user qui n'a jamais intéragi avec un
	// événement et qui a donc une userEventList vide
	List<EventEntity> liste = new ArrayList<>();
	userEventListe.forEach(x -> liste.add(x.getEvent()));

	if (userEventListe.size() != 0) {
	    for (EventEntity x : liste) {
		if (eventsSubmitToUser2.contains(x)) {
		    eventsSubmitToUser2.remove(x);
		}
	    }
	    Collections.shuffle(eventsSubmitToUser2);

	    if (eventsSubmitToUser2.size() > 30) {
		return eventsSubmitToUser2.subList(0, 30);
	    }
	    return eventsSubmitToUser2;
	} else {
	    Collections.shuffle(eventsSubmitToUser2);
	    if (eventsSubmitToUser2.size() > 30) {
		return eventsSubmitToUser2.subList(0, 30);
	    }
	    return eventsSubmitToUser2;
	}
    }
}