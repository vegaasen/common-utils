package com.suiveg.utils.application;

import com.suiveg.utils.string.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="vegard.aasen@telenor.com">Vegard Aasen</a>
 */
public final class ApplicationUtils {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy@HH:mm:ss");

    private static String lastCompiledDate = "";

    public static String getLastCompiledDate() {
        if(StringUtils.isBlank(lastCompiledDate)) {
            try {
                final URI uri = ApplicationUtils.class.getClassLoader().getResource(
                        ApplicationUtils.class.getCanonicalName().replace('.', '/') + ".class").toURI();
                if(uri!=null) {
                    final File file = new File(uri);
                    if(file.exists()) {
                        final Date lastChanged = new Date(file.lastModified());
                        lastCompiledDate = FORMATTER.format(lastChanged);
                    }
                }
            } catch (Exception e) {
                //todo: logger
            }
        }
        return lastCompiledDate;
    }

}
