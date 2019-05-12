package com.shahinnazarov.paribas.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "client_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientDetail implements Serializable {
    private static final long serialVersionUID = 7884101654041029603L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Length(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Length(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;
//    @JsonIgnore
//    @Length(max = 15)
//    @Pattern(regexp = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$")
//    @Column(name = "secret_key", length = 15)
//    private String secretKey;
    //etc.

    @JsonIgnore
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.LAZY)
    private Collection<BondOrder> bondOrders;
}
