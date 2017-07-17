/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.ClientCertificateUrlExtensionHandler;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class ClientCertificateUrlExtensionMessage extends ExtensionMessage {

    public ClientCertificateUrlExtensionMessage() {
        super(ExtensionType.CLIENT_CERTIFICATE_URL);
    }

    @Override
    public ClientCertificateUrlExtensionHandler getHandler(TlsContext context) {
        return new ClientCertificateUrlExtensionHandler(context);
    }

}