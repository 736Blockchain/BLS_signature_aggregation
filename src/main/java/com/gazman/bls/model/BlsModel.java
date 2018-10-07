package com.gazman.bls.model;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public enum BlsModel {
    instance;

    public final Pairing pairing;
    public final Element systemParameters;

    BlsModel(){
//        pairing = PairingFactory.getPairing("it/unisa/dia/gas/plaf/jpbc/pairing/a/a_181_603.properties");
    	pairing = PairingFactory.getPairing("/Users/jiaxyan/Downloads/a_181_603.properties");
    	systemParameters = pairing.getG2().newRandomElement(); // this will be a hardcoded value in the future
    }
}
