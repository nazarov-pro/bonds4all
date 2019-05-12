package com.shahinnazarov.paribas.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryActions;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "bond_histories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BondHistory implements Serializable {
    private static final long serialVersionUID = 386958264224345192L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bond_order_id", nullable = false)
    private BondOrder bondOrder;
    @Column(name = "bond_action", nullable = false)
    @Enumerated(EnumType.STRING)
    private BondHistoryActions action;
    @Column(name = "created_datetime")
    private LocalDateTime created;
    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bondHistory")
    private Collection<BondHistoryItem> items;
}
