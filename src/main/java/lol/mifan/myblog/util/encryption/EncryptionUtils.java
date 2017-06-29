/**   
 * filename：EncryptionUtils.java
 *   
 * date：2016年4月22日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

//	 /**
//    * 32位MD5加密
//    * @author 范子才
//    * @param plainText
//    * @return
//    * @version 2016年4月19日 下午9:14:48
//    */
//   public static String get32BitMd5(String plainText) {
//       try {
//           MessageDigest md = MessageDigest.getInstance("MD5");
//           md.update(plainText.getBytes());
//           byte b[] = md.digest();
//
//           int i;
//
//           StringBuffer buf = new StringBuffer("");
//           for (byte aB : b) {
//               i = aB;
//               if (i < 0)
//                   i += 256;
//               if (i < 16)
//                   buf.append("0");
//               buf.append(Integer.toHexString(i));
//           }
//           //32位加密
//           return buf.toString();
//       } catch (NoSuchAlgorithmException e) {
//           e.printStackTrace();
//           return null;
//       }
//
//   }
//
//
//   /**
//    * 16位MD5加密
//    * @author 范子才
//    * @param plainText
//    * @return
//    * @version 2016年4月19日 下午9:14:48
//    */
//   public static String get16BitMd5(String plainText) {
//       try {
//           MessageDigest md = MessageDigest.getInstance("MD5");
//           md.update(plainText.getBytes());
//           byte b[] = md.digest();
//
//           int i;
//
//           StringBuffer buf = new StringBuffer("");
//           for (byte aB : b) {
//               i = aB;
//               if (i < 0)
//                   i += 256;
//               if (i < 16)
//                   buf.append("0");
//               buf.append(Integer.toHexString(i));
//           }
//           // 16位的加密
//           return buf.toString().substring(8, 24);
//       } catch (NoSuchAlgorithmException e) {
//           e.printStackTrace();
//           return null;
//       }
//
//   }


}
