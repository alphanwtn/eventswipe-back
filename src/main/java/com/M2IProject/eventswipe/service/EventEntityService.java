package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.model.UsersGenreListEntity;
import com.M2IProject.eventswipe.repository.EventEntityRepository;
import com.M2IProject.eventswipe.repository.UserEntityRepository;
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

    public List<EventEntity> getAllEventsUnderRadius(int userid) {
	// first step récupérer les genres selectionnés par le user dans sa
	// usersGenreList et constituer ainsi la liste des genres recherchés

	List<String> searchedGenresId = new ArrayList<String>();
	List<UsersGenreListEntity> userGenresId = usersGenreListEntityRepository.findAllByUserId(userid);

	userGenresId.forEach(x -> searchedGenresId.add(x.getGenre().getId()));

	// second step constituer la liste des événements proposés, basée sur la liste
	// des genres recherchés
	List<EventEntity> eventsList = new ArrayList<>();

	for (String g : searchedGenresId) {
	    Iterable<EventEntity> resultByGenre = eventEntityRepository.findAllByGenreId(g);
	    resultByGenre.forEach(x -> eventsList.add(x));
	}
	System.out.println("***" + eventsList);
	// third step réduire le nombre d'événements proposés en vérifiant qu'ils sont
	// dans le radius recherché par le user

	List<EventEntity> eventsUnderRadius = new ArrayList<>();
	UserEntity user = userEntityRepository.findById(userid).get();
	Integer userRadius = user.getSearchRadiusKm();

	for (EventEntity e : eventsList) {
	    double lat1 = Double.valueOf(user.getGps_latitude());
	    double lon1 = Double.valueOf(user.getGps_longitude());
	    double lat2 = Double.valueOf(e.getVenue().getGps_latitude());
	    double lon2 = Double.valueOf(e.getVenue().getGps_longitude());
	    if (e.getVenue().getGps_latitude() == null || e.getVenue().getGps_longitude() == null) {
		eventsUnderRadius.add(e);
	    } else if (DistanceCalculator.distance(lat1, lon1, lat2, lon2, "K") <= userRadius) {
		eventsUnderRadius.add(e);
	    } else {

	    }
	    Collections.shuffle(eventsUnderRadius);

	    if (eventsUnderRadius.size() > 30)
		return eventsUnderRadius.subList(0, 30);
	}
	return eventsUnderRadius;
    }
}