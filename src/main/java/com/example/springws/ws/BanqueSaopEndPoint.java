package com.example.springws.ws;

import com.adria.soap.GetCompteRequest;
import com.adria.soap.GetCompteResponse;
import com.adria.soap.ListComptesRequest;
import com.adria.soap.ListComptesResponse;
import com.example.springws.dao.CompteRepo;
import com.example.springws.entites.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.List;


@Endpoint
public class BanqueSaopEndPoint {
    @Autowired
    CompteRepo compteRepo;
    public static final String NAME_SPACE="http://www.adria.com/soap";

    @ResponsePayload
    @PayloadRoot(localPart = "GetCompteRequest",namespace = NAME_SPACE)
    public GetCompteResponse consultCompte(@RequestPayload GetCompteRequest request) throws DatatypeConfigurationException {
        GetCompteResponse getCompteResponse=new GetCompteResponse();
        Compte compte=compteRepo.findById(request.getCode()).get();
        com.adria.soap.Compte soapCompte=MapSaopCompteFrom(compte);
        getCompteResponse.setCompte(soapCompte);
        return getCompteResponse;
    }

    @ResponsePayload
    @PayloadRoot(localPart = "ListComptesRequest",namespace = NAME_SPACE)
    public ListComptesResponse consultComptes(@RequestPayload ListComptesRequest request) throws DatatypeConfigurationException {
        ListComptesResponse getCompteResponse=new ListComptesResponse();
        List<Compte> comptes=compteRepo.findAll();

        for (Compte c:comptes) {
            getCompteResponse.getComptes().add(MapSaopCompteFrom(c));
        }

        return getCompteResponse;
    }

    private com.adria.soap.Compte MapSaopCompteFrom(Compte compte) throws DatatypeConfigurationException {
        com.adria.soap.Compte soapCompte=new com.adria.soap.Compte();
        soapCompte.setCode(compte.getCode());
        soapCompte.setSolde(compte.getSolde());
        soapCompte.setActive(compte.getActive());
        GregorianCalendar gregorianCalendar=new GregorianCalendar();
        gregorianCalendar.setTime(compte.getDateCreation());
        soapCompte.setDateCreation(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
        return soapCompte;
    }
}
