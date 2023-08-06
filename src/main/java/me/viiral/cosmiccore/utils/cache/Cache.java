package me.viiral.cosmiccore.utils.cache;

import lombok.Getter;

@Getter
public class Cache {
    private final String name;
    public Cache(String name) {
        this.name = name;
    }
}
