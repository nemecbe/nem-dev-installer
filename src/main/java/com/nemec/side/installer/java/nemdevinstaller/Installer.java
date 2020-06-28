/*
 * Copyright (C) 2020 Benjamin Nemec
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nemec.side.installer.java.nemdevinstaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * The NemDev Installer is an application that will be used to automate the 
 * installation and update process for all of the desktop applications I develop.
 * 
 * This specific application is a Java installer, and as such will be used to 
 * handle the installation and update process of all Java applications I develop.
 * 
 * Be warned that while this application will theoretically work for any kind 
 * of distribution released on GitHub, it will only be tested with Java 
 * applications that I own on GitHub and in no way will be guaranteed to work 
 * for any other GitHub repositories or the retrieval of artifacts from any 
 * other kind of artifactory. Use at your own discretion!
 * 
 * @author Benjamin Nemec
 */
public class Installer {
    
    /**
     * Main method for the NemDev Installer Application
     * @param args is unused at this time in the application's development
     */
    public static void main(String[] args) throws Exception {
        String installerUrl = "https://api.github.com/repos/nemecbe/nem-dev-installer/releases/latest";

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(installerUrl);
            request.addHeader("content-type", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dataMap = mapper.readValue(json, Map.class);
            
            httpClient = HttpClientBuilder.create().build();
            List<Map<String, String>> assets = (List<Map<String, String>>)dataMap.get("assets");
            String assetDownloadUrl = assets.get(0).get("browser_download_url");
            request = new HttpGet(assetDownloadUrl);
            request.addHeader("content-type", assets.get(0).get("content_type"));
            result = httpClient.execute(request);
            
            byte[] installerZip = EntityUtils.toByteArray(result.getEntity());
            FileOutputStream fout = new FileOutputStream("installer.zip");
            fout.write(installerZip);
            fout.close();
            
            ZipInputStream zin = new ZipInputStream(new FileInputStream("installer.zip"));
            ZipEntry zEntry;
            while ((zEntry = zin.getNextEntry()) != null) {
                File target = new File("./testing");
                int BUFFER_SIZE = 4096;
                File file = new File(target, zEntry.getName());

//                if (!file.toPath().normalize().startsWith(target.toPath())) {
//                    throw new IOException("Bad zip entry");
//                }

                if (zEntry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                byte[] buffer = new byte[BUFFER_SIZE];
                file.getParentFile().mkdirs();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                int count;

                while ((count = zin.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }

                out.close();
            }
            zin.close();

            System.out.println(json);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
