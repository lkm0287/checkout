package com.justice.checkout.controller;

import com.justice.checkout.model.Offer;
import com.justice.checkout.model.Product;
import com.justice.checkout.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Checkout a basket of scanned products. Applying any relevant offers and providing the " +
            "checkout total price in the response in pounds and pence.")
    @PostMapping()
    public @ResponseBody
    ResponseEntity<String> checkout() {
        BigDecimal total = checkoutService.checkout();
        return new ResponseEntity<>(String.format("Checkout total: %s", total), HttpStatus.OK);
    }

    @Operation(summary = "Scan a product to purchase by providing its product code.")
    @PostMapping("/scan/product/{productCode}")
    public @ResponseBody
    ResponseEntity<String> scanProduct(@Parameter(description = "Product code of the product to be scanned.")
            @PathVariable("productCode") String productCode) {
        checkoutService.addProductToCheckoutBasket(productCode);
        return new ResponseEntity<>(String.format("Successfully scanned product to basket: %s", productCode), HttpStatus.OK);
    }

    @Operation(summary = "Add a product to the product catalogue. Any offer codes provided will be applied " +
            "to scanned products if present in the offer catalogue.")
    @PostMapping("/add/product")
    public @ResponseBody
    ResponseEntity<String> addProduct(@RequestBody Product product) {
        checkoutService.addProductToProducts(product);
        return new ResponseEntity<>(String.format("Successfully added product to product catalogue: %s", product.getProductCode()), HttpStatus.OK);
    }

    @Operation(summary = "Add an offer to the offer catalogue. There are two types of offers - buy one get one free (BOGOF) or price discount offers.")
    @PostMapping("/add/offer")
    public @ResponseBody
    ResponseEntity<String> addOffer(@RequestBody Offer offer) {
        checkoutService.addOfferToOffers(offer);
        return new ResponseEntity<>(String.format("Successfully added offer to offers: %s", offer.getOfferCode()), HttpStatus.OK);
    }

}
