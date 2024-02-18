package com.scaler.EcomProductService.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Product extends BaseModel {
                private String title;
                private String description;
                private String image;


                //Product : Category => M: 1
                @ManyToOne
                //@JoinColumn(name = "category_id")
                private Category  category;
                //For Above case
                // Unidirectional -> adds category id on product side
                // Bidirectional -> adds a mapping table by default , to avoid that use @Joincolumn

                //Product : Price => 1:1
                @OneToOne
                private Price price;


                //Product : Order => M:M
                // But Orders are not required to be shown at product side. So this is uni-directional

                // Just for showing , M:M bidirectional below
                // @ManyToMany(mappedBy = "products")
                // private List<Order> orders;
                // Bidirectional M:M , without mappedBy creates two mapping tables
                // Name given for mapping table created is tablename_attributename
}
