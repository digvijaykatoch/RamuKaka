/**
 * Bean for Audit Tool Issue Detail Table
 */
package com.schein.bean;

import java.sql.Timestamp;

/**
 * This is the Audit Tool Issue Detail Table a.k.a. IM0162
 */
public class IssueTable {
 
   /** Property controlNo */
   Integer controlNo;
 
   /** Property issueType */
   Integer issueType;
 
   /** Property issueComment */
   String issueComment;
 
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
   public IssueTable() {
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
    * Gets the issueType
    */
   public Integer getIssueType() {
      return this.issueType;
   }
 
   /**
    * Sets the issueType
    */
   public void setIssueType(Integer value) {
      this.issueType = value;
   }
 
   /**
    * Gets the issueComment
    */
   public String getIssueComment() {
      return this.issueComment;
   }
 
   /**
    * Sets the issueComment
    */
   public void setIssueComment(String value) {
      this.issueComment = value;
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