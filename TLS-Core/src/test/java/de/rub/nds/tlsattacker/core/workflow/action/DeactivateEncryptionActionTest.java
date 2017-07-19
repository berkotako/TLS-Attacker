/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.constants.CipherSuite;
import de.rub.nds.tlsattacker.core.record.cipher.RecordBlockCipher;
import de.rub.nds.tlsattacker.core.record.layer.TlsRecordLayer;
import de.rub.nds.tlsattacker.core.unittest.helper.ActionExecutorMock;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXB;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Robert Merget - robert.merget@rub.de
 */
public class DeactivateEncryptionActionTest {

    private TlsContext tlsContext;

    private ActionExecutorMock executor;
    private DeactivateEncryptionAction action;

    @Before
    public void setUp() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        executor = new ActionExecutorMock();
        tlsContext = new TlsContext();
        tlsContext.setSelectedCipherSuite(CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA);
        tlsContext.setRecordLayer(new TlsRecordLayer(tlsContext));
        tlsContext.getRecordLayer().setRecordCipher(new RecordBlockCipher(tlsContext));
        action = new DeactivateEncryptionAction();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class DeactivateEncryptionAction.
     */
    @Test
    public void testExecute() throws Exception {
        action.execute(tlsContext, executor);
        assertTrue(action.isExecuted());
        // TODO Check that decryption is disabled
    }

    /**
     * Test of reset method, of class DeactivateEncryptionAction.
     */
    @Test
    public void testReset() {
        assertFalse(action.isExecuted());
        action.execute(tlsContext, executor);
        assertTrue(action.isExecuted());
        action.reset();
        assertFalse(action.isExecuted());
        action.execute(tlsContext, executor);
        assertTrue(action.isExecuted());
    }

    @Test
    public void testJAXB() {
        StringWriter writer = new StringWriter();
        JAXB.marshal(action, writer);
        TLSAction action2 = JAXB.unmarshal(new StringReader(writer.getBuffer().toString()),
                DeactivateEncryptionAction.class);
        assertEquals(action, action2);
    }
}
