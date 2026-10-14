package com.dailycodework.dreamshop.repository;

import com.dailycodework.dreamshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
