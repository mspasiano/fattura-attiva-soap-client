package it.cnr.si;

import it.cnr.contab.sigla.FatturaAttivaComponentWS;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${client.ws.address}")
    private String address;

    @Value("${client.ws.user-name}")
    private String userName;

    @Value("${client.ws.password}")
    private String password;

    @Bean(name = "fatturaAttivaComponentWS")
    public FatturaAttivaComponentWS fatturaAttivaComponentWS() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean =
                new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(FatturaAttivaComponentWS.class);
        jaxWsProxyFactoryBean.setAddress(address);

        return (FatturaAttivaComponentWS) jaxWsProxyFactoryBean.create();
    }

    @Bean
    public Client ticketAgentClientProxy() {
        return ClientProxy.getClient(fatturaAttivaComponentWS());
    }

    @Bean
    public HTTPConduit ticketAgentConduit() {
        HTTPConduit httpConduit =
                (HTTPConduit) ticketAgentClientProxy().getConduit();
        httpConduit.setAuthorization(basicAuthorization());

        return httpConduit;
    }

    @Bean
    public AuthorizationPolicy basicAuthorization() {
        AuthorizationPolicy authorizationPolicy =
                new AuthorizationPolicy();
        authorizationPolicy.setUserName(userName);
        authorizationPolicy.setPassword(password);
        authorizationPolicy.setAuthorizationType("Basic");

        return authorizationPolicy;
    }
}