package org.mifos.application.customer.struts.actionforms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.mifos.application.customer.util.helpers.CustomerConstants;
import org.mifos.application.util.helpers.Methods;
import org.mifos.framework.struts.actionforms.BaseActionForm;
import org.mifos.framework.util.helpers.Money;
import org.mifos.framework.util.helpers.StringUtils;

public class CustHistoricalDataActionForm extends BaseActionForm {
	
	private String mfiJoiningDate;

	private String productName;

	private String loanAmount;

	private String totalAmountPaid;

	private String interestPaid;

	private String missedPaymentsCount;

	private String totalPaymentsCount;

	private String commentNotes;

	private String loanCycleNumber;

	private String type;

	public String getCommentNotes() {
		return commentNotes;
	}

	public void setCommentNotes(String commentNotes) {
		this.commentNotes = commentNotes;
	}

	public String getInterestPaid() {
		return interestPaid;
	}

	public Money getInterestPaidValue() {
		return getMoney(getInterestPaid());
	}

	public void setInterestPaid(String interestPaid) {
		this.interestPaid = interestPaid;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public Money getLoanAmountValue() {
		return getMoney(getLoanAmount());
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanCycleNumber() {
		return loanCycleNumber;
	}

	public Integer getLoanCycleNumberValue() {
		return getIntegerValue(getLoanCycleNumber());
	}

	public void setLoanCycleNumber(String loanCycleNumber) {
		this.loanCycleNumber = loanCycleNumber;
	}

	public String getMfiJoiningDate() {
		return mfiJoiningDate;
	}

	public void setMfiJoiningDate(String mfiJoiningDate) {
		this.mfiJoiningDate = mfiJoiningDate;
	}

	public String getMissedPaymentsCount() {
		return missedPaymentsCount;
	}

	public Integer getMissedPaymentsCountValue() {
		return getIntegerValue(getMissedPaymentsCount());
	}

	public void setMissedPaymentsCount(String missedPaymentsCount) {
		this.missedPaymentsCount = missedPaymentsCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public Money getTotalAmountPaidValue() {
		return getMoney(getTotalAmountPaid());
	}

	public void setTotalAmountPaid(String totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public String getTotalPaymentsCount() {
		return totalPaymentsCount;
	}

	public Integer getTotalPaymentsCountValue() {
		return getIntegerValue(getTotalPaymentsCount());
	}

	public void setTotalPaymentsCount(String totalPaymentsCount) {
		this.totalPaymentsCount = totalPaymentsCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String methodCalled = request.getParameter(Methods.method.toString());
		ActionErrors errors = new ActionErrors();
		if(null !=methodCalled) {
			if (Methods.previewHistoricalData.toString().equals(methodCalled)) {
				errors = handlePreviewValidations(request,errors);
			}
		}
		if (null != errors && !errors.isEmpty()) {
			request.setAttribute(Globals.ERROR_KEY, errors);
			request.setAttribute("methodCalled", methodCalled);
		}
		return errors;
	}

	private ActionErrors handlePreviewValidations(HttpServletRequest request,ActionErrors errors) {
		if(StringUtils.isNullAndEmptySafe(getCommentNotes())) {
			if (getCommentNotes().length() > CustomerConstants.HISTORICALDATA_COMMENT_LENGTH) {
				errors.add(CustomerConstants.MAXIMUM_LENGTH, new ActionMessage(
						CustomerConstants.MAXIMUM_LENGTH, CustomerConstants.HISTORICALDATA_NOTES,
						CustomerConstants.HISTORICALDATA_COMMENT_LENGTH));
			}
		}
		return errors;
	}
}
