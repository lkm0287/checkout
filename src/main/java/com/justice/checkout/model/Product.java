package com.justice.checkout.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final String productCode;
    private final String name;
    private BigDecimal price;
    private final String offerCode;
    private Offer offer;

    public Product(String productCode, String name, BigDecimal price, String offerCode) {
        this.productCode = productCode;
        this.name = name;
        this.price = price;
        this.offerCode = offerCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public Product getClone() {
        Product clone = new Product(this.getProductCode(), this.getName(), this.getPrice(), this.getOfferCode());
        clone.setOffer(this.getOffer());
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productCode, product.productCode)
                && Objects.equals(name, product.name)
                && Objects.equals(price, product.price)
                && Objects.equals(offerCode, product.offerCode)
                && Objects.equals(offer, product.offer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, name, price, offerCode, offer);
    }
}
