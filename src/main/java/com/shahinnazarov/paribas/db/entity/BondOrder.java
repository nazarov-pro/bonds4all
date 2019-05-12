package com.shahinnazarov.paribas.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "bond_orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BondOrder implements Serializable {
    private static final long serialVersionUID = 4854571805600354340L;
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_detail_id", nullable = false)
    private ClientDetail clientDetail;
    @Column(name = "term_length")
    private int termLength;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "ip_address",
            length = 25, nullable = false)
    private String ipAddress;
    @Column(name = "final_sum", nullable = false)
    private BigDecimal finalSum;
    @Column(name = "coupon_amount", nullable = false)
    private BigDecimal couponAmount;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime created;
    @Column(name = "valid_date", nullable = false)
    private LocalDateTime valid;

    @OneToMany(mappedBy = "bondOrder", fetch = FetchType.LAZY)
    private Collection<BondHistory> bondHistories;
}
