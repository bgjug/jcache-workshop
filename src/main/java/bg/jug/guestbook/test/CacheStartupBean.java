/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.jug.guestbook.test;

import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

/**
 *
 * @author lprimak
 */
@Singleton @Startup
@Log
public class CacheStartupBean {
    @PostConstruct
    @SneakyThrows
    void init() {
        InitialContext ic = new InitialContext();
        CacheManager cm = (CacheManager) ic.lookup("payara/CacheManager");
        log.log(Level.INFO, "CacheManager: {0}", cm);
    }
}
