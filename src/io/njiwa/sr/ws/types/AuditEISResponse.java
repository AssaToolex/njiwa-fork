/*
 * Njiwa Open Source Embedded M2M UICC Remote Subscription Manager
 * 
 * 
 * Copyright (C) 2019 - , Digital Solutions Ltd. - http://www.dsmagic.com
 *
 * Njiwa Dev <dev@njiwa.io>
 * 
 * This program is free software, distributed under the terms of
 * the GNU General Public License.
 */ 

package io.njiwa.sr.ws.types;

import io.njiwa.common.ws.types.BaseResponseType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by bagyenda on 25/05/2017.
 */
@XmlRootElement
public class AuditEISResponse  extends BaseResponseType {
    @XmlElement(name = "Eis")
    public Eis eis;

    public AuditEISResponse() {}

    public AuditEISResponse(Date startDate, Date endTime, long acceptablevalidity,
                            BaseResponseType.ExecutionStatus status, Eis eis) {
        super(startDate,endTime,acceptablevalidity,status);
        this.eis = eis;
    }
}
