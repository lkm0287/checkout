package com.justice.checkout.service;

import com.justice.checkout.model.Offer;
import com.justice.checkout.model.Product;
import com.justice.checkout.storage.Memory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    private static final Product FRUIT_TEA_1 = new Product("FR1", "Fruit tea", new BigDecimal("3.11"), "OFF1");
    private static final Product FRUIT_TEA_2 = new Product("FR1", "Fruit tea", new BigDecimal("3.11"), "OFF1");
    private static final Product FRUIT_TEA_3 = new Product("FR1", "Fruit tea", new BigDecimal("3.11"), "OFF1");
    private static final Product FRUIT_TEA_4 = new Product("FR1", "Fruit tea", new BigDecimal("3.11"), "OFF1");

    private static final Offer FIFTY_PERCENT_OFF_THREE_OR_MORE_OFFER = new Offer("OFF1", "50% off three or more products", false, true, 3, 50);
    private static final int ONCE = 1;
    private static final int THREE = 3;

    @Mock
    private Memory memory;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeAll
    static void beforeAllTests() {
        FRUIT_TEA_1.setOffer(FIFTY_PERCENT_OFF_THREE_OR_MORE_OFFER);
        FRUIT_TEA_2.setOffer(FIFTY_PERCENT_OFF_THREE_OR_MORE_OFFER);
        FRUIT_TEA_3.setOffer(FIFTY_PERCENT_OFF_THREE_OR_MORE_OFFER);
        FRUIT_TEA_4.setOffer(FIFTY_PERCENT_OFF_THREE_OR_MORE_OFFER);
    }

    @BeforeEach
    private void reset() {
        FRUIT_TEA_1.setPrice(new BigDecimal("3.11"));
        FRUIT_TEA_2.setPrice(new BigDecimal("3.11"));
        FRUIT_TEA_3.setPrice(new BigDecimal("3.11"));
        FRUIT_TEA_4.setPrice(new BigDecimal("3.11"));
    }

    @Test
    void checkoutWithNoProductQualifyingOffer() {
        List<Product> checkoutBasket = new ArrayList<>();
        checkoutBasket.add(FRUIT_TEA_1);

        Mockito.when(memory.getCheckoutBasket()).thenReturn(checkoutBasket);

        BigDecimal actualBasketTotal = checkoutService.checkout();

        Assertions.assertEquals(BigDecimal.valueOf(3.11), actualBasketTotal);
        Mockito.verify(memory, Mockito.times(THREE)).getCheckoutBasket();
        Mockito.verify(memory, Mockito.times(ONCE)).clearCheckoutBasket();
    }

    @Test
    void checkoutWithProductQualifyingOfferFiftyPercentOffThreeOrMore() {
        List<Product> checkoutBasket = new ArrayList<>();
        checkoutBasket.add(FRUIT_TEA_1);
        checkoutBasket.add(FRUIT_TEA_2);
        checkoutBasket.add(FRUIT_TEA_3);
        checkoutBasket.add(FRUIT_TEA_4);

        Mockito.when(memory.getCheckoutBasket()).thenReturn(checkoutBasket);

        BigDecimal actualBasketTotal = checkoutService.checkout();

        Assertions.assertEquals(BigDecimal.valueOf(6.22), actualBasketTotal);
        Mockito.verify(memory, Mockito.times(THREE)).getCheckoutBasket();
        Mockito.verify(memory, Mockito.times(ONCE)).clearCheckoutBasket();
    }

    @Test
    void qualifiesForPriceDiscountOffer() {
    }

    @Test
    void qualifiesForBogofOffer() {
    }

    @Test
    void getProductsInBasketByProductCode() {
    }

    @Test
    void getProductOffers() {
    }

    @Test
    void addOfferToOffers() {
    }

    @Test
    void addProductToProducts() {
    }

    @Test
    void addProductToCheckoutBasket() {
    }
}