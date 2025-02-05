package br.com.texsistemas.financemanager.domain.budget;

import br.com.texsistemas.financemanager.domain.category.Category;
import br.com.texsistemas.financemanager.domain.user.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "budget")
@Entity
public class Budget {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Float limit;
    private Date dateStart;
    private Date dateEnd;

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Float getLimit() {
        return limit;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}