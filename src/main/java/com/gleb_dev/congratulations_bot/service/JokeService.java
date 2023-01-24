package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.entity.Joke;
import com.gleb_dev.congratulations_bot.exception.JokeNotFoundException;
import com.gleb_dev.congratulations_bot.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Class implements main logic of joke's entity
 */
@Service
public class JokeService {

    private JokeRepository jokeRepository;
    private Random random;

    @Autowired
    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
        random = new Random();
    }

    public List<Joke> getAllJokes(){
        return jokeRepository.findAll();
    }

    public Joke getRandomJoke(){
        List<Joke> jokes = getAllJokes();

        if(jokes == null || jokes.isEmpty()){
            throw new JokeNotFoundException("Jokes cannot be found");
        }

        return jokes.get(random.nextInt(jokes.size()));
    }
}
