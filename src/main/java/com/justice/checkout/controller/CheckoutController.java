package com.justice.checkout.controller;

import com.justice.checkout.model.Offer;
import com.justice.checkout.model.Product;
import com.justice.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckoutController {

    private final CheckoutService checkoutService;

    @GetMapping()
    public @ResponseBody
    ResponseEntity<String> checkout() {
        BigDecimal total = checkoutService.checkout();
        return new ResponseEntity<>(String.format("Checkout total: %s", total), HttpStatus.OK);
    }

    @PostMapping("/scan/product/{productCode}")
    public @ResponseBody
    ResponseEntity<String> scanProduct(@PathVariable("productCode") String productCode) {
        checkoutService.addProductToCheckoutBasket(productCode);
        return new ResponseEntity<>(String.format("Successfully scanned product to basket: %s", productCode), HttpStatus.OK);
    }

    @PostMapping("/add/product")
    public @ResponseBody
    ResponseEntity<String> addProduct(@RequestBody Product product) {
        checkoutService.addProductToProducts(product);
        return new ResponseEntity<>(String.format("Successfully added product to product catalogue: %s", product.getProductCode()), HttpStatus.OK);
    }

    @PostMapping("/add/offer")
    public @ResponseBody
    ResponseEntity<String> addOffer(@RequestBody Offer offer) {
        checkoutService.addOfferToOffers(offer);
        return new ResponseEntity<>(String.format("Successfully added offer to offers: %s", offer.getOfferCode()), HttpStatus.OK);
    }

}
