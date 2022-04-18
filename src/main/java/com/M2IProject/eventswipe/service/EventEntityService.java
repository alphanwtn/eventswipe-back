package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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

    public List<EventEntity> getAllEventsByGenreName(List<String> searchedGenres) {

	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenres) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findByGenreName(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}

	Collections.shuffle(eventsList);

	if (eventsList.size() > 30)
	    return eventsList.subList(0, 30);
	// limitation du nombre d'event à 30 pour rapidité de test

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
	// limitation du nombre d'event à 30 pour rapidité de test

	return eventsList;
    }

    public List<EventEntity> getUserEventsPull(int userid) {
	// first step récupérer les genres selectionnés par le user dans sa
	// usersGenreList et constituer ainsi la liste des genres recherchés

	List<String> searchedGenresId = new ArrayList<String>();
	List<UsersGenreListEntity> userGenresId = usersGenreListEntityRepository.findAllByUserId(userid);

	userGenresId.forEach(x -> searchedGenresId.add(x.getGenre().getId()));

	// second step constituer la liste des événements qui sera triée, basée sur la
	// liste des genres recherchés

	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenresId) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findAllByGenreId(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}
	// third step réduire le nombre d'événements proposés en vérifiant qu'ils sont
	// dans le radius recherché par le user

	List<EventEntity> eventsSubmitToUser = new ArrayList<>();
	UserEntity user = userEntityRepository.findById(userid).get();
	Integer userRadius = user.getSearchRadiusKm();

	for (EventEntity e : eventsList) {
	    double lat1 = Double.valueOf(user.getGps_latitude());
	    double lon1 = Double.valueOf(user.getGps_longitude());

	    if (e.getVenue().getGps_latitude() == null || e.getVenue().getGps_longitude() == null) {
		continue;
	    }
	    double lat2 = Double.valueOf(e.getVenue().getGps_latitude());
	    double lon2 = Double.valueOf(e.getVenue().getGps_longitude());
	    if (DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K") <= userRadius) {
		eventsSubmitToUser.add(e);
	    }
	}

	// fourth step supprimer les events du pull soumis à l'utilisateur s'ils sont
	// déjà terminés (si la start_date_event est before today)
	List<EventEntity> eventsSubmitToUser2 = new ArrayList<>();
	for (EventEntity event : eventsSubmitToUser) {
	    Calendar today = Calendar.getInstance();
	    if (event.getStart_date_event().after(today)) {
		eventsSubmitToUser2.add(event);
	    }
	}

	// fifth step omettre l'évenement si celui ci à déjà était présenté au user par
	// le passé ( si déjà présent dans user eventlist sous n'importe quel statut)
	List<EventEntity> eventsSubmitToUser3 = new ArrayList<>();
	List<UsersEventListEntity> userEventListe = usersEventListEntityRepository.findAllByUserId(userid);
	for (EventEntity e : eventsSubmitToUser2) {

	    for (UsersEventListEntity x : userEventListe) {
		if (e.getId() == x.getEvent().getId()) {
		    continue;
		} else {
		    eventsSubmitToUser3.add(e);
		}
		if (eventsSubmitToUser3.size() == 0) {
		    return eventsSubmitToUser3;
		}
	    }
	}
	System.out.println(eventsSubmitToUser2.size() + "<<<<<<<");
	System.out.println(userEventListe.size() + "<<<<<<<<");
	Collections.shuffle(eventsSubmitToUser3);

	if (eventsSubmitToUser3.size() > 30)
	    return eventsSubmitToUser3.subList(0, 30);

	return eventsSubmitToUser3;
    }
}