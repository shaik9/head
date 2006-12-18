-- Creates a bunch of indexes for MIFOS.

CREATE UNIQUE INDEX CUST_GLOBAL_IDX ON CUSTOMER (GLOBAL_CUST_NUM);

CREATE UNIQUE INDEX CUST_SEARCH_IDX ON CUSTOMER (SEARCH_ID);

CREATE INDEX CUST_LO_IDX ON CUSTOMER (LOAN_OFFICER_ID, BRANCH_ID);

CREATE INDEX CUST_HIERARCHY_IDX ON CUSTOMER_HIERARCHY (CUSTOMER_ID, STATUS);

CREATE INDEX CUST_MOVEMENT_IDX ON CUSTOMER_MOVEMENT (CUSTOMER_ID, STATUS);

CREATE INDEX LOOKUP_VALUE_IDX ON LOOKUP_VALUE (ENTITY_ID);

CREATE UNIQUE INDEX CUST_POSITION_IDX ON CUSTOMER_POSITION (CUSTOMER_ID, POSITION_ID);

CREATE INDEX CHK_DETAIL_IDX ON CHECKLIST_DETAIL (CHECKLIST_ID,LOCALE_ID);

CREATE INDEX CUST_NAME_IDX ON CUSTOMER_NAME_DETAIL (CUSTOMER_ID);

CREATE INDEX CUST_ADDRESS_IDX ON CUSTOMER_ADDRESS_DETAIL (CUSTOMER_ID);

CREATE INDEX CUST_NOTE_IDX ON CUSTOMER_NOTE (CUSTOMER_ID);

CREATE INDEX CHANGE_LOG_IDX ON CHANGE_LOG (ENTITY_TYPE, ENTITY_ID, CHANGED_DATE);

CREATE INDEX CUSTOMER_MEETING_IDX ON CUSTOMER_MEETING (CUSTOMER_ID);

CREATE INDEX CUST_INHERITED_MEETING_IDX ON CUSTOMER_MEETING (CUSTOMER_ID);

CREATE UNIQUE INDEX OFFICE_GLOBAL_IDX ON OFFICE (GLOBAL_OFFICE_NUM);

CREATE INDEX OFFICE_HIERARCHY_IDX ON OFFICE_HIERARCHY (OFFICE_ID,STATUS);

CREATE UNIQUE INDEX PERSONNEL_GLOBAL_IDX ON PERSONNEL (GLOBAL_PERSONNEL_NUM);

CREATE UNIQUE INDEX PERSONNEL_SEARCH_IDX ON PERSONNEL (SEARCH_ID);

CREATE INDEX PERSONNEL_OFFICE_IDX ON PERSONNEL (OFFICE_ID);

CREATE INDEX PERSONNEL_HIERARCHY_IDX ON PERSONNEL_HIERARCHY (PERSONNEL_ID, STATUS);

CREATE INDEX PERSONNEL_MOVEMENT_IDX ON PERSONNEL_MOVEMENT (PERSONNEL_ID);

CREATE UNIQUE INDEX FEE_GLOBAL_IDX ON FEES (GLOBAL_FEE_NUM);

CREATE INDEX FEE_PMNT_CATG_IDX ON FEES (FEE_PAYMENTS_CATEGORY_TYPE_ID);

CREATE INDEX FEE_OFFICE_IDX ON FEES (OFFICE_ID);

CREATE UNIQUE INDEX PRD_OFFERING_GLOBAL_IDX ON PRD_OFFERING (GLOBAL_PRD_OFFERING_NUM);

CREATE INDEX PRD_OFFERING_OFFICE_IDX ON PRD_OFFERING (OFFICE_ID);

CREATE INDEX PRD_TYPE_IDX ON PRD_OFFERING (PRD_TYPE_ID);

CREATE INDEX PRD_OFFERING_FEE_IDX ON PRD_OFFERING_FEES (PRD_OFFERING_ID, FEE_ID);

CREATE INDEX ACCOUNT_ID_ACCOUNT_PAYMENT_IDX ON ACCOUNT_PAYMENT (ACCOUNT_ID);

CREATE INDEX ACCOUNT_ID_ACCOUNT_TRXN_IDX ON ACCOUNT_TRXN (ACCOUNT_ID);

CREATE UNIQUE INDEX ACCOUNT_GLOBAL_IDX ON ACCOUNT (GLOBAL_ACCOUNT_NUM);

CREATE INDEX CUSTOMER_ID_ACCOUNT_IDX ON ACCOUNT (CUSTOMER_ID);

CREATE INDEX ACCOUNT_FEES_ID_IDX ON ACCOUNT_FEES (ACCOUNT_ID,FEE_ID);

CREATE INDEX LOAN_ACCOUNT_TRXN_IDX ON LOAN_TRXN_DETAIL (ACCOUNT_TRXN_ID);

CREATE INDEX FEE_ACCOUNT_TRXN_IDX ON FEE_TRXN_DETAIL (ACCOUNT_TRXN_ID);

CREATE INDEX CUSTOMER_LO_NAME_IDX ON CUSTOMER (LOAN_OFFICER_ID, CUSTOMER_LEVEL_ID,DISPLAY_NAME(15),FIRST_NAME(15),LAST_NAME(15),SECOND_LAST_NAME(15));

CREATE INDEX CUSTOMER_NAME_IDX ON CUSTOMER (CUSTOMER_LEVEL_ID,FIRST_NAME(15),LAST_NAME(15),SECOND_LAST_NAME(15));

CREATE INDEX CUSTOMER_SCHEDULE_ACTION_DATE_IDX ON CUSTOMER_SCHEDULE (CUSTOMER_ID,ACTION_DATE,PAYMENT_STATUS);