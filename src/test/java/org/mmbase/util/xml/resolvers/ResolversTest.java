package org.mmbase.util.xml.resolvers;

import java.io.IOException;
import java.io.InputStream;
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

                InputStream byteStream = source.getByteStream();
                if (byteStream != null) {
                    try (InputStream in = byteStream) {
                        byte[] buffer = new byte[128];
                        int read = in.read(buffer);
                        if (read > 0) {
                            System.out.write(buffer, 0, read);
                        }
                        System.out.flush();
                    }
                } else {
                    System.out.println("  No byte stream available in resolved source.");
                }
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
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Arrow Relations//EN//XML",
        "-//OASIS//DTD DocBook XML V4.1.2//EN",
        "-//OASIS//ENTITIES DocBook XML Notations V4.1.2//EN",
        "-//OASIS//ENTITIES DocBook XML Character Entities V4.1.2//EN",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Arrow Relations//EN//XML",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Binary Operators//EN//XML",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Delimiters//EN//XML",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Negated Relations//EN//XML",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Ordinary//EN//XML",
        "ISO 8879:1986//ENTITIES Added Math Symbols: Relations//EN//XML",
        "ISO 8879:1986//ENTITIES Box and Line Drawing//EN//XML",
        "ISO 8879:1986//ENTITIES Russian Cyrillic//EN//XML",
        "ISO 8879:1986//ENTITIES Non-Russian Cyrillic//EN//XML",
        "ISO 8879:1986//ENTITIES Diacritical Marks//EN//XML",
        "ISO 8879:1986//ENTITIES Greek Letters//EN//XML",
        "ISO 8879:1986//ENTITIES Monotoniko Greek//EN//XML",
        "ISO 8879:1986//ENTITIES Greek Symbols//EN//XML",
        "ISO 8879:1986//ENTITIES Alternative Greek Symbols//EN//XML",
        "ISO 8879:1986//ENTITIES Added Latin 1//EN//XML",
        "ISO 8879:1986//ENTITIES Added Latin 2//EN//XML",
        "ISO 8879:1986//ENTITIES Numeric and Special Graphic//EN//XML",
        "ISO 8879:1986//ENTITIES Publishing//EN//XML",
        "ISO 8879:1986//ENTITIES General Technical//EN//XML",
        "-//OASIS//ELEMENTS DocBook XML Information Pool V4.1.2//EN",
        "-//OASIS//DTD DocBook XML CALS Table Model V4.1.2//EN",
        "-//OASIS//ELEMENTS DocBook XML Document Hierarchy V4.1.2//EN",
        "-//OASIS//ENTITIES DocBook XML Additional General Entities V4.1.2//EN"
    })
    public void resolvePublicId(String publicId) {
        resolve(publicId, null);
    }


    @ParameterizedTest
    @ValueSource(strings = {
        "http://www.oasis-open.org/docbook/xml/4.1.2/ent/iso-amsa.ent",


    })
    public void resolveSystemId(String systemId) {
        resolve(null, systemId);
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