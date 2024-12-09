package com.example.ec_orders.entities.order.dto;

import java.util.Date;
import java.util.List;

import com.example.ec_orders.entities.enums.Status;
import com.example.ec_orders.entities.order.Order;

public class OrderDTO {

    private Long id;
    private List<Long> productId;
    private Date date;
    private Double total;
    private Status status;
    private Long userId;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.productId = order.getProductId();
        this.date = order.getDate();
        this.total = order.getTotal();
        this.status = order.getStatus();
        this.userId = order.getUserId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getProductId() {
        return productId;
    }

    public void setProductId(List<Long> productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
