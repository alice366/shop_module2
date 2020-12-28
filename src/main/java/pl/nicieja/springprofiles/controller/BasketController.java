package pl.nicieja.springprofiles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import pl.nicieja.springprofiles.model.Basket;
import pl.nicieja.springprofiles.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BasketController {

    private Basket basket;
    private BigDecimal finalValueOfBasket;

    @Value("${main.vat-value}")
    private BigDecimal vatValue;
    @Value("${main.discount-value}")
    private BigDecimal discountValue;

    @Autowired
    public BasketController(Basket basket) {
        this.basket = basket;
        createBasket(basket);
    }

    private void createBasket(Basket basket) {
        Product product1 = new Product("product1", generateRandomBigDecimalFromRange(
                new BigDecimal(50).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(300).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Product product2 = new Product("product2", generateRandomBigDecimalFromRange(
                new BigDecimal(50).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(300).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Product product3 = new Product("product3", generateRandomBigDecimalFromRange(
                new BigDecimal(50).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(300).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Product product4 = new Product("product4", generateRandomBigDecimalFromRange(
                new BigDecimal(50).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(300).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Product product5 = new Product("product5", generateRandomBigDecimalFromRange(
                new BigDecimal(50).setScale(2, BigDecimal.ROUND_HALF_UP),
                new BigDecimal(300).setScale(2, BigDecimal.ROUND_HALF_UP)));

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        for (Product product : productList) {
            System.out.println(product.getName() + " " + product.getPrice());
        }
        basket.setProductList(productList);
    }

    public static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getBasketValue() {
        finalValueOfBasket = basket.getValueOfBasket()
                .multiply(BigDecimal.ONE.add(vatValue))
                .multiply(BigDecimal.ONE.subtract(discountValue))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println("Total basket value: " + finalValueOfBasket);
    }
}
