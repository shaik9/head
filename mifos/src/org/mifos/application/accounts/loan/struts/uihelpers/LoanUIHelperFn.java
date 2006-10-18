/**

 * LoanUIHelperFn.java    version: xxx

 

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

package org.mifos.application.accounts.loan.struts.uihelpers;

import java.math.BigDecimal;
import java.util.Locale;

import org.mifos.application.meeting.business.MeetingBO;
import org.mifos.application.meeting.util.helpers.MeetingHelper;
import org.mifos.framework.security.util.UserContext;
import org.mifos.framework.struts.tags.DateHelper;

/**
 * This class has got helper functions which could be called from jsp as part of
 * jsp2.0 specifications.
 */
public class LoanUIHelperFn {

	public LoanUIHelperFn() {
		super();

	}

	public static String getCurrrentDate(Locale locale) {
		return DateHelper.getCurrentDate(locale);
	}

	public static String getMeetingRecurrence(Object meeting,	Object userContext) {
		return  meeting!=null ? new MeetingHelper().getMessageWithFrequency((MeetingBO)meeting, (UserContext)userContext):null;
	}
	
	public static String getDoubleValue(Double value) {
		if (value != null)
			return BigDecimal.valueOf(value).toString();
		else
			return "0.0";
	}

}
