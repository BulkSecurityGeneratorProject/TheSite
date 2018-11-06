package com.wiener.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chest.
 */
@Entity
@Table(name = "chest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "roll_price")
    private Integer rollPrice;

    @Column(name = "num_of_times_rolled")
    private Long numOfTimesRolled;

    @OneToMany(mappedBy = "chest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChestItem> chestItems = new HashSet<>();
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

    public Chest name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Chest type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRollPrice() {
        return rollPrice;
    }

    public Chest rollPrice(Integer rollPrice) {
        this.rollPrice = rollPrice;
        return this;
    }

    public void setRollPrice(Integer rollPrice) {
        this.rollPrice = rollPrice;
    }

    public Long getNumOfTimesRolled() {
        return numOfTimesRolled;
    }

    public Chest numOfTimesRolled(Long numOfTimesRolled) {
        this.numOfTimesRolled = numOfTimesRolled;
        return this;
    }

    public void setNumOfTimesRolled(Long numOfTimesRolled) {
        this.numOfTimesRolled = numOfTimesRolled;
    }

    public Set<ChestItem> getChestItems() {
        return chestItems;
    }

    public Chest chestItems(Set<ChestItem> chestItems) {
        this.chestItems = chestItems;
        return this;
    }

    public Chest addChestItem(ChestItem chestItem) {
        this.chestItems.add(chestItem);
        chestItem.setChest(this);
        return this;
    }

    public Chest removeChestItem(ChestItem chestItem) {
        this.chestItems.remove(chestItem);
        chestItem.setChest(null);
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
        Chest chest = (Chest) o;
        if (chest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", rollPrice=" + getRollPrice() +
            ", numOfTimesRolled=" + getNumOfTimesRolled() +
            "}";
    }
}
