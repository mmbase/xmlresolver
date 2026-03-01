package org.mmbase.util.xml.resolvers;

import org.xmlresolver.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Example of using the XMLResolver library with OASIS catalog files.
 *
 * This demonstrates how to use the catalog files in the entities/ directory
 * to resolve DTDs and entity references locally instead of fetching them
 * from the internet.
 */
public class Resolvers {



    private static final XMLResolver resolver = createResolver();

    public static XMLResolver getResolver() {
        return resolver;
    }


    /**
     * Creates a configured XML Resolver that uses the catalog files
     * in the entities/ directory.
     *
     * @return configured Resolver instance
     */
    private static XMLResolver createResolver() {
        // Create a configuration for the resolver
        XMLResolverConfiguration config = new XMLResolverConfiguration();

        // Add catalog files from the classpath
        config.addCatalog("classpath:entities/catalog");
        config.addCatalog("classpath:entities/w3c/catalog");
        config.addCatalog("classpath:entities/oasis/4.1.2/docbook.cat");

        // Configure resolver features
        config.setFeature(ResolverFeature.PREFER_PUBLIC, true);
        config.setFeature(ResolverFeature.ALLOW_CATALOG_PI, true);

        // Create and return the resolver
        return new XMLResolver(config);
    }

}

