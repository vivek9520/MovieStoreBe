package com.movie.be.controller;

import com.movie.be.model.Movie;
import com.movie.be.repository.MovieRepository;
import com.movie.be.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/movie")
    public ResponseEntity<?> getAllMovies(){

        List<Movie> movies = movieRepository.findAll();
        if(movies.size()>0){
            return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Movies Not Available ",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/movie")
    public ResponseEntity<?> createMovie(@RequestBody Movie movie){
        try {
//            jwtUtils.ValidateToken("eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2Mzc0MzE5NzQsImVtYWlsIjoidml2ZWtAZ21haWwuY29tIn0.1x_r3mPPZsyALYBvA4mYKWXclfrnG3oRdCPAVLRuWrGvRnyF5at1XFkQc-QaYkdD04Tfcgn9awUWRVqfpcdB0Q");
            movie.setCreatedAt(new Date(System.currentTimeMillis()));
            movieRepository.save(movie);
            return new ResponseEntity<>(movie,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getSingleMovie(@PathVariable("id") String id){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if(optionalMovie.isPresent()){
            return new ResponseEntity<>(optionalMovie.get(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Movies not found with id:"+id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") String id,@RequestBody Movie movie){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if(optionalMovie.isPresent()){
            Movie saveMove = optionalMovie.get();
            saveMove.setName(movie.getName() != null ? movie.getName():saveMove.getName());
            saveMove.setDescription(movie.getDescription() != null ? movie.getDescription() : saveMove.getDescription());
            saveMove.setRate( String.valueOf(movie.getRate()) != null ? movie.getRate():saveMove.getRate());
            saveMove.setUpdatedAt(new Date(System.currentTimeMillis()));
            movieRepository.save(saveMove);
            return new ResponseEntity<>(saveMove,HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>("Movies not found with id:"+id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") String id){
        try {
            movieRepository.deleteById(id);
            return new ResponseEntity<>("Successfully Deleted with ID :"+id,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
