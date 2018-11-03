package org.art.web.rss.utils.xml;

import lombok.extern.log4j.Log4j2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

@Log4j2
public class StaxStreamProcessor implements AutoCloseable {

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newFactory();

    private XMLStreamReader reader;

    private String charset;

    public StaxStreamProcessor(InputStream inputStream, String charset) throws XMLStreamException {
        this.charset = charset;
        this.reader = XML_INPUT_FACTORY.createXMLStreamReader(inputStream, charset);
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                log.info("Exception while closing XMLStreamReader", e);
            }
        }
    }
}
