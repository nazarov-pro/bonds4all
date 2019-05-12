package com.shahinnazarov.paribas.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryActions;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryItemTypes;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "bond_history_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BondHistoryItem implements Serializable {
    private static final long serialVersionUID = 4812474668173791340L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bond_history_id", nullable = false)
    private BondHistory bondHistory;
    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BondHistoryItemTypes type;
    @Length(max = 100)
    @Column(name = "new_value", length = 100)
    private String newValue;
    @Length(max = 100)
    @Column(name = "old_value", length = 100)
    private String oldValue;
}
