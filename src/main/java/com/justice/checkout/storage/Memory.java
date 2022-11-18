package com.justice.checkout.storage;

import com.justice.checkout.model.Offer;
import com.justice.checkout.model.Product;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class Memory {
    private final List<Product> checkoutBasket = new ArrayList<>();
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Offer> offers = new HashMap<>();

    public void clearCheckoutBasket() {
        checkoutBasket.clear();
    }

    public void addProductToBasket(Product product) {
        checkoutBasket.add(product);
    }

    public void addOffer(Offer offer) {
        offers.put(offer.getOfferCode(), offer);
    }

    public Offer getOffer(String offerCode) {
        return offers.get(offerCode);
    }

    public void addProduct(Product product) {
        products.put(product.getProductCode(), product);
    }

    public Product getProduct(String productCode) {
        return products.get(productCode);
    }

}
