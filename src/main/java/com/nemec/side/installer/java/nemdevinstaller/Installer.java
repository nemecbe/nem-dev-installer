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
    public static void main(String[] args) {
        System.out.println("Hello Installer!");
    }
}
