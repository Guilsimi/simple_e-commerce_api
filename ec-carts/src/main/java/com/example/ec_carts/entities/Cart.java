package com.example.ec_carts.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cart")
public class Cart implements Serializable {

    @Id
    private Long id;
    private List<Long> productId;;

    public Cart() {
    }

    public Cart(Long id, List<Long> productId) {
        this.id = id;
        this.productId = productId;
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

    public void addProductId(Long id) {
        if (this.productId == null) {
            this.productId = new ArrayList<>();
        }
        this.productId.add(id);
    }

    public void removeProductId(Long id) {
        if (this.productId == null) {
            this.productId = new ArrayList<>();
        }
        this.productId.remove(id);
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
        Cart other = (Cart) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }    

}
