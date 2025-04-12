package com.liga.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liga.objects.KitchenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "kitchen_order")
public class KitchenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kitchen_order_seq")
    @SequenceGenerator(name = "kitchen_order_seq", sequenceName = "kitchen_order_sequence", allocationSize = 1)
    @Column(name = "kitchen_order_id")
    private Long id;

    @Column(name = "waiter_order_no")
    private Long waiterOrderNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private KitchenStatus status;

    @CreationTimestamp
    @Column(name = "create_dttm", updatable = false)
    private ZonedDateTime createDttm;

    @OneToMany(mappedBy = "order")
    private Set<OrderToDish> orderDishes = new HashSet<>();

    @Column(name="order_id_in_waiter_service")
    private Long orderIdWaiterService;
}
