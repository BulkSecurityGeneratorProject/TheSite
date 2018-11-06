package com.wiener.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ChestItem.
 */
@Entity
@Table(name = "chest_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChestItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "num_of_times_rolled")
    private Long numOfTimesRolled;

    @Column(name = "num_of_times_accepted")
    private Long numOfTimesAccepted;

    @ManyToOne
    @JsonIgnoreProperties("chestItems")
    private Chest chest;

    @ManyToMany(mappedBy = "chestItems")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ChestItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public ChestItem type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public ChestItem price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getNumOfTimesRolled() {
        return numOfTimesRolled;
    }

    public ChestItem numOfTimesRolled(Long numOfTimesRolled) {
        this.numOfTimesRolled = numOfTimesRolled;
        return this;
    }

    public void setNumOfTimesRolled(Long numOfTimesRolled) {
        this.numOfTimesRolled = numOfTimesRolled;
    }

    public Long getNumOfTimesAccepted() {
        return numOfTimesAccepted;
    }

    public ChestItem numOfTimesAccepted(Long numOfTimesAccepted) {
        this.numOfTimesAccepted = numOfTimesAccepted;
        return this;
    }

    public void setNumOfTimesAccepted(Long numOfTimesAccepted) {
        this.numOfTimesAccepted = numOfTimesAccepted;
    }

    public Chest getChest() {
        return chest;
    }

    public ChestItem chest(Chest chest) {
        this.chest = chest;
        return this;
    }

    public void setChest(Chest chest) {
        this.chest = chest;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public ChestItem transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public ChestItem addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.getChestItems().add(this);
        return this;
    }

    public ChestItem removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.getChestItems().remove(this);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChestItem chestItem = (ChestItem) o;
        if (chestItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chestItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChestItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", price=" + getPrice() +
            ", numOfTimesRolled=" + getNumOfTimesRolled() +
            ", numOfTimesAccepted=" + getNumOfTimesAccepted() +
            "}";
    }
}
