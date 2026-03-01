# MMBase Entities - OASIS Catalog Repository

This module provides a collection of XML DTDs and entity definitions with OASIS catalog files for offline XML processing.

## What's Included

### Entity Definitions
- ISO character entity sets (Latin, Greek, Math symbols, etc.)
- Custom character entities

### DTD Repository
- **W3C Standards**: XHTML 1.0/1.1, SVG DTDs
- **OASIS Standards**: DocBook XML V4.0, V4.1.2
- **Java EE**: Web Application DTDs

## Using the Catalog Files

### Modern Approach (2024+): org.xmlresolver

The project uses **[XMLResolver 6.x](https://github.com/xmlresolver/xmlresolver)** by Norman Walsh, the modern successor to Apache XML Commons Resolver.

**Maven dependency:**
```xml
<dependency>
    <groupId>org.xmlresolver</groupId>
    <artifactId>xmlresolver</artifactId>
    <version>6.0.21</version>
</dependency>
```

**Key advantages over the old xml-resolver:1.2 (2006):**
- Active maintenance (latest release: 2025)
- Java 9+ module support
- Better performance
- Improved catalog parsing
- Support for XML Catalog 1.1 specification

### Usage Example

```java
import org.xmlresolver.Resolver;
import org.xmlresolver.XMLResolverConfiguration;

// Create resolver with catalog
XMLResolverConfiguration config = new XMLResolverConfiguration();
config.addCatalog("classpath:entities/catalog");
Resolver resolver = new Resolver(config);

// Use with XML parser
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
builder.setEntityResolver(resolver);
```

See `org.mmbase.util.xml.EntityResolverExample` for complete examples.

### Alternative: Java Built-in API (Java 9+)

Java 9+ includes `javax.xml.catalog` package with built-in catalog support:

```java
import javax.xml.catalog.*;

CatalogResolver resolver = CatalogManager.catalogResolver(
    CatalogFeatures.defaults(),
    URI.create("file:///path/to/catalog")
);
```

## Catalog Structure

```
entities/
├── catalog                     # Main catalog
├── *.pen, *.ent               # ISO entity definitions
├── w3c/
│   ├── catalog                # W3C catalog
│   └── *.dtd                  # XHTML, SVG DTDs
└── oasis/
    ├── 4.0/
    │   └── docbookx.dtd
    └── 4.1.2/
        ├── docbook.cat        # DocBook catalog
        ├── docbookx.dtd
        └── ent/*.ent          # DocBook entities
```

## Benefits

1. **Offline Processing**: Parse XML without internet connectivity
2. **Performance**: No network latency for DTD/entity lookups
3. **Stability**: Consistent entity definitions regardless of external changes
4. **Security**: Prevent XXE attacks by controlling entity resolution
5. **Reliability**: No dependency on external servers being available

## Resources

- [OASIS XML Catalogs Specification](https://www.oasis-open.org/committees/entity/spec-2001-08-06.html)
- [XMLResolver Documentation](https://xmlresolver.github.io/xmlresolver/)
- [Java XML Catalog API](https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/javax/xml/catalog/package-summary.html)

