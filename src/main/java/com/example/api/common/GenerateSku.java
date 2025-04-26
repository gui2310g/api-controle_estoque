package com.example.api.common;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Random;

@Component
public class GenerateSku {
    public static String generateSku(String nome) {
        String normalized = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        String slug = normalized.toUpperCase().replaceAll("\\s+", "-");
        long timestamp = System.currentTimeMillis();
        int random = new Random().nextInt(9000) + 1000;
        return slug + "-" + timestamp + "-" + random;
    }
}
