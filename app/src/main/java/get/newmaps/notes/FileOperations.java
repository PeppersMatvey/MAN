package get.newmaps.notes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileOperations {
    enum ModeCipher{
        Decrypt, Encrypt;
    }
    public static Properties readPropertes(File f) throws Exception{
        Properties properties = new Properties();
        properties.load(new FileReader(f));
        return properties;
    }
    public static void sawePropertes(Properties p,File f) throws IOException {
        p.store(new FileOutputStream(f),"");
    }
    public static boolean filesCompareByByte(File path1, File path2) throws IOException {
        try (BufferedInputStream fis1 = new BufferedInputStream(new FileInputStream(path1));
             BufferedInputStream fis2 = new BufferedInputStream(new FileInputStream(path2))) {

            int ch = 0;
            long pos = 1;
            while ((ch = fis1.read()) != -1) {
                if (ch != fis2.read()) {
                    return false;
                }
                pos++;
            }
            if (fis2.read() == -1) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    public static void AESEncrypte(File in, File out, String sk, ModeCipher m) throws Exception{
        //sk=PasswordFormat.gt(sk);
        if(m==ModeCipher.Encrypt){
            addbytes(in);
        }
        FileInputStream fis=new FileInputStream(in);
        BufferedInputStream bis=new BufferedInputStream(fis);
        FileOutputStream fos=new FileOutputStream(out);
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        Cipher cip=Cipher.getInstance("AES/CBC/PKCS5Padding");
        MessageDigest MD=MessageDigest.getInstance("MD5");
        MessageDigest MD512=MessageDigest.getInstance("SHA-512");
        byte[] vv=MD.digest(sk.getBytes());
        sk.toUpperCase().getBytes();
        StringBuilder sb=new StringBuilder(sk);
        sb.reverse();
        sk=sb.toString();
        IvParameterSpec ipv=new IvParameterSpec(vv);
        if(m==ModeCipher.Encrypt){
            cip.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(MD.digest(MD512.digest(sk.getBytes())),"AES"),ipv);
        }else{
            cip.init(Cipher.DECRYPT_MODE,new SecretKeySpec(MD.digest(MD512.digest(sk.getBytes())),"AES"),ipv);
        }
        byte[] buffer = new byte[65536];
        while (bis.available() > 0){
            int bytesRead = bis.read(buffer);
            if(bytesRead<65536){
                Arrays.copyOf(buffer,65520);
            }
            byte[] output = cip.update(buffer, 0, bytesRead);
            if (output != null) {
                bos.write(output);
            }
        }
        if(m==ModeCipher.Encrypt){
            byte[] outputBytes = cip.doFinal();
            if (outputBytes != null) {
                bos.write(outputBytes);
            }
        }
        bis.close();
        bos.close();
    }
    public static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
    public static boolean compareFileString(File f1,File f2) throws Exception{
        String str1=readFile(f1.getAbsolutePath()).trim();
        String str2=readFile(f2.getAbsolutePath()).trim();
        return str1.equals(str2);
    }
    public static boolean compareByMemoryMappedFiles(File path1, File path2) throws IOException {
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1, "r");
             RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2, "r")) {

            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
            if (ch1.size() != ch2.size()) {
                return false;
            }
            long size = ch1.size();
            MappedByteBuffer m1 = ch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            MappedByteBuffer m2 = ch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);

            return m1.equals(m2);
        }
    }
    public static void writeNullAndDelete(File adress){
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile=new RandomAccessFile(adress,"rw");
            randomAccessFile.write(new byte[(int)adress.length()]);
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adress.delete();
    }
    private static void addbytes(File in) throws IOException {
        FileOutputStream fos=new FileOutputStream(in,true);
        String str=" ";
        for(int i=0;i<40;i++){
            str=str+" ";
        }
        fos.write(str.getBytes());
        fos.close();
    }
    public static synchronized void copy(File in,File out) throws Exception{
        FileInputStream fis=new FileInputStream(in);
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(out));
        byte[] buffer=new byte[65536];
        while (fis.available() > 0) {
            int real = fis.read(buffer);
            bos.write(buffer, 0, real);
        }
        bos.close();
        fis.close();
    }

    public static synchronized void copy(File in, OutputStream out) throws Exception{
        FileInputStream fis=new FileInputStream(in);
        byte[] buffer=new byte[65536];
        while (fis.available() > 0) {
            int real = fis.read(buffer);
            out.write(buffer, 0, real);
        }
        fis.close();
    }
    public static ArrayList<Imports.OneHome> readHomes() throws Exception {
        ArrayList<Imports.OneHome> arrayList=new ArrayList<>();
        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)1)).exists()){
            ArrayList<Imports.OneHome> arrayList1=readListHome((byte)1);
            for(Imports.OneHome one:arrayList1){
                arrayList.add(one);
            }
        }
        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)2)).exists()){
            ArrayList<Imports.OneHome> arrayList1=readListHome((byte)2);
            for(Imports.OneHome one:arrayList1){
                arrayList.add(one);
            }
        }
        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)3)).exists()){
            ArrayList<Imports.OneHome> arrayList1=readListHome((byte)3);
            for(Imports.OneHome one:arrayList1){
                arrayList.add(one);
            }
        }
        if(new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,(byte)4)).exists()){
            ArrayList<Imports.OneHome> arrayList1=readListHome((byte)4);
            for(Imports.OneHome one:arrayList1){
                arrayList.add(one);
            }
        }
        return arrayList;
    }
    public static ArrayList<Imports.OneHome> readListHome(byte zone) throws Exception{
        if(!new File(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,zone)).exists()){
            return new ArrayList<Imports.OneHome>();
        }
        DirToFile dirToFile=new DirToFile();
        dirToFile.clear();
        dirToFile.cht(CreatePatch.getPatch(CreatePatch.MODE_TYPE.DECRYPTE,zone));
        ArrayList<Imports.OneHome> arrayList=new ArrayList<>();
        for(File on:dirToFile.getSPISOCFile()){
            Imports.OneHome oneHome = new Imports().readHome(on);
            oneHome.zone = zone;
            arrayList.add(oneHome);
        }
        return arrayList;
    }
}
