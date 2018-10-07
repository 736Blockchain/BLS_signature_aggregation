package com.gazman.bls;

import com.gazman.bls.model.BlsModel;
import com.gazman.bls.model.Signature;
import com.gazman.bls.utils.Sha256Hash;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class BlsSignatures {

    public Pairing pairing = BlsModel.instance.pairing;
    private Element systemParameters = BlsModel.instance.systemParameters;
    private ArrayList<Signature> signatures = new ArrayList<>();

    public Signature sign(byte[] message, byte[] privateKey) {
        Element secretKey = pairing.getZr().newElementFromBytes(privateKey);
        Element publicKey = systemParameters.duplicate().powZn(secretKey);


        byte[] hash = hash(message, publicKey);
        Element h = pairing.getG1().newElementFromHash(hash, 0, hash.length);

        Element signatureElement = h.powZn(secretKey);

        Signature signature = new Signature();
        signature.message = message;
        signature.publicKey = publicKey;
        signature.signature = signatureElement;
        signatures.add(signature);
        return signature;
    }

    public void addSignature(Signature signature) {
        signatures.add(signature);
    }

    /**
     * 聚合签名
     * @param signatures 包含所有签名的list
     */
    public Element AggregateSignature(ArrayList<Signature> signatures) {
	    	Element signatureAggre = signatures.get(0).signature;
	    	for (int i=1; i<signatures.size(); i++) {
	        	System.out.println("signature:"+signatures.get(i).signature);
//	        	Element base = signatures.get(i).signature;
	        	signatureAggre = signatureAggre.mul(signatures.get(i).signature);
	        	//System.out.println("signatureMul:"+signatureMul);
	    }
    		return signatureAggre;
    }
//    public Element AggregateSignature(ArrayList<Signature> signatures) {
//        Element aggreSignature = signatures.get(0).signature.duplicate();
//           for (int i = 1; i < signatures.size(); i++) {
//               aggreSignature = aggreSignature.mul(signatures.get(i).signature);
//           }
//           
//           return aggreSignature;
//           
//       }

    
    
    public boolean validateAggreSignature(Element aggreSignature) {
    	
        Element compactPairing = pairing.pairing(aggreSignature, systemParameters.duplicate());

        byte[] hash0 = hash(signatures.get(0).message, signatures.get(0).publicKey);
        Element hashElement0 = pairing.getG1().newElementFromHash(hash0, 0, hash0.length);
        Element fullPairing = pairing.pairing(hashElement0, signatures.get(0).publicKey);
        for (int i = 1; i < signatures.size(); i++) {
            byte[] messageHash = hash(signatures.get(i).message, signatures.get(i).publicKey);

            Element hashElement = pairing.getG1().newElementFromHash(messageHash, 0, messageHash.length);
            Element publicKey = signatures.get(i).publicKey;
            Element p = pairing.pairing(hashElement, publicKey);
            fullPairing.mul(p);
        }
//        System.out.println("-------Verifying pass--------");
        return compactPairing.isEqual(fullPairing);
    }
    
    public boolean validateSingleSignature(Element element) {
    		//Not implemented yet
    		BigInteger a = BigInteger.valueOf(13);
    		BigInteger b = a.multiply(BigInteger.TEN);
    		System.out.println("---- Not Implemented yet");
    		return true;
    }
    
    @Deprecated
    public boolean validate() {
    		return false;
    	/*
        Element compactSignature = signatures.get(0).signature.duplicate();
        for (int i = 1; i < signatures.size(); i++) {
            compactSignature = compactSignature.mul(signatures.get(i).signature);
        }
        Element compactPairing = pairing.pairing(compactSignature, systemParameters.duplicate());

        byte[] hash0 = hash(signatures.get(0).message, signatures.get(0).publicKey);
        Element hashElement0 = pairing.getG1().newElementFromHash(hash0, 0, hash0.length);
        Element fullPairing = pairing.pairing(hashElement0, signatures.get(0).publicKey);
        for (int i = 1; i < signatures.size(); i++) {
            byte[] messageHash = hash(signatures.get(i).message, signatures.get(i).publicKey);

            Element hashElement = pairing.getG1().newElementFromHash(messageHash, 0, messageHash.length);
            Element publicKey = signatures.get(i).publicKey;
            Element p = pairing.pairing(hashElement, publicKey);
            fullPairing.mul(p);
        }

        System.out.println("-------Verifying pass--------");
        return compactSignature.isEqual(fullPairing);
    */}

    private byte[] hash(byte[] message, Element publicKey) {
        byte[] bytes1 = Sha256Hash.hash(message);
        byte[] bytes2 = publicKey.toBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes1.length + bytes2.length);
        buffer.put(bytes1);
        buffer.put(bytes2);

        return Sha256Hash.hash(buffer.array());
    }

}
