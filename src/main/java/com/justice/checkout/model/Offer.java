package com.justice.checkout.model;

public class Offer {

    private final String offerCode;
    private final String name;
    private final boolean bogof;
    private final boolean priceDiscountOffer;
    private final int minimumNumberOfProducts;
    private final int discountPercentage;

    public Offer(String offerCode, String name, boolean bogof, boolean priceDiscountOffer, int minimumNumberOfProducts, int discountPercentage) {
        this.offerCode = offerCode;
        this.name = name;
        this.bogof = bogof;
        this.priceDiscountOffer = priceDiscountOffer;
        this.minimumNumberOfProducts = minimumNumberOfProducts;
        this.discountPercentage = discountPercentage;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public String getName() {
        return name;
    }

    public boolean isBogof() { return bogof; }

    public boolean isPriceDiscountOffer() {
        return priceDiscountOffer;
    }

    public int getMinimumNumberOfProducts() {
        return minimumNumberOfProducts;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }
}
