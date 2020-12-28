package pl.nicieja.springprofiles.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Basket {

    private List<Product> productList;

    public BigDecimal getValueOfBasket() {
//        BigDecimal invoiceValue = BigDecimal.ZERO;
        BigDecimal allProductsValue = BigDecimal.ZERO;
//        for (Product product : productList) {
//            invoiceValue = invoiceValue.add(product.getPrice());
//        }
        allProductsValue = productList.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return allProductsValue;
    }
}
