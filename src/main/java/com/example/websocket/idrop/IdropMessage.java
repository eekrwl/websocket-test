package com.example.websocket.idrop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdropMessage {
    //private Long child;
    private Double longitude;
    private Double latitude;
    //private LocalDateTime dateTime;
    private String sender;
    private String receiver;
}

//실제로는 아이 pk 보내주면 알아서 부모 조회