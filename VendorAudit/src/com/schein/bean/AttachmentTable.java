/**
 * Bean for Audit Tool Attachment Table
 */
package com.schein.bean;

import java.sql.Timestamp;

/**
 * This is the Audit Tool Attachment Table a.k.a. IM0163
 */
public class AttachmentTable {
 
   /** Property controlNo */
   Integer controlNo;
 
   /** Property lineNo */
   Integer lineNo;
 
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
   public AttachmentTable() {
   }
 
   /**
    * Gets the controlNo
    */
   public Integer getControlNo() {
      return this.controlNo;
   }
 
   /**
    * Sets the controlNo
    */
   public void setControlNo(Integer value) {
      this.controlNo = value;
   }
 
   /**
    * Gets the lineNo
    */
   public Integer getLineNo() {
      return this.lineNo;
   }
 
   /**
    * Sets the lineNo
    */
   public void setLineNo(Integer value) {
      this.lineNo = value;
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