/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 *
 *  See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 *  explanation of the license and how it is applied.
 */
package org.mifos.accounts.loan.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mifos.accounts.loan.schedule.calculation.ScheduleCalculator;
import org.mifos.accounts.loan.schedule.domain.Installment;
import org.mifos.accounts.loan.schedule.domain.InstallmentPayment;
import org.mifos.accounts.loan.schedule.domain.Schedule;
import org.mifos.accounts.loan.schedule.domain.ScheduleMatcher;
import org.mifos.accounts.util.helpers.PaymentStatus;
import org.mifos.application.master.business.MifosCurrency;
import org.mifos.framework.util.CollectionUtils;
import org.mifos.framework.util.helpers.Money;
import org.mifos.framework.util.helpers.Transformer;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mifos.framework.TestUtils.getDate;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleCalculatorAdaptorTest {

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private LoanBO loanBO;

    private ScheduleCalculator scheduleCalculator;
    private MifosCurrency rupee = new MifosCurrency(Short.valueOf("1"), "Rupee", valueOf(1), "INR");
    private ScheduleCalculatorAdaptor scheduleCalculatorAdaptor;
    private static final Double DAILY_INTEREST_RATE = 0.000658;
    private static final Double ANNUAL_INTEREST_RATE = 24.0;
    private static BigDecimal LOAN_AMOUNT = new BigDecimal(1000);
    private static final Date DISBURSEMENT_DATE = getDate(23, 9, 2010);

    @Before
    public void setup() {
        scheduleCalculator = Mockito.spy(new ScheduleCalculator());
        scheduleMapper = Mockito.spy(new ScheduleMapper());
        scheduleCalculatorAdaptor = new ScheduleCalculatorAdaptor(scheduleCalculator, scheduleMapper);
        LOAN_AMOUNT = LOAN_AMOUNT.setScale(13, RoundingMode.HALF_UP);
        when(loanBO.getCurrency()).thenReturn(rupee);
    }

    @Test
    public void shouldApplyPayment() {
        List<LoanScheduleEntity> loanScheduleEntities = getLoanScheduleEntities(new double[]{100, 10, 5, 4, 3, 2, 1}, new double[]{1, 1, 1, 1, 1, 1, 1});
        when(loanBO.getLoanScheduleEntities()).thenReturn(loanScheduleEntities);
        when(loanBO.getDisbursementDate()).thenReturn(DISBURSEMENT_DATE);
        when(loanBO.getLoanAmount()).thenReturn(new Money(rupee, LOAN_AMOUNT));
        when(loanBO.getInterestRate()).thenReturn(ANNUAL_INTEREST_RATE);
        scheduleCalculatorAdaptor.applyPayment(loanBO, Money.zero(rupee), getDate(30, 10, 2010));
        verify(scheduleMapper, times(1)).mapToSchedule(Mockito.<Collection<LoanScheduleEntity>>any(), Mockito.<Date>any(), Mockito.<Double>any(), Mockito.<BigDecimal>any());
        verify(scheduleCalculator).applyPayment(Mockito.<Schedule>any(), Mockito.<BigDecimal>any(), Mockito.<Date>any());
        verify(scheduleMapper).populatePaymentDetails(Mockito.<Schedule>any(), Mockito.<LoanBO>any(), Mockito.<Date>any());
        verify(loanBO, times(2)).getLoanScheduleEntities();
        verify(loanBO, times(1)).getDisbursementDate();
        verify(loanBO, times(1)).getInterestRate();
    }

    @Test
    public void shouldComputeExtraInterestForDecliningPrincipalBalance() {
        List<LoanScheduleEntity> loanScheduleEntities = getLoanScheduleEntities(new double[]{100, 10, 0.0, 0.0, 0.0, 0.0, 0.0}, new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        when(loanBO.isDecliningBalanceInterestRecalculation()).thenReturn(true);
        when(loanBO.getLoanScheduleEntities()).thenReturn(loanScheduleEntities);
        when(loanBO.getDisbursementDate()).thenReturn(DISBURSEMENT_DATE);
        when(loanBO.getLoanAmount()).thenReturn(new Money(rupee, LOAN_AMOUNT));
        when(loanBO.getInterestRate()).thenReturn(ANNUAL_INTEREST_RATE);
        when(loanBO.getLoanScheduleEntityMap()).thenReturn(getLoanScheduleEntityMap(loanScheduleEntities));

        scheduleCalculatorAdaptor.computeExtraInterest(loanBO, getDate(30, 10, 2010));
        Schedule expectedSchedule = getSchedule(DISBURSEMENT_DATE, LOAN_AMOUNT, getInstallments(0, .46, 0));
        verify(scheduleCalculator).computeExtraInterest(argThat(new ScheduleMatcher(expectedSchedule)), Mockito.eq(getDate(30, 10, 2010)));

        verify(loanBO, times(1)).isDecliningBalanceInterestRecalculation();
        verify(loanBO, times(1)).getLoanScheduleEntities();
        verify(loanBO, times(1)).getDisbursementDate();
        verify(loanBO, times(1)).getLoanAmount();
        verify(loanBO, times(1)).getInterestRate();
        verify(loanBO, times(1)).getLoanScheduleEntityMap();

        ArrayList<LoanScheduleEntity> loanScheduleEntitiesWithExtraInterest = new ArrayList<LoanScheduleEntity>(loanBO.getLoanScheduleEntities());
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(0), 0.0);
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(1), 0.46);
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(2), 0.0);
    }

    @Test
    public void shouldNotComputeExtraInterestForNonPrincipalBalanceInterestTypes() {
        List<LoanScheduleEntity> loanScheduleEntities = getLoanScheduleEntities(new double[]{100, 10, 0.0, 0.0, 0.0, 0.0, 0.0}, new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        when(loanBO.isDecliningBalanceInterestRecalculation()).thenReturn(false);
        when(loanBO.getLoanScheduleEntities()).thenReturn(loanScheduleEntities);
        when(loanBO.getDisbursementDate()).thenReturn(DISBURSEMENT_DATE);
        when(loanBO.getLoanAmount()).thenReturn(new Money(rupee, LOAN_AMOUNT));
        when(loanBO.getInterestRate()).thenReturn(ANNUAL_INTEREST_RATE);
        when(loanBO.getLoanScheduleEntityMap()).thenReturn(getLoanScheduleEntityMap(loanScheduleEntities));

        scheduleCalculatorAdaptor.computeExtraInterest(loanBO, getDate(30, 10, 2010));

        verify(scheduleCalculator, times(0)).computeExtraInterest(Mockito.<Schedule>any(), Mockito.<Date>any());
        verify(loanBO, times(0)).getInterestRate();
        verify(loanBO, times(0)).getLoanScheduleEntities();
        verify(loanBO, times(0)).getDisbursementDate();
        verify(loanBO, times(0)).getLoanAmount();
        verify(loanBO, times(0)).getInterestRate();
        verify(loanBO, times(0)).getLoanScheduleEntityMap();

        ArrayList<LoanScheduleEntity> loanScheduleEntitiesWithExtraInterest = new ArrayList<LoanScheduleEntity>(loanBO.getLoanScheduleEntities());
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(0), 0.0);
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(1), 0.0);
        assertExtraInterest(loanScheduleEntitiesWithExtraInterest.get(2), 0.0);
    }

    @Test
    public void computeExtraInterestAndPopulateInLoanScheduleEntities() {
        List<Installment> installments = getInstallments(0, 0, 0);
        Schedule schedule = new Schedule(DISBURSEMENT_DATE, DAILY_INTEREST_RATE, LOAN_AMOUNT, installments);
        new ScheduleCalculator().computeExtraInterest(schedule, getDate(30, 10, 2010));
        List<LoanScheduleEntity> loanScheduleEntities = getLoanScheduleEntities(new double[]{100, 10, 0.0, 0.0, 0.0, 0.0, 0.0}, new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        Map<Integer, LoanScheduleEntity> loanScheduleEntityMap = getLoanScheduleEntityMap(loanScheduleEntities);
        assertThat(schedule.getInstallments().get(2).getExtraInterest().doubleValue(), is(0.46));
        scheduleCalculatorAdaptor.populateExtraInterestInLoanScheduleEntities(schedule, loanScheduleEntityMap);
        for (Installment installment : installments) {
            LoanScheduleEntity loanScheduleEntity = loanScheduleEntityMap.get(installment.getId());
            assertExtraInterest(loanScheduleEntity, installment.getExtraInterest().doubleValue());
        }
    }

    private Schedule getSchedule(Date disbursementDate, BigDecimal loanAmount, List<Installment> installments) {
        return new Schedule(disbursementDate, DAILY_INTEREST_RATE, loanAmount, installments);
    }

    private void assertExtraInterest(LoanScheduleEntity loanScheduleEntity, double extraInterest) {
        assertThat(loanScheduleEntity.getExtraInterest().getAmount().doubleValue(), is(extraInterest));
    }

    private Map<Integer, LoanScheduleEntity> getLoanScheduleEntityMap(List<LoanScheduleEntity> loanScheduleEntities) {
        return CollectionUtils.asValueMap(loanScheduleEntities, new Transformer<LoanScheduleEntity, Integer>() {
            @Override
            public Integer transform(LoanScheduleEntity input) {
                return Integer.valueOf(input.getInstallmentId());
            }
        });
    }

    private List<Installment> getInstallments(double... extraInterest) {
        return asList(
                getInstallment(1, getDate(23, 10, 2010), getDate(23, 10, 2010), new double[]{100, 10, extraInterest[0], 0.0, 0.0, 0.0, 0.0}, new double[]{0, 0, 0.0, 0.0, 0.0, 0.0, 0.0}),
                getInstallment(2, getDate(23, 11, 2010), getDate(23, 11, 2010), new double[]{100, 10, extraInterest[1], 0.0, 0.0, 0.0, 0.0}, new double[]{0, 0, 0.0, 0.0, 0.0, 0.0, 0.0}),
                getInstallment(3, getDate(23, 12, 2010), getDate(23, 12, 2010), new double[]{100, 10, extraInterest[2], 0.0, 0.0, 0.0, 0.0}, new double[]{0, 0, 0.0, 0.0, 0.0, 0.0, 0.0})
        );
    }

    private Installment getInstallment(int id, Date dueDate, Date paidDate, double[] actualAmounts, double[] paidAmounts) {
        
        Installment installment = new Installment(id, dueDate, valueOf(actualAmounts[0]), valueOf(actualAmounts[1]),
                valueOf(actualAmounts[2]), valueOf(actualAmounts[3]), valueOf(actualAmounts[4]),
                valueOf(actualAmounts[5]), valueOf(actualAmounts[6]));
        InstallmentPayment installmentPayment = new InstallmentPayment();
        installmentPayment.setPrincipalPaid(valueOf(paidAmounts[0]));
        installmentPayment.setInterestPaid(valueOf(paidAmounts[1]));
        installmentPayment.setExtraInterestPaid(valueOf(paidAmounts[2]));
        installmentPayment.setFeesPaid(valueOf(paidAmounts[3]));
        installmentPayment.setMiscFeesPaid(valueOf(paidAmounts[4]));
        installmentPayment.setPenaltyPaid(valueOf(paidAmounts[5]));
        installmentPayment.setMiscPenaltyPaid(valueOf(paidAmounts[6]));
        installmentPayment.setPaidDate(paidDate);
        installment.addPayment(installmentPayment);
        return installment;
    }

    private List<LoanScheduleEntity> getLoanScheduleEntities(double[] actualAmounts, double[] paidAmounts) {
        LoanScheduleEntity loanScheduleEntity1 = new LoanScheduleBuilder("1", loanBO).withDueDate(getDate(23, 10, 2010)).
                withPaymentStatus(PaymentStatus.UNPAID).withPrincipal(100).withInterest(10).build();
        LoanScheduleEntity loanScheduleEntity2 = new LoanScheduleBuilder("2", loanBO).withDueDate(getDate(23, 11, 2010)).
                withPaymentStatus(PaymentStatus.UNPAID).withPrincipal(100).withInterest(10).build();
        LoanScheduleEntity loanScheduleEntity3 = new LoanScheduleBuilder("3", loanBO).withDueDate(getDate(23, 12, 2010)).
                withPaymentStatus(PaymentStatus.UNPAID).withPrincipal(100).withInterest(10).build();
        return Arrays.asList(loanScheduleEntity1, loanScheduleEntity2, loanScheduleEntity3);
    }

}
