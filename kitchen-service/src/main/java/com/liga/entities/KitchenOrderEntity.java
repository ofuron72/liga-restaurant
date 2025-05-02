package com.liga.entities;

import com.liga.objects.KitchenStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая заказ на кухне.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@Table(name = "kitchen_order")
public class KitchenOrderEntity {
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
    private Set<OrderToDishEntity> orderDishes = new HashSet<>();

    @Column(name="order_id_in_waiter_service")
    private Long orderIdWaiterService;
}
