package com.M2IProject.eventswipe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "venues")
public class VenueEntity {
    @Id
    private String id;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String url;

    @Column(length = 10)
    private String postal_code;

    @Column(length = 30)
    private String city_name;

    @Column(columnDefinition = "TEXT")
    private String address_line;

    @Column(length = 30)
    private String gps_latitude;

    @Column(length = 30)
    private String gps_longitude;
}
