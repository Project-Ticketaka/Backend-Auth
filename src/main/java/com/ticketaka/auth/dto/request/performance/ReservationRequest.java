package com.ticketaka.auth.dto.request.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequest {
    private String memberId;
    private String memberEmail;
    private String performanceId;
    private String prfPoster;
    private int prfSessionId;
    private int price;
}