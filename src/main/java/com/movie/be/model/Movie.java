package com.movie.be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Movie")
public class Movie {
    @Id
    private String id;
    private String name;
    private int rate;
    private String description;
    private String imgURL;
    private Date createdAt;
    private Date updatedAt;




}
