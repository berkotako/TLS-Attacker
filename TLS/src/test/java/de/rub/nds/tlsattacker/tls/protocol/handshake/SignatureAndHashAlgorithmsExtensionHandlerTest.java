/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handshake;

import de.rub.nds.tlsattacker.tls.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.tls.constants.ExtensionType;
import de.rub.nds.tlsattacker.tls.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.tls.protocol.extension.SignatureAndHashAlgorithmsExtensionHandler;
import de.rub.nds.tlsattacker.tls.protocol.extension.SignatureAndHashAlgorithmsExtensionMessage;
import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class SignatureAndHashAlgorithmsExtensionHandlerTest {

    private final SignatureAndHashAlgorithmsExtensionHandler msgHandler;
    private SignatureAndHashAlgorithmsExtensionMessage message;
    private final int gotPointer;
    private final byte[] createdExtension = {(byte) 0, (byte) 13, // Extension type is signature_algorithms
        (byte) 0, (byte) 4, //Count of supported_signature_algorithms bytes
        (byte) 2, (byte) 2, //SHA-1 and DSA
        (byte) 1, (byte) 1};  // MD5 and RSA

    public SignatureAndHashAlgorithmsExtensionHandlerTest() {
        msgHandler = SignatureAndHashAlgorithmsExtensionHandler.getInstance();
        gotPointer = msgHandler.parseExtension(createdExtension, 0);

    }

    @Before
    public void prepareSAHAEMessage() {
        message = (SignatureAndHashAlgorithmsExtensionMessage) msgHandler.getExtensionMessage();
    }

    @Test
    public void testPointer() {
        assertEquals("The new pointer must be 8", 8, gotPointer);
    }

    @Test
    public void testExtensionBytes() {
        assertArrayEquals("Extension Message should be 00 13 00 04 02 02 01 01", createdExtension,
                message.getExtensionBytes().getValue());
    }

    @Test
    public void testExtensionType() {
        assertArrayEquals("Extension Type should be 00 13", ExtensionType.SIGNATURE_AND_HASH_ALGORITHMS.getValue(),
                message.getExtensionType().getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongExtension() {
        SignatureAndHashAlgorithmsExtensionHandler newMsgHandler = SignatureAndHashAlgorithmsExtensionHandler.getInstance();
        byte[] newCreatedExtension = {(byte) 1, (byte) 12, // wrong Extension
            (byte) 0, (byte) 4,
            (byte) 2, (byte) 2,
            (byte) 1, (byte) 1};
        newMsgHandler.parseExtension(newCreatedExtension, 0);
    }

    @Test
    public void testSignatureAndHashAlgorithmLength() {
        assertEquals("The length should be 6", new Integer(6), message.getExtensionLength().getValue());
    }

    @Test
    public void testSignatureAndHashAlgorithmConfig() {
        ArrayList<SignatureAndHashAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(new SignatureAndHashAlgorithm(new byte[]{createdExtension[4], createdExtension[5]}));
        algorithms.add(new SignatureAndHashAlgorithm(new byte[]{createdExtension[6], createdExtension[7]}));
        assertEquals("Checks the array list", algorithms, message.getSignatureAndHashAlgorithmsConfig());
    }

    @Test
    public void testSignatureAndHashAlgorithms() {
        assertArrayEquals(new byte[]{(byte) 2, (byte) 2, (byte) 1, (byte) 1}, message.getSignatureAndHashAlgorithms().getValue());
    }

    @Test
    public void testExtensionLength() {
        assertEquals((int) ExtensionByteLength.EXTENSIONS,
                (int) message.getExtensionLength().getOriginalValue());
    }

}
