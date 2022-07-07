package com.dentapp.spring.models;

public enum StatusType {
    waiting, //Beklemede issue oluşturuldupunda
    accepted, // Kabul Edildi teknisyen
    deliveryDate, //Teslim Tarihi Girildi teknisyen
    approved, // Teslimat Onaylandı hekim
    notApproved,
    delay, //Teslimat Gecikmede tarihin geçmesi otomatik
    review, // revize ye gönderme
    completed //hekim
}
