package br.com.texsistemas.financemanager.domain.account;

import br.com.texsistemas.financemanager.domain.user.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "account")
@Entity
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String type;
    private Float openingBalance;
    private String coin;
    private Date date;

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Float getOpeningBalance() {
        return openingBalance;
    }

    public String getCoin() {
        return coin;
    }

    public Date getDate() {
        return date;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOpeningBalance(Float openingBalance) {
        this.openingBalance = openingBalance;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}