package com.schein.controller;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.schein.utils.FileUtils;
import com.schein.utils.LoggerUtil;
import com.schein.bean.AttachmentTable;
import com.schein.bean.EmailTable;
import com.schein.bean.IssueDetailsTable;
import com.schein.bean.IssueTable;
import com.schein.bean.LoginUser;
import com.schein.bean.MasterTable;
import com.schein.bean.PoBean;
import com.schein.bean.User;
import com.schein.dao.CustomPropertyDao;
import com.schein.dao.PoDao;
import com.schein.dao.TicketDao;
import com.schein.dao.UserManager;
import com.schein.utils.AS400Utils;
import com.schein.utils.BoUtils;
import com.schein.utils.DateTimeUtil;
import com.schein.utils.DropDownOption;
import com.schein.utils.EmailSender;
import com.schein.utils.WebUtils;

public class TicketController {

	@SuppressWarnings("unchecked")
	public void execute(WorkContext ctx) {
		final String UPLOAD_DIRECTORY = "E:\\VendorAudit\\upload\\";
		HttpServletRequest httpServletRequest = ctx.getRequest();
		HttpSession httpSession = ctx.getSession();
		LoginUser loginUser = (LoginUser) httpSession.getAttribute("loginuser");
		User curUser = loginUser.getUserInfo();
		UserManager userManager = new UserManager();

		String errorString = "";
		String filePath = "";
		String fileName = "";
		String fromEmail = "";

		Connection connection = null;

		String ponumber = WebUtils.getRequestParameter(httpServletRequest, "ponumber");
		String supplier = WebUtils.getRequestParameter(httpServletRequest, "supplier");
		String saveFlag = WebUtils.getRequestParameter(httpServletRequest, "save");
		String repeatFlag = WebUtils.getRequestParameter(httpServletRequest, "repeatFlag");

		String supplieremail = "";
		Integer controlNo = 0;
		// TODO Fetch supplier as well

		String option = WebUtils.getRequestParameter(httpServletRequest, "option");
		TicketDao ticketdao = new TicketDao();
		List ticketList = new ArrayList();
		List analystList = new ArrayList();
		List statusList = new ArrayList();
		List locationList = new ArrayList();
		List stateList = new ArrayList();

		String report = httpServletRequest.getParameter("report");

		try {

			connection = AS400Utils.getAS400DBConnection();

			fromEmail = ticketdao.getFromEmail(connection);

			if (report != null && !"".equalsIgnoreCase(report))
				LoggerUtil.log("debug", "report in ticketcontroller is : " + report);

			if ("reset".equalsIgnoreCase(option)) {
				// TODO
			}

			if ("back".equalsIgnoreCase(option)) {
				// TODO
			}

			if ("createTicket".equalsIgnoreCase(option)) {

				String freightterms = "";
				String xdock = "";
				String goodload = "";
				PoDao podao = new PoDao();
				List poList = podao.getPODetails(connection, ponumber);
				
				if(poList.size()>0){
				PoBean pobean = new PoBean();
				pobean = (PoBean) poList.get(0);

				supplier = pobean.getSupplier();

				// Fetch Control No. to be shown. Max+1

				if (repeatFlag != null && !"".equalsIgnoreCase(repeatFlag))
					controlNo = Integer.parseInt(WebUtils.getRequestParameter(httpServletRequest, "controlNo"));
				else
					controlNo = ticketdao.getControlNumber(connection);
				httpServletRequest.setAttribute("controlNo", controlNo);

				// Fetch Analyst Details
				analystList = ticketdao.getAnalysts(connection);
				httpServletRequest.setAttribute("analystList", analystList);

				// Fetch Working Status
				statusList = ticketdao.getWorkingStatus(connection, ponumber);
				httpServletRequest.setAttribute("statusList", statusList);

				// Fetch Source/ Warehouse
				locationList = ticketdao.getLocation(connection);
				httpServletRequest.setAttribute("locationList", locationList);

				// Fetch State
				stateList = ticketdao.getState(connection);
				httpServletRequest.setAttribute("stateList", stateList);

				// Fetch Status List
				statusList = ticketdao.getStatus(connection);
				httpServletRequest.setAttribute("statusList", statusList);

				httpServletRequest.setAttribute("tsmnotes", "");

				// Create ticket with new control no - this is just for
				// placeholder. User can change data as needed later
				String analyst = "";
				String pronumber = "";
				String tsmnotes = "";
				String location = "";
				String state = "";
				String status = "";

				location = ticketdao.getChosenLocation(connection, pobean.getWarehouse().toString());

				analyst = loginUser.getUserInfo().getUserName().trim();

				state = WebUtils.getRequestParameter(httpServletRequest, "state");
				DropDownOption dc1 = (DropDownOption) ticketdao.getStatus(connection).get(4);
				status = dc1.getKeyValue().toString();

				MasterTable mbean = new MasterTable();
				mbean.setControlNo(controlNo);
				mbean.setAnalyst(analyst);
				// mbean.setObserveDte();
				mbean.setPoNumber(ponumber);
				mbean.setProNumber(pronumber);
				mbean.setShipLoc(state);
				mbean.setStatus(status);
				mbean.setCarrier(0);
				mbean.setCrtPgm("Audittool");
				mbean.setCrtUser(curUser.getUserId());
				mbean.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
				mbean.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
				mbean.setUpdPgm("Audittool");
				mbean.setUpdUser(curUser.getUserId());

				httpServletRequest.setAttribute("mbean", mbean);
				// Inserting - actually creating ticket for first time in flow
				int ret = ticketdao.createTicket(mbean, connection);
				httpServletRequest.setAttribute("first", "Yes");
				httpServletRequest.setAttribute("analyst", analyst);
				httpServletRequest.setAttribute("pronumber", pronumber);

				// Freight Terms and Xdock
				xdock = ticketdao.getXdoc(connection, supplier);
				freightterms = ticketdao.getFreightTerms(connection, supplier);

				httpServletRequest.setAttribute("xdock", xdock);
				httpServletRequest.setAttribute("freightterms", freightterms);
				httpServletRequest.setAttribute("goodload", goodload);
				
				}
				else{
					httpServletRequest.setAttribute("message", "No such po. Please search again.");
				}

			}

			if ("submitticket".equalsIgnoreCase(option)) {
				// TODO
				String msg = ""; // Carries message back to ui
				String supplierexception = "";
				// String followup="";
				String followupdate = "";
				// String responded = "";
				String responsedate = "";
				String analystnotes = "";
				String location = "";
				String state = "";

				controlNo = Integer.parseInt(WebUtils.getRequestParameter(httpServletRequest, "controlNo"));

				PoDao podao = new PoDao();
				List poList = podao.getPODetails(connection, ponumber);
				PoBean pobean = new PoBean();
				pobean = (PoBean) poList.get(0);
				supplier = pobean.getSupplier();

				// Fetch form details - vendoremail
				supplieremail = (String) WebUtils.getRequestParameter(httpServletRequest, "supplieremail");
				// supexception
				supplierexception = (String) WebUtils.getRequestParameter(httpServletRequest, "supplierexception");
				// followupdate
				followupdate = (String) WebUtils.getRequestParameter(httpServletRequest, "followupdate");
				followupdate = DateTimeUtil.getYYYYMMDDDateWithoutSeparators(followupdate);
				// responsedate
				responsedate = (String) WebUtils.getRequestParameter(httpServletRequest, "responsedate");
				responsedate = DateTimeUtil.getYYYYMMDDDateWithoutSeparators(responsedate);
				// analystnotes
				analystnotes = (String) WebUtils.getRequestParameter(httpServletRequest, "analystnotes");

				// Writing followupdate and responsedate
				MasterTable mtable = ticketdao.getTicketDetails(connection, controlNo);

				BigDecimal bd1 = new BigDecimal(0);
				BigDecimal bd2 = new BigDecimal(0);

				if (followupdate != null && !"".equalsIgnoreCase(followupdate))
					bd1 = new BigDecimal(followupdate);

				if (responsedate != null && !"".equalsIgnoreCase(responsedate))
					bd2 = new BigDecimal(responsedate);

				mtable.setFollowupDate(bd1);
				mtable.setResponseDate(bd2);
				mtable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
				mtable.setUpdPgm("Audittool");
				mtable.setUpdUser(curUser.getUserId());
				ticketdao.updateTicket(mtable, connection);

				// File Attachment Preparation
				String attachments = null;
				// Getting all files for the control no
				List attList = new ArrayList();
				attList = FileUtils.getAllPhotos(connection, controlNo);
				// Converting list to comma separated string
				attachments = String.join(",", attList);

				// Prepare email
				String to = "";
				String from = ""; // Letting from be empty
				String subject = "";
				String msgBody = "";

				mtable = ticketdao.getTicketDetails(connection, controlNo);

				to = supplieremail;
				subject = supplier + ", " + mtable.getPoNumber() + " * Henry Schein Vendor Receiving Observations "
						+ mtable.getShipLoc();

				msgBody = "";

				// Message Body Creation Begin

				msgBody = "<html><body><p>Hello " + supplier
						+ " Team,</p><p>Our HSI Global Supply Chain team performed a receiving audit of your order(s) shipped to our "
						+ mtable.getShipLoc()
						+ " distribution center to ensure compliance with our <i>*shipping guidelines.</i></p><p>Let me share the results of our audit: </p><p><u><b> Item Details </b></u></p>"
						+ "<table><tr><td>Purchase Order</td><td>" + mtable.getPoNumber()
						+ "</td></tr><tr><td>Carrier</td><td>" + mtable.getCarrier()
						+ "</td></tr><tr><td>PRO/Tracking #</td><td>" + mtable.getProNumber() + "</td></tr></table> "
						+ "" + "<p><u><b> Improvement Opportunities </b></u></p>" + "<table>";
				// + "<tr><td>Delivery Issues</td><td>" + tbd +
				// "</td></tr><tr><td>Order Information
				// Issues</td><td>"+tbd+"</td></tr><tr><td>Pallet Issues
				// #</td><td>"+tbd+"</td></tr><tr><td>Shrink Wrap
				// Issues</td><td>"+tbd+"</td></tr></table> "

				// TODO Dynamically generate rows

				// TODO Put in util later - Begin
				Map ticketmap = ticketdao.getIssues(connection);
				Map ticketdetailmap = new HashMap();
				Integer tsmkey = new Integer(0);
				Integer analystkey = new Integer(0);

				// using keySet() for iteration over keys
				Iterator itr = ticketmap.keySet().iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					List list = new ArrayList();

					list = ticketdao.getSubIssues(connection, key); // TODO It
																	// returns
																	// list
																	// which
																	// needs to
																	// be then
																	// appended
					// to another list?
					String fullCsv = String.join(",", list);
					ticketdetailmap.put(key, fullCsv);
				}

				// Put in util later - End

				Iterator entries = ticketmap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					if (value.trim().equalsIgnoreCase("TSM Issue")) {
						tsmkey = Integer.parseInt((String) entry.getKey());
						continue;
					}
					if (value.trim().equalsIgnoreCase("Analyst Notes")) {
						analystkey = Integer.parseInt((String) entry.getKey());
						continue;
					}
					msgBody = msgBody + "<tr><td>" + value + "</td><td>"
							+ ticketdao.getSubForIssues(connection, controlNo, Integer.parseInt(key)) + "</td></tr>";
				}

				// TODO Find better place for this piece of code later
				if ((analystnotes != null && !"".equalsIgnoreCase(analystnotes)) && analystkey != 0) {
					// Analyst notes being written into IM0162. Not written in
					// IM0164 because there is no sub issue.
					IssueTable issueTable = new IssueTable();
					issueTable.setControlNo(controlNo);
					issueTable.setIssueComment(analystnotes);
					issueTable.setIssueType(analystkey);
					issueTable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
					issueTable.setUpdPgm("Audittool");
					issueTable.setUpdUser(curUser.getUserId());
					issueTable.setCrtPgm("Audittool");
					issueTable.setCrtUser(curUser.getUserId());
					issueTable.setTsCrte(DateTimeUtil.getCurrentTimeStamp());

					if (saveFlag != null && !"".equalsIgnoreCase(saveFlag)) {
						ticketdao.updateIssueComment(connection, issueTable);
					} else {
						String chkComment = "";

						chkComment = ticketdao.getCommentForIssues(connection, controlNo, analystkey);

						if (chkComment != null && !"".equals(chkComment))
							ticketdao.updateIssueComment(connection, issueTable);
						else
							ticketdao.addIssueComment(connection, issueTable);
					}
				}

				msgBody = msgBody + "</table> " + "<p>**TSM Notes :"
						+ ticketdao.getCommentForIssues(connection, controlNo, tsmkey) + "</p>" + ""
						+ "<p>***Analyst Notes :" + ticketdao.getCommentForIssues(connection, controlNo, analystkey)
						+ "</p>" + ""
						+ "<p>Please review and let me know what actions your team will take to improve the highlighted issues.I appreciate your collaborative spirit to work with us.</p>"
						+ "<p> - Henry Schein Global Supply Chain</p>" + ""
						+ "<p><small>Note: Any damage reported in this receiving audit is shared in the spirit of strengthening our partnership to avoid any re-occurrence.  Damage details (including: Items/Qty and/or need for RA or Credit) will be reported through normal business process by our DC if Henry Schein is unable to induct the product into inventory for sale.</small></p>"
						+ ""
						+ "<p><small>*: These guidelines ensure our DC teams receive your products efficiently allowing quick availability for our customers. Please refer to our North America Supplier Partnership Guide located on the Vendor Information Portal for shipping requirements.</p></small>"
						+ "</body></html>";

				// Message Body Creation End
				subject = subject.startsWith(",") ? subject.substring(1) : subject;

				LoggerUtil.log("debug", "Message body generated: " + msgBody);

				// Write mail to IM0165

				EmailTable emailtable = new EmailTable();
				emailtable.setControlNo(controlNo);
				emailtable.setEmailId(String.valueOf(ticketdao.getEmailId(connection, controlNo)));
				emailtable.setSubject(subject);
				emailtable.setToEmail(supplieremail);
				emailtable.setMessage(msgBody);
				emailtable.setCrtPgm("Audittool");
				emailtable.setCrtUser(curUser.getUserId());
				emailtable.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
				emailtable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
				emailtable.setUpdPgm("Audittool");
				emailtable.setUpdUser(curUser.getUserId());

				from = fromEmail;

				if (saveFlag != null && !"".equalsIgnoreCase(saveFlag)) {
					msg = "Successfully saved analyst notes and other information. ";
				} else {

					int emailret = ticketdao.addEmail(connection, emailtable);

					try {
						EmailSender.sendEmailWithAttachment(to, from, subject, msgBody, attachments);
					} catch (Exception ex) {
						LoggerUtil.log("error", "Error sending email inside TicketController");
					}
					// If email is sent successfully and data is written
					// correctly to relevant tables, set message as Success
					msg = "Email successfully sent for Control No. " + controlNo;

				}
				httpServletRequest.setAttribute("message", msg);

				// Set PO number and supplier details as well as other details
				httpServletRequest.setAttribute("controlNo", controlNo);
				List opentickets = new ArrayList();
				opentickets = ticketdao.getOpenTickets(loginUser.getUserInfo().getUserId());
				httpServletRequest.setAttribute("opentickets", opentickets);
				httpServletRequest.setAttribute("supplieremail", supplieremail);

				httpServletRequest.setAttribute("supplierexception", supplierexception);
				// httpServletRequest.setAttribute("followup", followup);
				httpServletRequest.setAttribute("followupdate", followupdate);
				httpServletRequest.setAttribute("responsedate", responsedate);
				// httpServletRequest.setAttribute("responsedate",
				// responsedate);
				httpServletRequest.setAttribute("analystnotes", analystnotes);
			}

			if ("editTicket".equalsIgnoreCase(option)) {// Fetches complete
														// details of one
														// ticket.
				String freightterms = "";
				String xdock = "";
				String goodload = "";
				// TODO
				controlNo = Integer.parseInt(WebUtils.getRequestParameter(httpServletRequest, "controlNo"));
				MasterTable mbean = ticketdao.getTicketDetails(connection, controlNo); // Ticket
																						// Details
				// List conversationList = ticketdao.getEmails(connection,
				// controlNo); //Emails
				// List attachmentList = ticketdao.getAttachments(connection,
				// controlNo); //Attachments
				String issues = ""; // Issues - see below section

				// TODO Place in a single method later - since the below code is
				// used in multiple places
				Map ticketmap = ticketdao.getIssues(connection);
				Map ticketsubmap = new HashMap();
				String fullList = "";
				Integer tsmkey = new Integer(0);
				String tsmnotes = "";

				Iterator entries = ticketmap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					String subIssue = "";
					if (value.trim().equalsIgnoreCase("TSM Issue")) {
						tsmkey = Integer.parseInt((String) entry.getKey());
						continue;
					}
					if (value.trim().equalsIgnoreCase("Analyst Notes")) {
						// analystkey = Integer.parseInt((String)
						// entry.getKey());
						continue;
					}
					subIssue = ticketdao.getSubForIssues(connection, controlNo, Integer.parseInt(key));
					ticketsubmap.put(key, subIssue);

					fullList = fullList + "," + ticketdao.getSubForIssues(connection, controlNo, Integer.parseInt(key));
				}
				fullList = fullList.replaceFirst("^,", "");

				issues = fullList;

				httpServletRequest.setAttribute("mbean", mbean);
				httpServletRequest.setAttribute("controlNo", controlNo);
				httpServletRequest.setAttribute("ticketmap", ticketmap);
				httpServletRequest.setAttribute("ticketsubmap", ticketsubmap);
				// httpServletRequest.setAttribute("conversationList",
				// conversationList);
				// httpServletRequest.setAttribute("attachmentList",
				// attachmentList);
				httpServletRequest.setAttribute("issues", issues);

				tsmnotes = ticketdao.getCommentForIssues(connection, controlNo, tsmkey);

				httpServletRequest.setAttribute("tsmnotes", tsmnotes);

				// Fetch Analyst Details
				analystList = ticketdao.getAnalysts(connection);
				httpServletRequest.setAttribute("analystList", analystList);

				ponumber = mbean.getPoNumber();
				PoDao podao = new PoDao();
				List poList = podao.getPODetails(connection, ponumber);

				PoBean pobean = (PoBean) poList.get(0);
				supplier = pobean.getSupplier();

				// Fetch Working Status
				statusList = ticketdao.getWorkingStatus(connection, ponumber);
				httpServletRequest.setAttribute("statusList", statusList);

				// Fetch Ship Location
				locationList = ticketdao.getLocation(connection);
				httpServletRequest.setAttribute("locationList", locationList);

				// Fetch state location
				stateList = ticketdao.getState(connection);
				httpServletRequest.setAttribute("stateList", stateList);

				// Fetch status List
				statusList = ticketdao.getStatus(connection);
				httpServletRequest.setAttribute("statusList", statusList);

				httpServletRequest.setAttribute("flow", "edit");
				String loc = "";
				for (int i = 0; i < locationList.size(); i++) {
					DropDownOption d = (DropDownOption) locationList.get(i);
					if (d.getKeyValue().trim().equalsIgnoreCase(mbean.getShipLoc().trim())) {
						loc = d.getDisplayValue();
					}
				}
				String status = "";
				status = mbean.getStatus();
				httpServletRequest.setAttribute("status", status);

				// Freight Terms and Xdock
				xdock = ticketdao.getXdoc(connection, supplier); // locked
				freightterms = WebUtils.getRequestParameter(httpServletRequest, "freightterms");
				if (!(freightterms != null && !"".equals(freightterms)))
					freightterms = ticketdao.getFreightTerms(connection, supplier);
				goodload = mbean.getGoodLoad();

				httpServletRequest.setAttribute("xdock", xdock);
				httpServletRequest.setAttribute("freightterms", freightterms);
				httpServletRequest.setAttribute("goodload", goodload);
				

			} // Edit Ticket

			if ("editTicket2".equalsIgnoreCase(option)  || "backToMain".equalsIgnoreCase(option)) {

				controlNo = Integer.parseInt(WebUtils.getRequestParameter(httpServletRequest, "controlNo"));
				// else
				// controlNo = ticketdao.getControlNumber(connection);

				// Store everything coming in from form into IM0160

				String analyst = "";
				String status = "";
				String location = "";
				String obsdate = "";
				String pronumber = "";
				String tsmnotes = "";
				String firstflag = "";
				String prev = "";
				String state = "";
				String goodload = "";
				String freightterms = "";
				String carrier = "";

				// Fetch form details
				analyst = WebUtils.getRequestParameter(httpServletRequest, "analyst");
				status = WebUtils.getRequestParameter(httpServletRequest, "status");
				location = WebUtils.getRequestParameter(httpServletRequest, "location");
				obsdate = WebUtils.getRequestParameter(httpServletRequest, "obsdate");
				obsdate = DateTimeUtil.getYYYYMMDDDateWithoutSeparators(obsdate);
				pronumber = WebUtils.getRequestParameter(httpServletRequest, "pronumber");
				tsmnotes = WebUtils.getRequestParameter(httpServletRequest, "tsmnotes");
				firstflag = WebUtils.getRequestParameter(httpServletRequest, "firstflag");
				prev = WebUtils.getRequestParameter(httpServletRequest, "prev");
				state = WebUtils.getRequestParameter(httpServletRequest, "state");
				goodload = WebUtils.getRequestParameter(httpServletRequest, "goodload");
				freightterms = WebUtils.getRequestParameter(httpServletRequest, "freightterms");
				carrier = WebUtils.getRequestParameter(httpServletRequest, "carrier");
				pronumber=pronumber.trim();
				if (goodload.equalsIgnoreCase("on"))
					goodload = "Y";
				else
					goodload = "N";

				BigDecimal obd = new BigDecimal(0);

				if (obsdate != null && !"".equalsIgnoreCase(obsdate))
					obd = new BigDecimal(obsdate);

				MasterTable mbean = new MasterTable();
				mbean.setControlNo(controlNo);
				mbean.setAnalyst(analyst);
				mbean.setObserveDte(obd);
				mbean.setPoNumber(ponumber);
				mbean.setProNumber(pronumber);
				mbean.setShipLoc(state);
				mbean.setStatus(status);
				mbean.setGoodLoad(goodload);
				mbean.setCarrier(carrier.matches("-?\\d+") ? Integer.parseInt(carrier) : 0);
				mbean.setCrtPgm("Audittool");
				mbean.setCrtUser(curUser.getUserId());
				mbean.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
				mbean.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
				mbean.setUpdPgm("Audittool");
				mbean.setUpdUser(curUser.getUserId());

				// Inserting - actually creating ticket for first time in flow
				int ret = 0;

				if (!(prev != null && !"".equals(prev)))
					ret = ticketdao.updateTicket(mbean, connection); // TODO

				// Fetch Issue list
				Map ticketmap = ticketdao.getIssues(connection);
				Map ticketdetailmap = new HashMap();
				Map ticketsubmap = new HashMap();

				String fullList = "";

				// using keySet() for iteration over keys
				Iterator itr = ticketmap.keySet().iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					List list = new ArrayList();
					String subIssue = "";

					list = ticketdao.getSubIssues(connection, key); // TODO It
																	// returns
																	// list
																	// which
																	// needs to
																	// be then
																	// appended
					subIssue = ticketdao.getSubForIssues(connection, controlNo, Integer.parseInt(key));
					// to another list?
					String fullCsv = String.join(",", list);
					ticketdetailmap.put(key, fullCsv);
					ticketsubmap.put(key, subIssue);
				}

				if (tsmnotes != null && !"".equalsIgnoreCase(tsmnotes)) {
					// TSM Notes persist using entryset
					Iterator itr2 = ticketmap.entrySet().iterator();
					String key = "";
					while (itr2.hasNext()) {
						Map.Entry entry = (Entry) itr2.next();
						String value = (String) entry.getValue();
						if (value.trim().equalsIgnoreCase("TSM Issue"))
							key = (String) entry.getKey();
					}

					// TSM notes being written into IM0162. Not written in
					// IM0164 because there is no sub issue.
					IssueTable issueTable = new IssueTable();
					issueTable.setControlNo(controlNo);
					issueTable.setIssueComment(tsmnotes);
					issueTable.setIssueType(Integer.parseInt(key));
					issueTable.setCrtPgm("Audittool");
					issueTable.setCrtUser(curUser.getUserId());
					issueTable.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
					issueTable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
					issueTable.setUpdPgm("Audittool");
					issueTable.setUpdUser(curUser.getUserId());
					ticketdao.updateIssueComment(connection, issueTable);
				}

				// Now we have 2 maps for the front end - ticket and
				// ticketdetail
				httpServletRequest.setAttribute("ticketmap", ticketmap);
				httpServletRequest.setAttribute("ticketdetailmap", ticketdetailmap);
				httpServletRequest.setAttribute("ticketsubmap", ticketsubmap);

				List carrierlist = ticketdao.getCarrier(connection);
				httpServletRequest.setAttribute("carrierlist", carrierlist);

				// Fetching fresh mbean
				mbean = ticketdao.getTicketDetails(connection, controlNo);

				// Set PO number and supplier details as well as other details
				httpServletRequest.setAttribute("controlNo", controlNo);
				httpServletRequest.setAttribute("analyst", analyst);
				httpServletRequest.setAttribute("status", status);
				httpServletRequest.setAttribute("location", location);
				httpServletRequest.setAttribute("obsdate", obsdate);
				httpServletRequest.setAttribute("flow", "edit");
				httpServletRequest.setAttribute("tsmnotes", tsmnotes);
				httpServletRequest.setAttribute("pronumber", pronumber);
				httpServletRequest.setAttribute("mbean", mbean);
				httpServletRequest.setAttribute("state", state);
				httpServletRequest.setAttribute("goodload", goodload);
				httpServletRequest.setAttribute("freightterms", freightterms);
				httpServletRequest.setAttribute("carrier", mbean.getCarrier());

				if (saveFlag != null && !"".equals(saveFlag)) {
					// Fetch Analyst Details
					analystList = ticketdao.getAnalysts(connection);
					httpServletRequest.setAttribute("analystList", analystList);

					// Fetch Working Status
					statusList = ticketdao.getWorkingStatus(connection, ponumber);
					httpServletRequest.setAttribute("statusList", statusList);

					// Fetch Ship Location
					locationList = ticketdao.getLocation(connection);
					httpServletRequest.setAttribute("locationList", locationList);

					stateList = ticketdao.getState(connection);
					httpServletRequest.setAttribute("stateList", stateList);

					statusList = ticketdao.getStatus(connection);
					httpServletRequest.setAttribute("statusList", statusList);

					httpServletRequest.setAttribute("first", firstflag);
				} else {
					if (firstflag != null && !"".equals(firstflag)) {
						httpServletRequest.setAttribute("flow", "");
						httpServletRequest.setAttribute("first", "");
					}
				}
				
				if(option.equalsIgnoreCase("backToMain")){
					List opentickets = new ArrayList();
					opentickets = ticketdao.getOpenTickets(loginUser.getUserInfo().getUserId());
					httpServletRequest.setAttribute("opentickets", opentickets);
					
				}

			} // editTicket2

			if ("editTicket3".equalsIgnoreCase(option)) {

				controlNo = Integer.parseInt(WebUtils.getRequestParameter(httpServletRequest, "controlNo"));

				Map ticketmap = ticketdao.getIssues(connection);
				Map ticketdetailmap = new HashMap();
				Map ticketsubmap = new HashMap();

				IssueTable issueTable = new IssueTable();
				IssueDetailsTable issueDetailsTable = new IssueDetailsTable();
				List issueIns = new ArrayList();
				List issueDet = new ArrayList();

				Iterator entries = ticketmap.entrySet().iterator();

				while (entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					String issuename = key + "issues";
					String comment = key + "comment";
					comment = (String) WebUtils.getRequestParameter(httpServletRequest, comment);
					String[] issueDetails = httpServletRequest.getParameterValues(issuename);
					List issueList = new ArrayList();
					if (issueDetails != null && issueDetails.length > 0)
						issueList = Arrays.asList(issueDetails);
					String details = "";

					for (int i = 0; i < issueList.size(); i++) {
						details = (String) issueList.get(i);

						// int subIssue = ticketdao.getSubIssueSv(connection,
						// details);
						// Above code is commented as it is no longer needed. We
						// extract the integer value from details.

						int subIssue = Integer.parseInt(details.substring(0, 2));

						issueTable = new IssueTable();
						issueDetailsTable = new IssueDetailsTable();

						issueTable.setControlNo(controlNo);
						issueTable.setIssueType(Integer.parseInt(key));
						issueTable.setIssueComment(comment);
						issueTable.setCrtPgm("Audittool");
						issueTable.setCrtUser(curUser.getUserId());
						issueTable.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
						issueTable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
						issueTable.setUpdPgm("Audittool");
						issueTable.setUpdUser(curUser.getUserId());
						issueIns.add(ticketdao.updateIssueComment(connection, issueTable));

						issueDetailsTable.setControlNo(controlNo);
						issueDetailsTable.setIssueType(Integer.parseInt(key));
						issueDetailsTable.setIssueSubType(subIssue);
						issueDetailsTable.setCrtPgm("Audittool");
						issueDetailsTable.setCrtUser(curUser.getUserId());
						issueDetailsTable.setTsCrte(DateTimeUtil.getCurrentTimeStamp());
						issueDetailsTable.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
						issueDetailsTable.setUpdPgm("Audittool");
						issueDetailsTable.setUpdUser(curUser.getUserId());

						if (i == 0) {
							ticketdao.deleteIssueType(connection, issueDetailsTable);
						}

						issueDet.add(ticketdao.updateIssueType(connection, issueDetailsTable));
					}
				}

				// using keySet() for iteration over keys
				Iterator itr = ticketmap.keySet().iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					String subIssue = "";
					List list = new ArrayList();

					list = ticketdao.getSubIssues(connection, key); // TODO It
																	// returns
																	// list
																	// which
																	// needs to
																	// be then
																	// appended
					// to another list?
					String fullCsv = String.join(",", list);
					subIssue = ticketdao.getSubForIssues(connection, controlNo, Integer.parseInt(key));
					ticketdetailmap.put(key, fullCsv);
					ticketsubmap.put(key, subIssue);
				}

				if (issueIns.contains(0) || issueDet.contains(0)) {
					httpServletRequest.setAttribute("message", "Unable to update values");
					LoggerUtil.log("error", "Unable to update issue and sub issue in IM0162 and IM0164");
					// rollback?
				} else {
					httpServletRequest.setAttribute("message", "Issues updated successfully");
					LoggerUtil.log("error", "Updated issue and sub issue in IM0162 and IM0164");
				}

				// Set PO number and supplier details as well as other details
				httpServletRequest.setAttribute("controlNo", controlNo);

				// Get Carrier and update
				String carrier = "";
				carrier = WebUtils.getRequestParameter(httpServletRequest, "carrier");
				MasterTable mbean = ticketdao.getTicketDetails(connection, controlNo);
				mbean.setCarrier(carrier.matches("-?\\d+") ? Integer.parseInt(carrier) : 0);
				mbean.setTsUpd(DateTimeUtil.getCurrentTimeStamp());
				mbean.setUpdPgm("Audittool");
				mbean.setUpdUser(curUser.getUserId());
				ticketdao.updateTicket(mbean, connection);

				// Check save flag and populate required fields for frontend
				if (saveFlag != null && !"".equals(saveFlag)) {
					httpServletRequest.setAttribute("repeat", "Yes");
					httpServletRequest.setAttribute("ticketmap", ticketmap);
					httpServletRequest.setAttribute("ticketdetailmap", ticketdetailmap);
					httpServletRequest.setAttribute("ticketsubmap", ticketsubmap);
					httpServletRequest.setAttribute("carrier", Integer.parseInt(carrier));
				}

				httpServletRequest.setAttribute("flow", "edit");
				List fileList = new ArrayList();
				fileList = ticketdao.getFilesList(connection, controlNo);
				httpServletRequest.setAttribute("fileList", fileList);

				// Fetch supplier email
				PoDao podao = new PoDao();
				ponumber = WebUtils.getRequestParameter(httpServletRequest, "ponumber");
				if (!(ponumber != null && !"".equals(ponumber)))
					ponumber = ticketdao.getTicketDetails(connection, controlNo).getPoNumber();

				List poList = podao.getPODetails(connection, ponumber);

				PoBean pobean = (PoBean) poList.get(0);
				supplier = pobean.getSupplier();
				supplieremail = ticketdao.getSupplierEmail(connection, supplier);

				List carrierlist = ticketdao.getCarrier(connection);
				httpServletRequest.setAttribute("carrierlist", carrierlist);
			} // editTicket3

			if ("editTicket4".equalsIgnoreCase(option)) {
				// Files upload begins
				if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
					// TODO Have to get count based on MAX of file

					try {
						List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory())
								.parseRequest(httpServletRequest);
						int count = 0;
						count = ticketdao.getLineNo(connection, controlNo);
						int err = 0;
						for (FileItem item : multiparts) {

							if (!item.isFormField() && item.getSize() > 0
									&& (item.getName() != null || !item.getName().isEmpty())) {
								String name = new File(item.getName()).getName();
								File storeLocation = ((DiskFileItem) item).getStoreLocation();
								fileName = UPLOAD_DIRECTORY + File.separator + "Image_" + controlNo + "_" + count;
								File filecheck = new File(fileName);
								LoggerUtil.log("debug", "if file exists: " + filecheck.exists());
								item.write(filecheck);
								LoggerUtil.log("debug", "written successfully");

								err = FileUtils.uploadPhoto(connection, controlNo, count, curUser);

								if (err > 0) {
									// id=errorString.substring(7,
									// errorString.length());
									errorString = "File Uploaded Successfully";
								} else {
									errorString = "File Not Uploaded";
									LoggerUtil.log("error", "File Not Uploaded with control no: " + controlNo
											+ " and lineNo: " + count);
								}
								LoggerUtil.log("debug", "uploaded successfully " + errorString);

								httpServletRequest.setAttribute("errorString", errorString);
								count++;
							} else {
								String field = item.getFieldName();
								if (field.equalsIgnoreCase("controlNo"))
									controlNo = Integer.valueOf(item.getString());
								if (field.equalsIgnoreCase("ponumber"))
									ponumber = item.getString();
							}

						}

					} catch (Exception ex) {
						httpServletRequest.setAttribute("errorString", "File Upload Failed due to " + ex);
						LoggerUtil.log("error", "Error is: " + ex.getCause());
					}

				} else {
					httpServletRequest.setAttribute("errorString",
							"Sorry this Servlet only handles file upload request");
				}
				// Files upload ends

				if (saveFlag != null && !"".equals(saveFlag)) {
					httpServletRequest.setAttribute("repeat", "Yes");
				}

				List fileList = new ArrayList();
				fileList = ticketdao.getFilesList(connection, controlNo);
				httpServletRequest.setAttribute("fileList", fileList);

				// Set PO number and supplier details as well as other details
				httpServletRequest.setAttribute("controlNo", controlNo);
				httpServletRequest.setAttribute("supplieremail", supplieremail);
				httpServletRequest.setAttribute("flow", "edit");

				// Fetch supplier email
				PoDao podao = new PoDao();
				List poList = podao.getPODetails(connection, ponumber);

				PoBean pobean = (PoBean) poList.get(0);
				supplier = pobean.getSupplier();
				String supplierexception = ticketdao.getSupplierException(connection, supplier);

				// Supplier email should be the email that was entered
				// previously.
				List elist = ticketdao.getEmails(connection, controlNo);
				if (elist.size() > 0) {
					EmailTable latestMail = (EmailTable) elist.get(0);
					latestMail = ticketdao.getEmailDetail(connection, controlNo, latestMail.getEmailId());
					supplieremail = latestMail.getToEmail().toString();
				} else {
					supplieremail = ticketdao.getSupplierEmail(connection, supplier);
				}

				// After file upload - fetch data for next screen as well
				httpServletRequest.setAttribute("supplieremail", supplieremail);

				// httpServletRequest.setAttribute("supexception",
				// supexception);
				// httpServletRequest.setAttribute("followup", followup);
				// httpServletRequest.setAttribute("followupdate",
				// followupdate);
				// httpServletRequest.setAttribute("responded", responded);
				// httpServletRequest.setAttribute("responsedate",
				// responsedate);
				String analystnotes = "";
				Map ticketmap = ticketdao.getIssues(connection);
				Map ticketdetailmap = new HashMap();
				Integer analystkey = new Integer(0);
				Iterator entries = ticketmap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					if (value.trim().equalsIgnoreCase("Analyst Notes")) {
						analystkey = Integer.parseInt((String) entry.getKey());
						continue;
					}
				}
				analystnotes = ticketdao.getCommentForIssues(connection, controlNo, analystkey);
				httpServletRequest.setAttribute("analystnotes", analystnotes);

				// Writing followupdate and responsedate
				String followupdate = "";
				String responsedate = "";
				MasterTable mtable = ticketdao.getTicketDetails(connection, controlNo);
				// followupdate
				followupdate = (String) WebUtils.getRequestParameter(httpServletRequest, "followupdate");
				// followupdate =
				// DateTimeUtil.getYYYYMMDDDateWithoutSeparators(followupdate);
				// responsedate
				responsedate = (String) WebUtils.getRequestParameter(httpServletRequest, "responsedate");
				// responsedate =
				// DateTimeUtil.getYYYYMMDDDateWithoutSeparators(responsedate);

				// Reading followupdate and responsedate
				mtable = ticketdao.getTicketDetails(connection, controlNo);
				httpServletRequest.setAttribute("followupdate",
						mtable.getFollowupDate() != null ? mtable.getFollowupDate().toString() : "0");
				httpServletRequest.setAttribute("responsedate",
						mtable.getResponseDate() != null ? mtable.getResponseDate().toString() : "0");
				httpServletRequest.setAttribute("supplierexception", supplierexception);

			}

//			if ("backToMain".equalsIgnoreCase(option)) {
//				List opentickets = new ArrayList();
//				opentickets = ticketdao.getOpenTickets(loginUser.getUserInfo().getUserId());
//				httpServletRequest.setAttribute("opentickets", opentickets);
//			}

		}

		catch (Exception ex) {

			LoggerUtil.log("error", "Error while handling form: ");
			ex.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LoggerUtil.log("error", "Error closing connection");
				e.printStackTrace();
			}
		}

		httpServletRequest.setAttribute("ponumber", ponumber);
		httpServletRequest.setAttribute("supplier", supplier);
		httpServletRequest.setAttribute("loginUser", loginUser);
	}

}
