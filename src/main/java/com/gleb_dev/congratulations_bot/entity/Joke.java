package com.gleb_dev.congratulations_bot.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Class represents joke in database
 */

@Entity
@Data
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "text")
    private String text;
}
