package com.example.productsmanager2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Andrew Korzhov
 * @project ProductsManager2
 * @createdAt 05-Nov-19
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private int price;
    private int count;

    @Override
    public String toString() {
        return "Product: " + name + ", price: " + price + ", count: " + count;
    }
}
