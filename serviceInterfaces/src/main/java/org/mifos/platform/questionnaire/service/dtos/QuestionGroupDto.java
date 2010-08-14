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

package org.mifos.platform.questionnaire.service.dtos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.mifos.platform.questionnaire.service.EventSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XStreamAlias("QuestionGroup")
public class QuestionGroupDto implements Serializable {
    private static final long serialVersionUID = -956690618372202849L;

    private String title;
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "SE_BAD_FIELD")
    @XStreamImplicit(itemFieldName = "section")
    private List<SectionDto> sections;
    private EventSource eventSource;
    private boolean editable;
    private boolean ppi;

    public QuestionGroupDto() {
        sections = new ArrayList<SectionDto>();
    }

    public String getTitle() {
        return title;
    }

    public List<SectionDto> getSections() {
        return sections;
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSection(SectionDto sectionDto) {
        sections.add(sectionDto);
    }

    public void setSections(List<SectionDto> sections) {
        this.sections = sections;
    }

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isPpi() {
        return ppi;
    }

    public void setPpi(boolean ppi) {
        this.ppi = ppi;
    }
}
