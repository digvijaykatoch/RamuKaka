/**
 * Bean for Audit Tool Supplier Table
 */
package com.schein.bean;

import java.sql.Timestamp;

/**
 * This is the Audit Tool Supplier Table a.k.a. IM0161
 */
public class SupplierTable {
 
   /** Property supplier */
   Integer supplier;
 
   /** Property freightTerms */
   String freightTerms;
 
   /** Property emailContact */
   String emailContact;
 
   /** Property crtUser */
   String crtUser;
 
   /** Property crtPgm */
   String crtPgm;
 
   /** Property tsCrte */
   Timestamp tsCrte;
 
   /** Property updUser */
   String updUser;
 
   /** Property updPgm */
   String updPgm;
 
   /** Property tsUpd */
   Timestamp tsUpd;
 
   /**
    * Constructor
    */
   public SupplierTable() {
   }
 
   /**
    * Gets the supplier
    */
   public Integer getSupplier() {
      return this.supplier;
   }
 
   /**
    * Sets the supplier
    */
   public void setSupplier(Integer value) {
      this.supplier = value;
   }
 
   /**
    * Gets the freightTerms
    */
   public String getFreightTerms() {
      return this.freightTerms;
   }
 
   /**
    * Sets the freightTerms
    */
   public void setFreightTerms(String value) {
      this.freightTerms = value;
   }
 
   /**
    * Gets the emailContact
    */
   public String getEmailContact() {
      return this.emailContact;
   }
 
   /**
    * Sets the emailContact
    */
   public void setEmailContact(String value) {
      this.emailContact = value;
   }
 
   /**
    * Gets the crtUser
    */
   public String getCrtUser() {
      return this.crtUser;
   }
 
   /**
    * Sets the crtUser
    */
   public void setCrtUser(String value) {
      this.crtUser = value;
   }
 
   /**
    * Gets the crtPgm
    */
   public String getCrtPgm() {
      return this.crtPgm;
   }
 
   /**
    * Sets the crtPgm
    */
   public void setCrtPgm(String value) {
      this.crtPgm = value;
   }
 
   /**
    * Gets the tsCrte
    */
   public Timestamp getTsCrte() {
      return this.tsCrte;
   }
 
   /**
    * Sets the tsCrte
    */
   public void setTsCrte(Timestamp value) {
      this.tsCrte = value;
   }
 
   /**
    * Gets the updUser
    */
   public String getUpdUser() {
      return this.updUser;
   }
 
   /**
    * Sets the updUser
    */
   public void setUpdUser(String value) {
      this.updUser = value;
   }
 
   /**
    * Gets the updPgm
    */
   public String getUpdPgm() {
      return this.updPgm;
   }
 
   /**
    * Sets the updPgm
    */
   public void setUpdPgm(String value) {
      this.updPgm = value;
   }
 
   /**
    * Gets the tsUpd
    */
   public Timestamp getTsUpd() {
      return this.tsUpd;
   }
 
   /**
    * Sets the tsUpd
    */
   public void setTsUpd(Timestamp value) {
      this.tsUpd = value;
   }
}