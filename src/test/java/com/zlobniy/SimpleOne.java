package com.zlobniy;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class SimpleOne {


    @Test
    public void simpleOne() throws DecoderException, EncoderException{

        String checksum = "1,demo";

        Base64 base = new Base64(  );
        String encodedChecksum = base.encodeAsString( checksum.getBytes() );
        System.out.println( "encodedChecksum: " + encodedChecksum );

        Base64 decoder = new Base64(  );
        String decodedChecksum = new String( decoder.decode( encodedChecksum ) );
        System.out.println( decodedChecksum );
    }
}
