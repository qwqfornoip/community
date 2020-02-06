package com.qwqnoi.community.model;

import lombok.Data;

@Data
public class Usr {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
