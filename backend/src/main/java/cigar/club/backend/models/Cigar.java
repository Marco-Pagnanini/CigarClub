package cigar.club.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Cigar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barCode;
    private String admCode;
    private String name;
    private String brandName;
    private String description;
    private String manufacturer;
    private String origin;
    private Strength strength;
    private String wrapper;
    private Color wrapperColor;
    private String binder;
    private String filler;
    private String masterLine;
    private String rollingType;
    private String shape;
    private Double price;
    private Rating rating;
    private Integer numberInABox;
    private Integer ring;
    private Integer smokingTime;



}
