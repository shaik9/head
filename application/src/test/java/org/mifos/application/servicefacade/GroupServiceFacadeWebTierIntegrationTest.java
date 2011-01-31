/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.application.servicefacade;

import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mifos.application.meeting.business.MeetingBO;
import org.mifos.config.ClientRules;
import org.mifos.customers.group.business.GroupBO;
import org.mifos.customers.persistence.CustomerDao;
import org.mifos.customers.util.helpers.CustomerStatus;
import org.mifos.domain.builders.MeetingBuilder;
import org.mifos.dto.domain.AddressDto;
import org.mifos.dto.domain.ApplicableAccountFeeDto;
import org.mifos.dto.domain.CustomerDetailsDto;
import org.mifos.dto.domain.GroupCreationDetail;
import org.mifos.dto.domain.MeetingDto;
import org.mifos.framework.MifosIntegrationTestCase;
import org.mifos.framework.util.helpers.IntegrationTestObjectMother;
import org.mifos.security.AuthenticationAuthorizationServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupServiceFacadeWebTierIntegrationTest extends MifosIntegrationTestCase {

    // class under test
    @Autowired
    private GroupServiceFacade groupServiceFacade;

    @Autowired private AuthenticationAuthorizationServiceFacade authenticationAuthorizationService;
    @Autowired private CustomerDao customerDao;

    @Before
    public void setup () {
        authenticationAuthorizationService.reloadUserDetailsForSecurityContext("mifos");
    }

    @Ignore
    @Test
    public void shouldCreateGroupWithActivationDateInPast() {

        // setup
        boolean centerHierarchyExistsOriginal = ClientRules.getCenterHierarchyExists();

        MeetingBO meeting = new MeetingBuilder().withStartDate(new DateTime().minusWeeks(2)).build();
        MeetingDto meetingDto = meeting.toDto();

        String displayName = "testGroup";
        String externalId = null;
        AddressDto addressDto = null;
        Short loanOfficerId = null;
        List<ApplicableAccountFeeDto> feesToApply = new ArrayList<ApplicableAccountFeeDto>();
        Short customerStatus = CustomerStatus.GROUP_ACTIVE.getValue();
        boolean trained = false;
        DateTime trainedOn = null;
        String parentSystemId = null;
        Short officeId = IntegrationTestObjectMother.SAMPLE_BRANCH_OFFICE;
        DateTime mfiJoiningDate = new DateTime().minusWeeks(2);
        DateTime activationDate = new DateTime().minusWeeks(1);
        GroupCreationDetail groupCenterDetail = new GroupCreationDetail(displayName, externalId, addressDto, loanOfficerId,
                feesToApply, customerStatus, trained, trainedOn, parentSystemId, officeId, mfiJoiningDate, activationDate);

        // exercise test
        ClientRules.setCenterHierarchyExists(false);
        CustomerDetailsDto newlyCreatedGroupDetails = groupServiceFacade.createNewGroup(groupCenterDetail, meetingDto);

        // verification
        ClientRules.setCenterHierarchyExists(centerHierarchyExistsOriginal);
        GroupBO group = customerDao.findGroupBySystemId(newlyCreatedGroupDetails.getGlobalCustNum());
        Assert.assertThat(new LocalDate(group.getCustomerActivationDate()), is(activationDate.toLocalDate()));
    }
}
