package edu.web.app.client;

import edu.web.app.dto.AtmReq;
import edu.web.app.dto.AtmResp;

import javax.ejb.Local;

@Local
public interface AtmClientService {

   AtmResp performAction(AtmReq req);

}
