package com.bird.framework.xsy.mall.service.ansj;

import org.ansj.dic.PathToStream;
import org.ansj.exception.LibraryException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathToStream extends PathToStream {
    @Override
    public InputStream toStream(String path) {
        path = path.substring(8).split("\\|")[1];
        String root = ClassLoader.getSystemResource("").getPath();
        String filePath = root + path;

        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new LibraryException(e);
        }
    }
}
