package com.origin.abbreviated.url.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String url;

    @Column(name = "short")
    private String shortenedUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
}
