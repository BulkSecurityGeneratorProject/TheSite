package com.wiener.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "notes")
    private String notes;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "transaction_chest_item",
               joinColumns = @JoinColumn(name = "transactions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "chest_items_id", referencedColumnName = "id"))
    private Set<ChestItem> chestItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Transaction address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public Transaction notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public Transaction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ChestItem> getChestItems() {
        return chestItems;
    }

    public Transaction chestItems(Set<ChestItem> chestItems) {
        this.chestItems = chestItems;
        return this;
    }

    public Transaction addChestItem(ChestItem chestItem) {
        this.chestItems.add(chestItem);
        chestItem.getTransactions().add(this);
        return this;
    }

    public Transaction removeChestItem(ChestItem chestItem) {
        this.chestItems.remove(chestItem);
        chestItem.getTransactions().remove(this);
        return this;
    }

    public void setChestItems(Set<ChestItem> chestItems) {
        this.chestItems = chestItems;
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
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", notes='" + getNotes() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
