package org.hgq.test;


import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ImageToString {
    //private static Log log = LogFactory.getLog(ImageToString.class);


  /*  public static void main(String[] args) throws Exception {
        InputStream in = readUrl("https://flm-all-test.obs.ap-southeast-2.myhuaweicloud.com/fslface/a074270c-2f75-44e5-a0db-8f9400be6774.jpg?AWSAccessKeyId=WZ35JXPLW0U3HHDDMWKC&Expires=1628763819&Signature=2EPW1XAZ7kb3byxkQASXZKkpYg8%3D");

        BufferedImage bImage = ImageIO.read(in);

        System.out.println(bImage.toString());
        System.out.println("=========================");
        byte[] img = getBytes(bImage);

        String string = new String(img,"iso-8859-1");

        System.out.println(string);
    }*/

// 根据图片地址将图片转换为字符串类型的数据

    public String imageToString(String picture) {
        StringBuffer sb2 = new StringBuffer();

        BufferedImage image1 = getImage(picture);

        byte[] img = getBytes(image1);

        for (int i = 0; i < img.length; i++) {
            if (sb2.length() == 0) {
                sb2.append(img[i]);

            } else {
                sb2.append("," + img[i]);

            }

        }

        return sb2.toString();

    }

// 将BufferImage 转换为字节数组

    private static byte[] getBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", baos);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return baos.toByteArray();

    }

// 根据图片地址得到BufferedImage

    public static BufferedImage getImage(String picture) {
        try {
            BufferedImage bImage = ImageIO.read(new File(picture));

            return bImage;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;

        }

    }

    @Test
    public void test() {
        try {
            InputStream in = readUrl("https://flm-all-test.obs.ap-southeast-2.myhuaweicloud.com/fslface/a074270c-2f75-44e5-a0db-8f9400be6774.jpg?AWSAccessKeyId=WZ35JXPLW0U3HHDDMWKC&Expires=1628763819&Signature=2EPW1XAZ7kb3byxkQASXZKkpYg8%3D");

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            // String s = out.toString(StandardCharsets.UTF_8.name());
            //byte[] bytes = out.toByteArray();
            // String s = new String(bytes, "utf-8");

            String s = bytesToHex(out.toByteArray());

            // 加密
//            BASE64Encoder encoder = new BASE64Encoder();
//            String s = encoder.encode(out.toByteArray());

            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static InputStream readUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return inputStream;
    }

    /**
     * 字符串转图片
     *
     * @param imgStr   --->图片字符串
     * @param filename --->图片名
     * @return
     */
    public static boolean generateImage(String imgStr, String filename) {

        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("D:/Systems/" + filename);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 图片转字符串
     *
     * @param filePath --->文件路径
     * @return
     */
    public static String getImageStr(String filePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(filePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /*
     * 测试代码
     */
    /*public static void main(String[] args) {
        String imageStr = getImageStr("D:\\001.jpg");
        System.out.println(imageStr);
        boolean generateImage = generateImage(imageStr, "001.jpg");
        System.out.println(generateImage);
    }*/

    public static String byteToString(byte[] buf) {
        String str1 = "";
        StringBuilder sb = new StringBuilder(str1);
        for (byte element : buf) {
            sb.append(element);
        }
        str1 = sb.toString();
        return str1;
    }

    public static void main(String[] args) throws Exception {
//        File imgFile = new File("d:\\1.jpg");
//        FileInputStream fis = new FileInputStream( imgFile );
        InputStream in = readUrl("https://flm-all-test.obs.ap-southeast-2.myhuaweicloud.com/fslface/a074270c-2f75-44e5-a0db-8f9400be6774.jpg?AWSAccessKeyId=WZ35JXPLW0U3HHDDMWKC&Expires=1628763819&Signature=2EPW1XAZ7kb3byxkQASXZKkpYg8%3D");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }

        String imgStr = new String(out.toByteArray());
        System.out.println(imgStr);
    }

}
