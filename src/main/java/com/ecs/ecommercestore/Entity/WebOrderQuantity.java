package com.ecs.ecommercestore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The quantity ordered of a product.
 * @author mohamednicer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "web_order_quantity ")
public class WebOrderQuantity {

    /** The unqiue id of the order quantity. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** The product being ordered. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** The quantity being ordered. */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /** The order itself. */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private WebOrder order;
}
