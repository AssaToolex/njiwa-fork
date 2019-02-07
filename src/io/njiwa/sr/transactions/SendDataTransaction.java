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

package io.njiwa.sr.transactions;

import io.njiwa.common.Utils;
import io.njiwa.common.model.RpaEntity;
import io.njiwa.common.model.TransactionType;
import io.njiwa.common.ws.WSUtils;
import io.njiwa.common.ws.types.BaseResponseType;
import io.njiwa.common.ws.types.WsaEndPointReference;
import io.njiwa.sr.ws.interfaces.ES3;

import javax.ejb.Asynchronous;
import javax.persistence.EntityManager;
import javax.xml.ws.Holder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bagyenda on 09/05/2017.
 */
public class SendDataTransaction extends SmSrBaseTransaction {

    public String sdAid = null; // The AID

    public Map<String, Boolean> respMap = new HashMap<String, Boolean>();
    public byte[] response = new byte[0];

    @Asynchronous // XXX right?
    private void sendResponse(EntityManager em, boolean success) {
        if (status == null) {
            status = new BaseResponseType.ExecutionStatus(success ? BaseResponseType.ExecutionStatus.Status
                    .ExecutedSuccess : BaseResponseType.ExecutionStatus.Status.Failed,
                    new BaseResponseType.ExecutionStatus.StatusCode("8.1.1", "SendData", "", ""));
        } else
            status.status = BaseResponseType.ExecutionStatus.Status.Failed;
        Date endDate = Calendar.getInstance().getTime(); // Set it


        final ES3 proxy = WSUtils.getPort("http://namespaces.gsma.org/esim-messaging/1", "ES3Port",
                getReplyToAddress(em, "ES3"), ES3.class, RpaEntity.Type.SMSR, em,requestingEntityId);
        final WsaEndPointReference sender = new WsaEndPointReference();
        sender.address = originallyTo;
        final Holder<String> msgType = new Holder<String>("http://gsma" +
                ".com/ES3/ProfileManagentCallBack/ES3-SendData");

        try {
            proxy.sendDataResponse(sender, getReplyToAddress(em, "ES3").address, relatesTO, msgType, Utils
                            .gregorianCalendarFromDate
                                    (startDate),
                    Utils.gregorianCalendarFromDate(endDate), TransactionType.DEFAULT_VALIDITY_PERIOD, status, response !=
                            null ?
                            Utils.HEX.b2H(response) : null);
        } catch (WSUtils.SuppressClientWSRequest wsa) {
        } catch (Exception ex) {
            Utils.lg.error("Async sendDataResponse failed: " + ex.getMessage());
        }

    }

    @Override
    public synchronized void processResponse(EntityManager em, long tid, ResponseType rtype, String reqId, byte[]
            response) {
        if (rtype == ResponseType.SUCCESS) {
            // Get stuff
            if (reqId == null)
                reqId = "";
            // Check if we have seen this reqId before
            if (respMap.get(reqId) != null)
                return;
            respMap.put(reqId, true);
            // Add data to output
            int len = response == null ? 0 : response.length;
            int oldlen = this.response.length;
            byte[] x = new byte[len + oldlen];
            System.arraycopy(this.response, 0, x, 0, oldlen);
            System.arraycopy(response, 0, x, oldlen, response.length);
            this.response = x;

            if (hasMore())
                return; // Still more data expected
        }
        sendResponse(em, rtype == ResponseType.SUCCESS); // Send to caller
    }


}
