/**

 * LoanAccountActionForm.java    version: 1.0

 

 * Copyright (c) 2005-2006 Grameen Foundation USA

 * 1029 Vermont Avenue, NW, Suite 400, Washington DC 20005

 * All rights reserved.

 

 * Apache License 
 * Copyright (c) 2005-2006 Grameen Foundation USA 
 * 

 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the 

 * License. 
 * 
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an explanation of the license 

 * and how it is applied.  

 *

 */
package org.mifos.application.accounts.loan.struts.actionforms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.mifos.application.accounts.loan.exceptions.LoanExceptionConstants;
import org.mifos.application.accounts.loan.util.helpers.LoanConstants;
import org.mifos.application.accounts.util.helpers.AccountState;
import org.mifos.application.configuration.util.helpers.ConfigurationConstants;
import org.mifos.application.fees.business.FeeView;
import org.mifos.application.productdefinition.business.LoanOfferingBO;
import org.mifos.application.util.helpers.Methods;
import org.mifos.application.util.helpers.YesNoFlag;
import org.mifos.framework.exceptions.PropertyNotFoundException;
import org.mifos.framework.struts.actionforms.BaseActionForm;
import org.mifos.framework.struts.tags.DateHelper;
import org.mifos.framework.util.helpers.Money;
import org.mifos.framework.util.helpers.SessionUtils;
import org.mifos.framework.util.helpers.StringUtils;

public class LoanAccountActionForm extends BaseActionForm {

	public LoanAccountActionForm() {
		super();
		defaultFees = new ArrayList<FeeView>();
		additionalFees = new ArrayList<FeeView>();
	}

	private String accountId;

	private String globalAccountNum;

	private String prdOfferingName;

	private String accountName;

	private String accountTypeId;

	private String customerId;

	private String prdOfferingId;

	private String loanAmount;

	private String interestRate;

	private String noOfInstallments;

	private String disbursementDate;

	private String intDedDisbursement;

	private String loanOfferingFund;

	private String gracePeriodDuration;

	private String businessActivityId;

	private String collateralTypeId;

	private String collateralNote;

	private List<FeeView> defaultFees;

	private List<FeeView> additionalFees;

	private String stateSelected;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getGlobalAccountNum() {
		return globalAccountNum;
	}

	public void setGlobalAccountNum(String globalAccountNum) {
		this.globalAccountNum = globalAccountNum;
	}

	public String getPrdOfferingName() {
		return prdOfferingName;
	}

	public void setPrdOfferingName(String prdOfferingName) {
		this.prdOfferingName = prdOfferingName;
	}

	public String getPrdOfferingId() {
		return prdOfferingId;
	}

	public void setPrdOfferingId(String prdOfferingId) {
		this.prdOfferingId = prdOfferingId;
	}

	public String getBusinessActivityId() {
		return businessActivityId;
	}

	public void setBusinessActivityId(String businessActivityId) {
		this.businessActivityId = businessActivityId;
	}

	public String getCollateralNote() {
		return collateralNote;
	}

	public void setCollateralNote(String collateralNote) {
		this.collateralNote = collateralNote;
	}

	public String getCollateralTypeId() {
		return collateralTypeId;
	}

	public void setCollateralTypeId(String collateralTypeId) {
		this.collateralTypeId = collateralTypeId;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getGracePeriodDuration() {
		return gracePeriodDuration;
	}

	public void setGracePeriodDuration(String gracePeriodDuration) {
		this.gracePeriodDuration = gracePeriodDuration;
	}

	public String getIntDedDisbursement() {
		return intDedDisbursement;
	}

	public void setIntDedDisbursement(String intDedDisbursement) {
		this.intDedDisbursement = intDedDisbursement;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanOfferingFund() {
		return loanOfferingFund;
	}

	public void setLoanOfferingFund(String loanOfferingFund) {
		this.loanOfferingFund = loanOfferingFund;
	}

	public String getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(String noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public List<FeeView> getAdditionalFees() {
		return additionalFees;
	}

	public void setAdditionalFees(List<FeeView> additionalFees) {
		this.additionalFees = additionalFees;
	}

	public List<FeeView> getDefaultFees() {
		return defaultFees;
	}

	public void setDefaultFees(List<FeeView> defaultFees) {
		this.defaultFees = defaultFees;
	}

	public String getStateSelected() {
		return stateSelected;
	}

	public void setStateSelected(String stateSelected) {
		this.stateSelected = stateSelected;
	}

	public AccountState getState() throws PropertyNotFoundException {
		return AccountState.getStatus(getShortValue(getStateSelected()));
	}

	public Integer getCustomerIdValue() {
		return getIntegerValue(getCustomerId());
	}

	public Short getPrdOfferingIdValue() {
		return getShortValue(getPrdOfferingId());
	}

	public Short getGracePeriodDurationValue() {
		return getShortValue(getGracePeriodDuration());
	}

	public Money loanAmountValue() {
		return getMoney(getLoanAmount());
	}

	public Short getNoOfInstallmentsValue() {
		return getShortValue(getNoOfInstallments());
	}

	public Date getDisbursementDateValue(Locale locale) {
		return DateHelper.getLocaleDate(locale, getDisbursementDate());
	}

	public boolean isInterestDedAtDisbValue() {
		return getBooleanValue(getIntDedDisbursement());
	}

	public Double getInterestDoubleValue() {
		return getDoubleValue(getInterestRate());
	}

	public Short getLoanOfferingFundValue() {
		return getShortValue(getLoanOfferingFund());
	}

	public Integer getBusinessActivityIdValue() {
		return getIntegerValue(getBusinessActivityId());
	}

	public Short getCollateralTypeIdValue() {
		return getShortValue(getCollateralTypeId());
	}

	public FeeView getDefaultFee(int i) {
		while (i >= defaultFees.size()) {
			defaultFees.add(new FeeView());
		}
		return (FeeView) (defaultFees.get(i));
	}

	public List<FeeView> getFeesToApply() {
		List<FeeView> feesToApply = new ArrayList<FeeView>();
		for (FeeView fee : getAdditionalFees())
			if (fee.getFeeIdValue() != null)
				feesToApply.add(fee);
		for (FeeView fee : getDefaultFees())
			if (!fee.isRemoved())
				feesToApply.add(fee);
		return feesToApply;
	}

	public FeeView getSelectedFee(int index) {
		while (index >= additionalFees.size())
			additionalFees.add(new FeeView());
		return (FeeView) additionalFees.get(index);
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		String method = request.getParameter(Methods.method.toString());
		if (method.equals(Methods.schedulePreview.toString())) {
			intDedDisbursement = null;
			for (int i = 0; i < defaultFees.size(); i++) {
				// if an already checked fee is unchecked then the value set to
				// 0
				if (request.getParameter("defaultFee[" + i + "].feeRemoved") == null) {
					defaultFees.get(i).setFeeRemoved(YesNoFlag.NO.getValue());
				}
			}
		}

	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String method = request.getParameter(Methods.method.toString());
		ActionErrors errors = new ActionErrors();
		if (method.equals(Methods.getPrdOfferings.toString()))
			checkValidationForGetPrdOfferings(errors);
		else if (method.equals(Methods.load.toString()))
			checkValidationForLoad(errors);
		else if (method.equals(Methods.schedulePreview.toString()))
			checkValidationForSchedulePreview(errors, request);
		if (!errors.isEmpty()) {
			request.setAttribute("methodCalled", method);
		}
		return errors;
	}

	private void checkValidationForGetPrdOfferings(ActionErrors errors) {
		if (StringUtils.isNullOrEmpty(getCustomerId()))
			addError(errors, LoanConstants.CUSTOMER,
					LoanConstants.CUSTOMERNOTSELECTEDERROR,
					ConfigurationConstants.CLIENT, ConfigurationConstants.GROUP);
	}

	private void checkValidationForLoad(ActionErrors errors) {
		checkValidationForGetPrdOfferings(errors);
		if (StringUtils.isNullOrEmpty(getPrdOfferingId()))
			addError(errors, LoanConstants.PRDOFFERINGID,
					LoanConstants.LOANOFFERINGNOTSELECTEDERROR,
					ConfigurationConstants.LOAN, LoanConstants.INSTANCENAME);
	}

	private void checkValidationForSchedulePreview(ActionErrors errors,
			HttpServletRequest request) {
		LoanOfferingBO loanOffering = (LoanOfferingBO) request.getSession()
				.getAttribute(LoanConstants.LOANOFFERING);
		checkForMinMax(errors, getLoanAmount(),
				loanOffering.getMaxLoanAmount(), loanOffering
						.getMinLoanAmount(), LoanConstants.LOANAMOUNT);
		checkForMinMax(errors, getInterestRate(), loanOffering
				.getMaxInterestRate(), loanOffering.getMinInterestRate(),
				LoanConstants.INTERESTRATE);
		checkForMinMax(errors, getNoOfInstallments(), loanOffering
				.getMaxNoInstallments().doubleValue(), loanOffering
				.getMinNoInstallments().doubleValue(), LoanConstants.NO_OF_INST);
		if (StringUtils.isNullOrEmpty(getDisbursementDate())) {
			addError(errors, "Proposed/Actual disbursal date",
					"errors.validandmandatory",
					"Proposed/Actual disbursal date");
		}
		if (((!isInterestDedAtDisbValue()) && StringUtils
				.isNullOrEmpty(getGracePeriodDuration()))
				|| (getGracePeriodDurationValue() != null && getGracePeriodDurationValue() > loanOffering
						.getMaxNoInstallments()))
			addError(errors, LoanConstants.GRACEPERIODDURATION,
					LoanConstants.GRACEPERIODERROR,
					LoanConstants.GRACEPERIODDURATION,
					getStringValue(loanOffering.getMaxNoInstallments()));
		validateFees(request, errors);

	}

	private void checkForMinMax(ActionErrors errors, String currentValue,
			Double maxValue, Double minValue, String field) {
		if (StringUtils.isNullOrEmpty(currentValue)
				|| getDoubleValue(currentValue).doubleValue() > maxValue
						.doubleValue()
				|| getDoubleValue(currentValue).doubleValue() < minValue
						.doubleValue()) {
			addError(errors, field, LoanExceptionConstants.INVALIDMINMAX,
					field, getStringValue(minValue), getStringValue(maxValue));
		}
	}

	protected void validateFees(HttpServletRequest request, ActionErrors errors) {
		validateForFeeAmount(errors);
		validateForDuplicatePeriodicFee(request, errors);
	}

	protected void validateForFeeAmount(ActionErrors errors) {
		List<FeeView> feeList = getFeesToApply();
		for (FeeView fee : feeList) {
			if (StringUtils.isNullOrEmpty(fee.getAmount()))
				errors.add(LoanConstants.FEE, new ActionMessage(
						LoanConstants.ERRORS_SPECIFY_FEE_AMOUNT));
		}
	}

	protected void validateForDuplicatePeriodicFee(HttpServletRequest request,
			ActionErrors errors) {
		List<FeeView> additionalFeeList = (List<FeeView>) SessionUtils
				.getAttribute(LoanConstants.ADDITIONAL_FEES_LIST, request
						.getSession());
		for (FeeView selectedFee : getAdditionalFees()) {
			int count = 0;
			for (FeeView duplicateSelectedfee : getAdditionalFees()) {
				if (selectedFee.getFeeIdValue() != null
						&& selectedFee.getFeeId().equals(
								duplicateSelectedfee.getFeeId())) {
					if (isSelectedFeePeriodic(selectedFee, additionalFeeList))
						count++;
				}
			}
			if (count > 1) {
				errors.add(LoanConstants.FEE, new ActionMessage(
						LoanConstants.ERRORS_DUPLICATE_PERIODIC_FEE));
				break;
			}
		}
	}

	private boolean isSelectedFeePeriodic(FeeView selectedFee,
			List<FeeView> additionalFeeList) {
		for (FeeView fee : additionalFeeList)
			if (fee.getFeeId().equals(selectedFee.getFeeId()))
				return fee.isPeriodic();
		return false;
	}
}
