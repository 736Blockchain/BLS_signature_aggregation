package com.gazman.bls;

import com.gazman.bls.model.BlsModel;
import com.gazman.bls.model.Signature;
import com.gazman.bls.utils.Sha256Hash;
import com.gazman.bls.BlsSignatures;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;


public class Test {


	public static void main(String[] args) {
		BlsSignatures blsSignatures = new BlsSignatures();
		ArrayList<Signature> signatureslist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            byte[] privateKey = blsSignatures.pairing.getZr().newRandomElement().toBytes();
            Signature signature = blsSignatures.sign( ("cool春捂秋冻出去玩去adcvwdvavawdv " + i).getBytes(), privateKey );
            System.out.println(signature.signature.toBytes().length);
            signatureslist.add(  new Signature(signature.message, signature.publicKey, 
            		blsSignatures.pairing.getG1().newElementFromBytes(  signature.signature.toBytes())  ) );
            
        }
        
        Element res = blsSignatures.AggregateSignature(signatureslist);
        byte[] resbyte = res.toBytes();
        System.out.println(resbyte.length);
        Element newres = blsSignatures.pairing.getG1().newElementFromBytes(resbyte);
        System.out.println( blsSignatures.validateAggreSignature(newres) );
        
	}

}
