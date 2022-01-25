package com.momolela.io.stream;

import java.io.IOException;
import java.io.InputStream;

public class IO12SystemIn {

    public static void main(String[] args) throws IOException {
        InputStream in = System.in;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int ch = in.read();
            if (ch == '\r') {
                continue;
            } else if (ch == '\n') {
                String s = sb.toString();
                if (s.equals("over")) {
                    break;
                }
                System.out.println(s);
                sb.delete(0, sb.length());
            } else {
                sb.append((char) ch);
            }
        }
    }

}
