package com.gazman.bls.model;

import it.unisa.dia.gas.jpbc.Element;

public class Signature {
    public byte[] message;
    public Element publicKey;
    public Element signature;
    
    public Signature() {
    	
    }
    
    public Signature(byte[] message_, Element publicKey_, Element signature_) {
    		this.message = message_;
    		this.publicKey = publicKey_;
    		this.signature = signature_;
    }
}
