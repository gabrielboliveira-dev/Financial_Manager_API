package br.com.texsistemas.financemanager.domain.transaction;

import br.com.texsistemas.financemanager.domain.account.Account;
import br.com.texsistemas.financemanager.domain.category.Category;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String description;
    private Float value; //
    private Date date;
    private String type;
    private String status;

    // Getters
    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Float getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}