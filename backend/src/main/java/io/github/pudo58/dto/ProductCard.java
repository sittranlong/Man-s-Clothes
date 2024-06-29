package io.github.pudo58.dto;

import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ProductCard {
    private UUID id;
    private String name;
    private int price;
    private String description;
    private byte[] image;
    private Rating rating;

    @Setter
    @Getter
    public static class Rating {
        private double rate;
        private int count;
    }

    public static ProductCard fromProduct(Product product) {
        ProductCard card = new ProductCard();
        card.setId(product.getId());
        card.setName(product.getName());
        card.setPrice(product.getPrice());
        card.setDescription(product.getDescription());
        card.setImage(product.getImage());
        Rating rating = new Rating();
        rating.setRate(product.getReviews().stream().mapToInt(Review::getRating).average().orElse(0));
        rating.setCount(product.getReviews().size());
        card.setRating(rating);
        return card;
    }
}
