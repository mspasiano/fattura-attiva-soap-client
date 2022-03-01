package it.cnr.si;

import it.cnr.contab.sigla.FatturaAttiva;
import it.cnr.contab.sigla.FatturaAttivaComponentWS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FatturaAttivaTest {
    @Autowired
    private FatturaAttivaComponentWS fatturaAttivaComponentWS;

    @Test
    public void contextLoads() {
        final FatturaAttiva fatturaAttiva = fatturaAttivaComponentWS.ricercaFattura(
                "",
                new Long(2022),
                "044",
                "044.000",
                new Long(7)
        );
        assertEquals("04400020220comF00007", fatturaAttiva.getNumeroFattura());
    }

}
