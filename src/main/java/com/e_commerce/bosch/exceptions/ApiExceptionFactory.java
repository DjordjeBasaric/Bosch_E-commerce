package com.e_commerce.bosch.exceptions;


import org.springframework.http.HttpStatus;

public class ApiExceptionFactory {

    public static ApiException productNotFound(){
        return new ApiException("Product not found", HttpStatus.NOT_FOUND);
    }

    public static ApiException minPriceGreaterThenMaxPrice(){
        return new ApiException("Filter minimum price is greater then maximum price", HttpStatus.CONFLICT);
    }

    public static ApiException sortByNotFound() {
        return new ApiException("Sort by not found", HttpStatus.NOT_FOUND);
    }

    public static ApiException sortDirectionNotFound() {
        return new ApiException("Sort direction not found, has to be either 'desc' or 'asc' (case insensitive)",
                HttpStatus.NOT_FOUND);
    }

    public static ApiException cartNotFound() {
        return new ApiException("Cart not found", HttpStatus.NOT_FOUND);
    }

    public static ApiException cartItemNotFound() {
        return new ApiException("Cart item not found", HttpStatus.NOT_FOUND);
    }

    public static ApiException invalidQuantity() {
        return new ApiException("Quantity must be 1 or more", HttpStatus.BAD_REQUEST);
    }

    public static ApiException usernameAlredyTaken() {
        return new ApiException("Username already taken", HttpStatus.CONFLICT);
    }

    public static ApiException userNotFound() {
        return new ApiException("User with that credentials not found", HttpStatus.NOT_FOUND);
    }

    public static ApiException invalidCredentials() {
        return new ApiException("Invalid credentials", HttpStatus.BAD_REQUEST);
    }
}
