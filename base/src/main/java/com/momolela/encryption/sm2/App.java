package com.momolela.encryption.sm2;

import java.util.Arrays;

public class App {

    /**
     * 元消息串
     */
    private static String M = "哈哈哈，&*&…………&、、//\\!@#$%^&*()物品woyebuzhidaowozijiqiaodesha!@#$%^&*())))))ooooooooppppppppppppppppppplllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkffffffffffffffffffffffffffffffffffffff";

    public static void main(String[] args) {
        SM2Util sm2 = new SM2Util();
        SM2KeyPair keyPair = sm2.generateKeyPair();
        byte[] data = sm2.encrypt(M, keyPair.getPublicKey());
        System.out.println("data is:" + Arrays.toString(data));
        sm2.decrypt(data, keyPair.getPrivateKey()); // 71017045908707391874054405929626258767106914144911649587813342322113806533034
    }

}
