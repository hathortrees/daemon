package com.hathor.daemon.data.entities;

import javax.persistence.*;

@Entity
public class Team {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Integer id;

   @Column(unique = true)
   private String name;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
