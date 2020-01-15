/*
 * Created on Jun 11, 2007
 *
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import com.schein.bean.PageAccess;
import com.schein.bean.User;
import com.schein.bean.CustomPropertyBean;
import com.schein.bean.Whse;
import java.lang.reflect.*;

/**
 * @author dnair
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WebUtils {

  public static String getRequestAttribute(HttpServletRequest request, String name) {
    if (request.getAttribute(name) != null)
      return ((String) request.getAttribute(name));
    else
      return "";
  }

  public static String getRequestParameter(HttpServletRequest request, String name) {
    if (request.getParameter(name) != null)
      return (((String) request.getParameter(name)).trim());
    else
      return "";
  }

  public static HashMap getSelectedHtml(LinkedList ltAll, String strSelected) {
    HashMap htmlSelect = new HashMap();
    String htmlCode1 = "";
    String htmlCode2 = "";
    LinkedList ltSelected = new LinkedList();

    if (strSelected != null && !strSelected.equals("")) {
      StringTokenizer stToken = new StringTokenizer(strSelected, "','");
      while (stToken.hasMoreTokens()) {
        ltSelected.add(stToken.nextToken());
      }
    }
    for (int i = 0; i < ltAll.size(); i++) {
      String strSelect = (String) ltAll.get(i);
      if (ltSelected != null && ltSelected.size() > 0) {
        boolean flag = false;
        for (int j = 0; j < ltSelected.size(); j++) {
          String selected = (String) ltSelected.get(j);
          if (selected.trim().equals(strSelect.trim())) {
            htmlCode2 +=
                "<OPTION VALUE='" + strSelect.trim() + "'>" + strSelect.trim() + "</OPTION>";
            flag = true;
            break;
          }
        }
        if (flag == false)
          htmlCode1 += "<OPTION VALUE='" + strSelect.trim() + "'>" + strSelect.trim() + "</OPTION>";
      } else {
        htmlCode1 += "<OPTION VALUE='" + strSelect.trim() + "'>" + strSelect.trim() + "</OPTION>";
      }
    }
    htmlSelect.put("htmlCode1", htmlCode1);
    htmlSelect.put("htmlCode2", htmlCode2);
    return htmlSelect;
  }

  /*
   * The method getAndSetRequestParameters gets all the request parameters from the
   * HttpServletRequest object and sets all those params for that request Only exception param is
   * "report". We will set this param in the jsp or in the controller.
   */
  public static void getAndSetRequestParameters(HttpServletRequest request) {
    Enumeration params = request.getParameterNames();
    String paramName = "";
    String paramValue = "";
    boolean errorFlag = false;

    while (params.hasMoreElements()) {
      paramName = (String) params.nextElement();
      errorFlag = false;
      try {
        paramValue = WebUtils.getRequestParameter(request, paramName);
      } catch (Exception e) {
        errorFlag = true;
      }
      if (!errorFlag) {
        StringTokenizer st = new StringTokenizer(paramName, ".");
        if ((st.countTokens() == 1) && (!paramName.equalsIgnoreCase("report"))) {
          request.setAttribute(paramName, paramValue);
        }
      }
    }
  }

  public static String checkPageAccess(LinkedList ltPageAccess, String userRole, String jspFile) {
    String strAccess = "disabled";
    PageAccess pageAccessBean = null;
    for (int i = 0; i < ltPageAccess.size(); i++) {
      pageAccessBean = (PageAccess) ltPageAccess.get(i);
      if (pageAccessBean.getUserRole().equalsIgnoreCase(userRole.trim())
          && pageAccessBean.getJspFile().equalsIgnoreCase(jspFile.trim())) {
        strAccess = "";
        break;
      }
    }
    return strAccess;
  }

  public static String getBuyerHtml(LinkedList userList, int selectedBuyer) {
    String htmlCode = "<OPTION VALUE='-1'>Select a Buyer</OPTION>";
    User user = null;
    for (int i = 0; i < userList.size(); i++) {
      user = (User) userList.get(i);
      if (selectedBuyer == user.getBuyerId()) {
        htmlCode += "<OPTION SELECTED VALUE='" + user.getBuyerId() + "'>" + user.getUserDspn()
            + "</OPTION>";
      } else {
        htmlCode += "<OPTION VALUE='" + user.getBuyerId() + "'>" + user.getUserDspn() + "</OPTION>";
      }
    }
    return htmlCode;
  }

  // public static String getReportGroupHtml(LinkedList groupList, int selectedGroup) {
  // String htmlCode = "<OPTION VALUE='-1'>Select a Group</OPTION>";
  // RegionalGroupBean groupBean = null;
  // for (int i = 0; i < groupList.size(); i++) {
  // groupBean = (RegionalGroupBean) groupList.get(i);
  // if (selectedGroup == groupBean.getGroupid()) {
  // htmlCode += "<OPTION SELECTED VALUE='" + groupBean.getGroupid() + "'>"
  // + groupBean.getGroupName() + "</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='" + groupBean.getGroupid() + "'>" + groupBean.getGroupName()
  // + "</OPTION>";
  // }
  // }
  // return htmlCode;
  // }

  /**
   * This method returns html drop down code for order status.
   * 
   * @param orderStatusLinkedList
   * @return html drop down code for order status.
   */
  public static String getDropDownHtmlForProperties(LinkedList orderStatusLinkedList,
      String selectedEntry) {
    String htmlCode = "";
    boolean isSelected;
    for (int i = 0; i < orderStatusLinkedList.size(); i++) {
      CustomPropertyBean customPropertyBean = (CustomPropertyBean) orderStatusLinkedList.get(i);
      isSelected = customPropertyBean.getShortValue().trim().equalsIgnoreCase(selectedEntry.trim());
      if (isSelected) {
        htmlCode += "<OPTION VALUE='" + customPropertyBean.getShortValue()
            + "' selected class='selectedIndex'>" + customPropertyBean.getLongValue() + "</OPTION>";
      } else {
        htmlCode += "<OPTION VALUE='" + customPropertyBean.getShortValue() + "'>"
            + customPropertyBean.getLongValue() + "</OPTION>";
      }
    }
    return htmlCode;

  }

  public static String getDropDownHtml(LinkedList List, int selectedvalue, String dropdowndefault,
      String classname, String method1, String method2, String divison) {

    String htmlCode = "";
    if (!("divison".equalsIgnoreCase(dropdowndefault)))
      htmlCode = "<OPTION VALUE='-1'>Select a " + dropdowndefault + "</OPTION>";
    else
      htmlCode = "<OPTION VALUE=' '>Select a " + dropdowndefault + "</OPTION>";
    try {

      Class beanclass = Class.forName(classname);
      for (int i = 0; i < List.size(); i++) {
        Object castedbean = List.get(i);
        beanclass.cast(castedbean);

        int j = 0;
        Method m1 = beanclass.getMethod(method1.trim(), null);
        Method m2 = beanclass.getMethod(method2.trim(), null);
        Object ret1 = m1.invoke(castedbean, null);
        Object ret2 = m2.invoke(castedbean, null);
        if (!("divison".equalsIgnoreCase(dropdowndefault))) {
          Integer Int = (Integer) ret1;
          j = Int.intValue();


          if (selectedvalue == j) {
            htmlCode += "<OPTION SELECTED VALUE='" + j + "'>" + (String) ret2 + "</OPTION>";
          } else {
            htmlCode += "<OPTION VALUE='" + j + "'>" + (String) ret2 + "</OPTION>";
          }
        } else {
          if (divison != null && divison.equalsIgnoreCase((String) ret1)) {
            htmlCode += "<OPTION SELECTED VALUE='" + ((String) ret1).trim() + "'>"
                + ((String) ret2).trim() + "</OPTION>";
          } else {
            htmlCode += "<OPTION VALUE='" + ((String) ret1).trim() + "'>" + ((String) ret2).trim()
                + "</OPTION>";
          }
        }

      }

    } catch (ClassNotFoundException e) {
      System.err.println("Class.forName() can't find the class");
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      System.err.println("Method does not exist");
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      System.err.println("No permission to invoke method");
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      System.err.println("Method threw an: " + e.getTargetException());
      e.printStackTrace();
    }

    return htmlCode;
  }

  public static String getTeamHtml(LinkedList teamList, String selectedTeam) {
    String htmlCode = "<OPTION VALUE='-1'>Select a Team</OPTION>";
    User user = null;
    for (int i = 0; i < teamList.size(); i++) {
      user = (User) teamList.get(i);
      if (selectedTeam != null && !"".equals(selectedTeam)
          && Integer.parseInt(selectedTeam) == user.getBuyerId()) {
        htmlCode += "<OPTION SELECTED VALUE='" + user.getBuyerId() + "'>" + user.getUserDspn()
            + "</OPTION>";
      } else {
        htmlCode += "<OPTION VALUE='" + user.getBuyerId() + "'>" + user.getUserDspn() + "</OPTION>";
      }
    }
    return htmlCode;
  }

  // public static String getImpactType(FSCPathSelectionBean selBean, ToItemListBean itmLstBean) {
  // String htmlCode = "";
  // if (!selBean.isFSCPath()) {
  // if (itmLstBean.getFillRateType().equals("A")) {
  // htmlCode += "<OPTION SELECTED VALUE='A'>All</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='A'>All</OPTION>";
  // }
  // }
  // if (itmLstBean.getFillRateType().equals("BO")) {
  // htmlCode += "<OPTION SELECTED VALUE='BO'>BO and X/S</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='BO'>BO and X/S</OPTION>";
  // }
  // if (itmLstBean.getFillRateType().equals("NS")) {
  // htmlCode += "<OPTION SELECTED VALUE='NS'>Non-Stock Cross Ships</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='NS'>Non-Stock Cross Ships</OPTION>";
  // }
  // if (!selBean.getExcNSIItems().equalsIgnoreCase("Y")) {
  // if (itmLstBean.getFillRateType().equals("XD")) {
  // htmlCode += "<OPTION SELECTED VALUE='XD'>NSIs</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='XD'>NSIs</OPTION>";
  // }
  // }
  // if (!selBean.getExcDSItems().equalsIgnoreCase("Y")) {
  // if (itmLstBean.getFillRateType().equals("D")) {
  // htmlCode += "<OPTION SELECTED VALUE='D'>Drop Ships</OPTION>";
  // } else {
  // htmlCode += "<OPTION VALUE='D'>Drop Ships</OPTION>";
  // }
  // }
  // return htmlCode;
  // }

  public static String getSelectedDCHtml(LinkedList whseList, String selectedDC) {
    int warehouse;
    if (selectedDC == null || "".equals(selectedDC))
      selectedDC = "0";
    warehouse = Integer.parseInt(selectedDC);

    String htmlCode = "<option value='-1'>All</option>";
    for (int i = 0; i < whseList.size(); i++) {
      htmlCode += "<option value='" + ((Whse) whseList.get(i)).getBowhse() + "' "
          + (((Whse) whseList.get(i)).getBowhse() == warehouse ? "selected" : "") + ">";
      htmlCode += ((Whse) whseList.get(i)).getShipLoc() + "</option>";
    }

    return htmlCode;
  }

  public static String printBuyTeamList(LinkedList usrList, String sEntry, char type)
      throws Exception {
    User user = null;
    String htmlCode = "";
    for (int i = 0; i < usrList.size(); i++) {
      user = (User) usrList.get(i);
      if (sEntry.equals(("" + type + user.getBuyerId())))
        htmlCode += "<BR><OPTION value='" + user.getBuyerId() + "' selected class='selectedIndex'>"
            + user.getUserDspn() + "</OPTION>";
      else
        htmlCode +=
            "<BR><OPTION value='" + user.getBuyerId() + "'>" + user.getUserDspn() + "</OPTION>";
    }

    return htmlCode;
  }

  public static List getPeriodList(int minPeriod, int maxPeriod, int selectedPeriod) {
    List list = new ArrayList();

    for (int period = maxPeriod; period >= minPeriod; period--) {
      if (period % 100 == 0)
        period = period - 88;

      list.add(new Period(period, selectedPeriod == period));
    }

    return list;
  }

  public static String getDropDownHtml(List list, String attributes) {
    Option option = null;

    StringBuffer html = new StringBuffer("<select " + attributes + " >");

    for (int i = 0; i < list.size(); i++) {
      option = (Option) list.get(i);

      html.append("\n\t<option value='" + option.getKeyValue() + "' "
          + (option.isSelected() ? "selected >" : ">"));
      html.append(option.getDisplayValue() + "</option>");
    }

    html.append("\n </select>");

    return html.toString();
  }

  public static String getDropDownHtml(List list, String attributes, String selectedOption) {
    Option option = null;

    StringBuffer html = new StringBuffer("<select " + attributes + " >");
    if(attributes.indexOf("data-none-selected-text")!=-1){
        html.append("\n\t<option value=''>Please select</option>");
    }
    for (int i = 0; i < list.size(); i++) {
      option = (Option) list.get(i);      
      html.append("\n\t<option value='" + option.getKeyValue() + "' "
          + (option.getKeyValue().trim().equals(selectedOption) ? "selected >" : ">"));
      html.append(option.getDisplayValue() + "</option>");
    }

    html.append("\n </select>");

    return html.toString();
  }

  public static int getRequestIntParameter(HttpServletRequest httpServletRequest, String name) {

    int iName = 0;
    String temp = httpServletRequest.getParameter(name);
    try {
      if (temp != null && !"".trim().equals(name))
        iName = Integer.parseInt(temp.trim());
    } catch (NumberFormatException exception) {
      return 0;
    }
    return iName;
  }

  public static double getRequestDoubleParameter(HttpServletRequest httpServletRequest,
      String name) {

    double iName = 0.0;
    String temp = httpServletRequest.getParameter(name);
    try {
      if (temp != null && !"".trim().equals(name))
        iName = Double.parseDouble(temp.trim());
    } catch (NumberFormatException exception) {
      return 0.0;
    }
    return iName;
  }
}
