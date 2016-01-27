package com.github.davidmoten.ppk.maven;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.davidmoten.security.PPK;

@Mojo(name = "decrypt")
public final class DecryptMojo extends AbstractMojo {

    @Parameter(property = "privateKeyFile")
    private File privateKeyFile;

    @Parameter(property = "inputFile")
    private File inputFile;

    @Parameter(property = "outputFile")
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("inputFile exists " + inputFile.exists());
        outputFile.getParentFile().mkdirs();
        try (InputStream is = new BufferedInputStream(new FileInputStream(inputFile));
                OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));) {
            PPK.privateKey(privateKeyFile).decrypt(is, os);
        } catch (IOException e) {
            throw new MojoExecutionException("decryption failed: " + e.getMessage(), e);
        }
    }

}