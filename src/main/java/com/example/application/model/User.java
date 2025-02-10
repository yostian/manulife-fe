package com.example.application.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    
    @Column(name = "jenis_user") // Mapping ke database
    @JsonProperty("jenisUser") // Mapping JSON ke Java
    private String jenisUser;
    
    private String alamat;

    @Column(updatable = false) // Menjaga createDate agar tidak terupdate
    private LocalDateTime createDate;

    public User() {
        this.createDate = LocalDateTime.now(); // Set default createDate saat user dibuat
    }

    public User(String nama, String jenisUser, String alamat) {
        this.nama = nama;
        this.jenisUser = jenisUser;
        this.alamat = alamat;
        this.createDate = LocalDateTime.now(); // Set waktu saat objek dibuat
    }

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenisUser() { 
        return jenisUser; 
    }
    
    public void setJenisUser(String jenisUser) { 
        this.jenisUser = jenisUser; 
    }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public LocalDateTime getCreateDate() { return createDate; }
}
