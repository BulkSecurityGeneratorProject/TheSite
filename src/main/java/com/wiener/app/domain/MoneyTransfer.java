package com.wiener.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MoneyTransfer.
 */
@Entity
@Table(name = "money_transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MoneyTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payed_amount")
    private Integer payedAmount;

    @Column(name = "payed_time")
    private LocalDate payedTime;

    @Column(name = "payed_in_currency")
    private String payedInCurrency;

    @Column(name = "payment_successful")
    private Boolean paymentSuccessful;

    @Column(name = "payment_mode")
    private String paymentMode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayedAmount() {
        return payedAmount;
    }

    public MoneyTransfer payedAmount(Integer payedAmount) {
        this.payedAmount = payedAmount;
        return this;
    }

    public void setPayedAmount(Integer payedAmount) {
        this.payedAmount = payedAmount;
    }

    public LocalDate getPayedTime() {
        return payedTime;
    }

    public MoneyTransfer payedTime(LocalDate payedTime) {
        this.payedTime = payedTime;
        return this;
    }

    public void setPayedTime(LocalDate payedTime) {
        this.payedTime = payedTime;
    }

    public String getPayedInCurrency() {
        return payedInCurrency;
    }

    public MoneyTransfer payedInCurrency(String payedInCurrency) {
        this.payedInCurrency = payedInCurrency;
        return this;
    }

    public void setPayedInCurrency(String payedInCurrency) {
        this.payedInCurrency = payedInCurrency;
    }

    public Boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public MoneyTransfer paymentSuccessful(Boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
        return this;
    }

    public void setPaymentSuccessful(Boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public MoneyTransfer paymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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
        MoneyTransfer moneyTransfer = (MoneyTransfer) o;
        if (moneyTransfer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moneyTransfer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
            "id=" + getId() +
            ", payedAmount=" + getPayedAmount() +
            ", payedTime='" + getPayedTime() + "'" +
            ", payedInCurrency='" + getPayedInCurrency() + "'" +
            ", paymentSuccessful='" + isPaymentSuccessful() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            "}";
    }
}
