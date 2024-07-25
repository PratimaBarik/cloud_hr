package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.PromoCodeRequestMapper;

import java.util.List;

public interface PromoCodeRequestService {
    PromoCodeRequestMapper savePromoCode(PromoCodeRequestMapper promoCodeRequestMapper);

    List<PromoCodeRequestMapper> getAllPromoCode();
}
