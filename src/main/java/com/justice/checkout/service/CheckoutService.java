package com.justice.checkout.service;

import com.justice.checkout.model.Offer;
import com.justice.checkout.model.Product;
import com.justice.checkout.storage.Memory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckoutService {

    private final Memory memory;

    public BigDecimal checkout() {
        applyOffersToBasket();

        BigDecimal total = getBasketTotal();

        memory.clearCheckoutBasket();

        return total.setScale(2, BigDecimal.ROUND_DOWN);
    }

    private void applyOffersToBasket() {
        Set<Product> productsWithOffers = getProductOffers();

        for (Product productOffer: productsWithOffers) {
            List<Product> discountableProductsInBasket = getProductsInBasketByProductCode(productOffer.getProductCode());

            if (qualifiesForPriceDiscountOffer(productOffer.getOffer(), discountableProductsInBasket.size())) {
                System.out.printf("Applying price discount %s of %d percent off to %d of product %s%n",
                        productOffer.getOffer().getOfferCode(),
                        productOffer.getOffer().getDiscountPercentage(),
                        discountableProductsInBasket.size(),
                        productOffer.getProductCode());
                applyPriceDiscountsToProductsInBasket(productOffer.getOffer().getDiscountPercentage(), discountableProductsInBasket);
            }

            if (qualifiesForBogofOffer(productOffer.getOffer())) {
                System.out.printf("Applying BOGOF offer %s to the %d products of product %s in the basket%n",
                        productOffer.getOffer().getOfferCode(),
                        discountableProductsInBasket.size(),
                        productOffer.getProductCode());
                applyBogofOffers(discountableProductsInBasket);
            }
        }
    }

    private void applyBogofOffers(List<Product> discountableProductsInBasket) {
        int productCounter = 1;
        for (Product discountableProduct: discountableProductsInBasket) {
            // for every other product apply the BOGOF offer
            if (productCounter%2 == 0) {
                discountableProduct.setPrice(BigDecimal.ZERO);
            }
            productCounter++;
        }
    }

    public boolean qualifiesForPriceDiscountOffer(Offer offer, int numberOfQualifyingProductsInBasket) {
        return offer.isPriceDiscountOffer()
                && offer.getMinimumNumberOfProducts() <= numberOfQualifyingProductsInBasket;
    }

    public boolean qualifiesForBogofOffer(Offer offer) {
        return offer.isBogof();
    }

    private void applyPriceDiscountsToProductsInBasket(int discountPercentage, List<Product> discountableProductsInBasket) {
        for (Product productToDiscountInBasket: discountableProductsInBasket) {
            productToDiscountInBasket.setPrice(calculateNewDiscountedPrice(productToDiscountInBasket.getPrice(), discountPercentage));
        }
    }

    public List<Product> getProductsInBasketByProductCode(String productCode) {
        return memory
                .getCheckoutBasket()
                .stream()
                .filter(product -> product.getProductCode().equals(productCode))
                .collect(Collectors.toList());
    }

    public Set<Product> getProductOffers() {
        return memory.getCheckoutBasket()
                .stream()
                .filter(product -> product.getOffer() != null)
                .collect(Collectors.toSet());
    }

    private BigDecimal getBasketTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product: memory.getCheckoutBasket()) {
            total = total.add(product.getPrice());
        }
        return total;
    }

    private static BigDecimal calculateNewDiscountedPrice(BigDecimal basePrice, int percentageDiscount){
        if (percentageDiscount > 100) {
            percentageDiscount = 100; // validation to prevent larger discounts
        }
        BigDecimal newAmountAsPercentage = new BigDecimal(100 - percentageDiscount);
        return basePrice.multiply(newAmountAsPercentage).movePointLeft(2);
    }

    public void addOfferToOffers(Offer offer) {
        memory.addOffer(offer);
    }

    public void addProductToProducts(Product product) {
        Offer offer = memory.getOffers().get(product.getOfferCode());
        if (offer == null) {
            System.out.printf("No offer found for product %s with offer code %s.%n", product.getProductCode(), product.getOfferCode());
        } else {
            product.setOffer(offer);
        }
        memory.addProduct(product);
    }

    public void addProductToCheckoutBasket(String productCode) {
        Product product = memory.getProduct(productCode);
        memory.addProductToBasket(product.getClone());
    }
}
