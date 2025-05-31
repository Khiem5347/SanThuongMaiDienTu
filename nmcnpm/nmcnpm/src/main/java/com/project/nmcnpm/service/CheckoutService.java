package com.project.nmcnpm.service;

import com.project.nmcnpm.dto.Purchase;
import com.project.nmcnpm.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}