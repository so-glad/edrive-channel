package so.glad.channel.edrive.entity;

import java.io.InputStream;
import java.io.OutputStream;

import org.jibx.runtime.*;

import so.glad.serializer.MarshalException;
import so.glad.serializer.StreamSerializer;
import so.glad.serializer.UnmarshalException;

/**
 * Created by Cartoon on 2015/3/26.
 */
public class JibxStreamSerializer implements StreamSerializer {

    private IBindingFactory bindingFactory;

    public JibxStreamSerializer(){
        try {
            this.bindingFactory = BindingDirectory.getFactory("jibx_entity", Root.class);
        } catch (JiBXException e) {
            throw new IllegalStateException("Init Jibx binding factory failed", e);
        }
    }

    @Override
    public void marshal(Object object, OutputStream outputStream, String encoding) throws MarshalException {
        try {
            IMarshallingContext marshallingContext = bindingFactory.createMarshallingContext();
            marshallingContext.marshalDocument(object, encoding, null, outputStream);
        } catch (JiBXException e) {
            throw new MarshalException(e);
        }
    }

    @Override
    public Object unmarshal(InputStream inputStream, String encoding) throws UnmarshalException {
        try {
            IUnmarshallingContext unmarshallingContext = bindingFactory.createUnmarshallingContext();
            return unmarshallingContext.unmarshalDocument(inputStream, encoding);
        } catch (JiBXException e) {
            throw new UnmarshalException(e);
        }
    }
}
