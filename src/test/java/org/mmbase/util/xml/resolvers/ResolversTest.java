package org.mmbase.util.xml.resolvers;

import java.io.IOException;
import javax.xml.parsers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlresolver.XMLResolver;

class ResolversTest {


    /**
     * Creates a DocumentBuilder configured to use the catalog resolver.
     *
     * @return configured DocumentBuilder
     * @throws ParserConfigurationException if parser cannot be configured
     */
    @Test
    public  void createDocumentBuilder() throws ParserConfigurationException {
        XMLResolver resolver = Resolvers.getResolver();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(resolver.getEntityResolver());


    }

    /**
     * Example: Resolve a public identifier to a local resource.
     *
     * @param publicId the public identifier to resolve
     * @param systemId the system identifier (may be null)
     */
    public static void resolve(String publicId, String systemId) {
        XMLResolver resolver = Resolvers.getResolver();

        try {
            InputSource source = resolver.getEntityResolver().resolveEntity(publicId, systemId);

            if (source != null) {
                System.out.println("Resolved:");
                System.out.println("  Public ID: " + publicId);
                System.out.println("  System ID: " + (systemId != null ? systemId : "null"));
                System.out.println("  Resolved to: " + source.getSystemId());

            } else {
                System.out.println("Could not resolve: " + publicId);
            }
        } catch (SAXException | IOException e) {
            System.err.println("Error resolving entity: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ISO 8879-1986//ENTITIES Added Latin 1//EN//XML",
        "-//OASIS//DTD DocBook XML V4.1.2//EN",
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN"

    })
    public void resolvePublicId(String publicId) {
        resolve(publicId, null);
    }
    /**
     * Main method demonstrating various catalog lookups.
     */
    public static void main(String[] args) {
        // Example 2: Resolve XHTML DTD
//        resolveExample(
//            "-//W3C//DTD XHTML 1.0 Strict//EN",
//            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
  //      );

    }

}