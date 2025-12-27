package cigar.club.backend.models;

import cigar.club.backend.models.enums.Color;
import cigar.club.backend.models.enums.Strength;
import cigar.club.backend.models.enums.Type;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand manufacturer;

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

    private Double rating;

    private Integer numberInABox;

    private Integer ring;

    private Integer smokingTime;

    private Type type;



}
