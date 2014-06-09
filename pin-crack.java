import java.security.MessageDigest;

class test {
   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println("Usage: salt hash [digits=4]");
         System.exit(1);
      }

      int digits = 4;
      int max = 9999;
      if (args.length == 3) {
         digits = Integer.parseInt(args[2]);
         max = (int) Math.pow(10, digits) - 1;
      }

      String pass = args[1];
      String salt = makeSalt(Long.parseLong(args[0]));
      
      System.out.println("Up to: " + max);

      for (int i = 0; i < max; i++) {
         byte[] hash = passwordToHash("" + i, salt);
         String hashs = new String(hash);
         if (hashs.equals(pass)) {
            System.out.println(hashs + ":" + i);
         }
      }

  }

   public static byte[] passwordToHash(String password, String salt) {
      if (password == null) {
         return null;
      }
      String algo = null;
      byte[] hashed = null;
      try {
         byte[] saltedPassword = (password + salt).getBytes();
         byte[] sha1 = MessageDigest.getInstance(algo = "SHA-1").digest(saltedPassword);
         byte[] md5 = MessageDigest.getInstance(algo = "MD5").digest(saltedPassword);
         hashed = (toHex(sha1) + toHex(md5)).getBytes();
      } catch (Exception e) {
         System.out.println("Failed to encode string because of missing algorithm: " + algo);
      }
      return hashed;
   }

   public static String toHex(byte[] ary) {
      final String hex = "0123456789ABCDEF";
      String ret = "";
      for (int i = 0; i < ary.length; i++) {
         ret += hex.charAt((ary[i] >> 4) & 0xf);
         ret += hex.charAt(ary[i] & 0xf);
      }
      return ret;
   }

   public static String makeSalt(Long i) {
      return Long.toHexString(i);
   }
}
