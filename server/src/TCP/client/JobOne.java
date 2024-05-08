package TCP.client;

import TCP.intefaces.Executable;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class JobOne implements Executable, Serializable {
    private final static long serialVersionUID = -1L;
    private int n;

    public JobOne(int n) {
        this.n = n;
    }

    @Override
    public Object execute() {
        return Math.pow(n, 2);
    }
}


