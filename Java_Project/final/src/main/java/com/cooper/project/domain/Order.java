package com.cooper.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", unique = true, nullable = false)
    private Long orderId;

    @Column(name = "CUSTOMER_NAME", length = 40)
    private String customerName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderRecipe> orderRecipe = new ArrayList<>(); // Initialize the list here
    
}
