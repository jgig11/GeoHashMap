package geoHashMap;
import java.text.DecimalFormat;
import java.util.*;
public class Geohash {
    private static int numbits = 3 * 5;
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
 
    final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
    static {
        int i = 0;
        for (char c : digits) {
            lookup.put(c, i++);
        }
    }
 
    public static void main(String[] args) {
//        double[] latlon = new Geohash().decode("wwgqyc");
//        System.out.println("[["+latlon[1]+","+latlon[0]+"],["+latlon[3]+","+latlon[0]+"],["+latlon[3]+","+latlon[2]+"],["+latlon[1]+","+latlon[2]+"]]");
//        Geohash e = new Geohash();
//        String s = e.encode(39.16269,117.373774 );
//        System.out.println(s);
//        latlon = e.decode(s);
//        DecimalFormat df = new DecimalFormat("0.00000");
//        System.out.println(df.format(latlon[0]) + ", " + df.format(latlon[1]));
         
    }
 
    /**
     * ��Geohash�ִ�����ɾ�γֵ
     * 
     * @param geohash
     *            �������Geohash�ִ�
     * @return ��γֵ����
     */
    public double[] decode(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }
 
        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();
        // even bits
        int j = 0;
//        System.out.print("lon:");
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
 //           System.out.print(buffer.charAt(i));
            lonset.set(j++, isSet);
        }
        // odd bits
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length()){    
            	isSet = buffer.charAt(i) == '1';
            	latset.set(j++, isSet);
            }
        }  
        double[] lat = decode(latset, -90, 90);
        double[] lon = decode(lonset, -180, 180);
        double latmin = lat[0];
        double latmax = lat[1];
        double lonmin = lon[0];
        double lonmax = lon[1];
//        double latmid = (lat[0]+lat[1])/2;
//        double lonmid = (lon[0]+lon[1])/2;
        
        return new double[] { latmin, lonmin,latmax, lonmax };
    }
 
    /**
     * ���ݶ����Ʊ��봮��ָ������ֵ�仯��Χ������õ���/γֵ
     * 
     * @param bs
     *            ��/γ�����Ʊ��봮
     * @param floor
     *            ����
     * @param ceiling
     *            ����
     * @return ��/γֵ
     */
    private double[] decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        double[] range = new double[2];
        for (int i = 0; i < 15; i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
            range[0] = floor;
            range[1] = ceiling;
        }
        return range;
    }
 
    /**
     * ���ݾ�γֵ�õ�Geohash�ִ�
     * 
     * @param lat
     *            γ��ֵ
     * @param lon
     *            ����ֵ
     * @return Geohash�ִ�
     */
    public String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }
 
    /**
     * �������Ʊ��봮ת����Geohash�ִ�
     * 
     * @param i
     *            �����Ʊ��봮
     * @return Geohash�ִ�
     */
    public static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];
 
        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }
 
    /**
     * �õ���/γ�ȶ�Ӧ�Ķ����Ʊ���
     * 
     * @param lat
     *            ��/γ��
     * @param floor
     *            ����
     * @param ceiling
     *            ����
     * @return �����Ʊ��봮
     */
    private BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }
 
}