package com.prodRepo.prodRepo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer prodId;
  private String prodName;
  private String prodDescription;
  private Boolean prodAvailability;
  private Integer prodQuantity;
  private String prodImageName;
  private String prodImageType;
  @Lob
  private byte[] prodImageData;

}