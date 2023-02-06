package com.gleb_dev.congratulations_bot.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Class represents wish in database
 */

@Entity
@Data
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String text;
    @Column(nullable = false)
    private Language language;
}
