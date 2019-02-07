/*
 * Kasuku - Open Source eUICC Remote Subscription Management Server
 * 
 * 
 * Copyright (C) 2019 - , Digital Solutions Ltd. - http://www.dsmagic.com
 *
 * Paul Bagyenda <bagyenda@dsmagic.com>
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
 * Created by bagyenda on 08/06/2016.
 */
@XmlRootElement
public class GetEISResponse extends BaseResponseType {
    @XmlElement(name = "Eis")
    public Eis eis;

    public GetEISResponse(){}

    public GetEISResponse(Date startDate, Date endTime, long acceptablevalidity,
                          ExecutionStatus status, Eis eis)
    {
        super(startDate,endTime,acceptablevalidity,status);
        this.eis = eis;
    }
}
