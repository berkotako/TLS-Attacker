/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateUrlExtensionMessage;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class ClientCertificateUrlExtensionPreparatorTest {
    private final ExtensionType extensionType = ExtensionType.CLIENT_CERTIFICATE_URL;
    private final int extensionLength = 0;
    private TlsContext context;
    private ClientCertificateUrlExtensionMessage message;
    private ClientCertificateUrlExtensionPreparator preparator;

    @Before
    public void setUp() {
        context = new TlsContext();
        message = new ClientCertificateUrlExtensionMessage();
        preparator = new ClientCertificateUrlExtensionPreparator(context, message);
    }

    @Test
    public void testPreparator() {
        preparator.prepare();

        assertArrayEquals(extensionType.getValue(), message.getExtensionType().getValue());
        assertEquals(extensionLength, (int) message.getExtensionLength().getValue());
    }
}
