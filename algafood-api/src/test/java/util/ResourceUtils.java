package util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourceUtils {

    public static String getConteudoDoRecurso(String nomeRecurso){
        try {
            InputStream stream = ResourceUtils.class.getResourceAsStream(nomeRecurso);
            return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

}
