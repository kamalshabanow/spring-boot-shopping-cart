package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart.getItems()));  //change cart -> cart.getItems()
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}