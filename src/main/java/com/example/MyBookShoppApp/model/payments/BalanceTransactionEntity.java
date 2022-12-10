package com.example.MyBookShoppApp.model.payments;

import com.example.MyBookShoppApp.model.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "balance_transaction")
@Getter
@Setter
@NoArgsConstructor
public class BalanceTransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "date NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int value;

     @Column(columnDefinition = "TEXT NOT NULL")
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @OneToMany(mappedBy = "pk.balanceTransaction", cascade = CascadeType.ALL)
    private Set<Book2BalanceTransaction> setBalanceTransaction = new HashSet<>(0);


}
