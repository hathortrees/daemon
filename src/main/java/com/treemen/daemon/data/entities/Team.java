package com.treemen.daemon.data.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mai_tree_teams")
public class Team {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Integer id;

   @Column(unique = true)
   private String name;

   private int treesCount;

   private Date createdAt;

   private Date updatedAt;

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

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public Date getUpdatedAt() {
      return updatedAt;
   }

   public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
   }

   public int getTreesCount() {
      return treesCount;
   }

   public void setTreesCount(int treesCount) {
      this.treesCount = treesCount;
   }
}
