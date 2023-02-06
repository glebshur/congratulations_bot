package com.gleb_dev.congratulations_bot.service;

import com.gleb_dev.congratulations_bot.entity.Language;
import com.gleb_dev.congratulations_bot.entity.Wish;
import com.gleb_dev.congratulations_bot.exception.WishNotFoundException;
import com.gleb_dev.congratulations_bot.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Class implements main logic of wish's entity
 */
@Service
public class WishService {

    WishRepository wishRepository;
    Random random;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
        random = new Random();
    }

    public List<Wish> getAllWishes(Language language){
        return wishRepository.findAllByLanguage(language);
    }

    public Wish getRandomWish(Language language){
        List<Wish> wishes = getAllWishes(language);

        if(wishes == null || wishes.isEmpty()){
            throw new WishNotFoundException("Wishes cannot be found");
        }

        return wishes.get(random.nextInt(wishes.size()));
    }
}
