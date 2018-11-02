package org.art.web.rss.utils.xml;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

@Log4j2
public class StaxStreamProcessor implements AutoCloseable {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newFactory();

    private XMLStreamReader reader;

    private String charset;

    public StaxStreamProcessor(InputStream inputStream, String charset) throws XMLStreamException {
        this.charset = charset;
        this.reader = FACTORY.createXMLStreamReader(inputStream, charset);
    }

    public boolean hasNext() throws XMLStreamException {
        return reader != null && reader.hasNext();
    }

    public boolean doUntil(int event, String value) throws XMLStreamException {
        if (reader == null || value == null) {
            return false;
        }
        while (reader.hasNext()) {
            int e = reader.next();
            if (e == event && e != XMLEvent.CHARACTERS && value.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }

    public String getText() throws XMLStreamException {
        return reader != null && reader.hasNext() ? reader.getElementText().trim() : StringUtils.EMPTY;
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
