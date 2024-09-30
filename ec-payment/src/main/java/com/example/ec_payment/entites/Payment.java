package com.example.ec_payment.entites;

import java.io.Serializable;
import java.util.Date;

import com.example.ec_payment.entites.enums.PaymentMethod;
import com.example.ec_payment.entites.enums.PaymentStats;

public class Payment implements Serializable {

    private Long id;
    private Date paymentDate;
    private Integer stats;
    private Integer paymentMethod;

    public Payment() {
    }

    public Payment(Long id, Date paymentDate, PaymentStats stats, PaymentMethod paymentMethod) {
        this.id = id;
        this.paymentDate = paymentDate;
        setPaymentStats(stats);;
        setPaymentMethod(paymentMethod);;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    
    public PaymentStats getPaymentStats() {
        return PaymentStats.valueOf(stats);
    }

    public void setPaymentStats(PaymentStats stats) {
        if (stats != null) {
            this.stats = stats.getCode();
        }
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.valueOf(paymentMethod);
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            this.paymentMethod = paymentMethod.getCode();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
