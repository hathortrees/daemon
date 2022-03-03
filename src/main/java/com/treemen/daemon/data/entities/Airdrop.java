package com.treemen.daemon.data.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mai_tree_airdrops")
public class Airdrop {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private Date date;

   private Long totalHtr;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public Long getTotalHtr() {
      return totalHtr;
   }

   public void setTotalHtr(Long totalHtr) {
      this.totalHtr = totalHtr;
   }
}
