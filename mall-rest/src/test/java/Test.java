import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

    public static void main(String[] args) throws IOException {
        long time = 1529337600000l;
        Date date = new Date(time);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));

    }
}
