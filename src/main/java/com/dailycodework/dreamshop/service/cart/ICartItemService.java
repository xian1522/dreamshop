package com.dailycodework.dreamshop.service.cart;

import com.dailycodework.dreamshop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quentity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuentity(Long cartId, Long productId, int quentity);

    CartItem getCartItem(Long cartId, Long productId);
}
