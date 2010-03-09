package com.atech.utils;

public class CRCUtils extends HexUtils
{

        private static int CRC8_LOOKUP_TABLE[] = {
            0, 155, 173, 54, 193, 90, 108, 247, 25, 130, 
            180, 47, 216, 67, 117, 238, 50, 169, 159, 4, 
            243, 104, 94, 197, 43, 176, 134, 29, 234, 113, 
            71, 220, 100, 255, 201, 82, 165, 62, 8, 147, 
            125, 230, 208, 75, 188, 39, 17, 138, 86, 205, 
            251, 96, 151, 12, 58, 161, 79, 212, 226, 121, 
            142, 21, 35, 184, 200, 83, 101, 254, 9, 146, 
            164, 63, 209, 74, 124, 231, 16, 139, 189, 38, 
            250, 97, 87, 204, 59, 160, 150, 13, 227, 120, 
            78, 213, 34, 185, 143, 20, 172, 55, 1, 154, 
            109, 246, 192, 91, 181, 46, 24, 131, 116, 239, 
            217, 66, 158, 5, 51, 168, 95, 196, 242, 105, 
            135, 28, 42, 177, 70, 221, 235, 112, 11, 144, 
            166, 61, 202, 81, 103, 252, 18, 137, 191, 36, 
            211, 72, 126, 229, 57, 162, 148, 15, 248, 99, 
            85, 206, 32, 187, 141, 22, 225, 122, 76, 215, 
            111, 244, 194, 89, 174, 53, 3, 152, 118, 237, 
            219, 64, 183, 44, 26, 129, 93, 198, 240, 107, 
            156, 7, 49, 170, 68, 223, 233, 114, 133, 30, 
            40, 179, 195, 88, 110, 245, 2, 153, 175, 52, 
            218, 65, 119, 236, 27, 128, 182, 45, 241, 106, 
            92, 199, 48, 171, 157, 6, 232, 115, 69, 222, 
            41, 178, 132, 31, 167, 60, 10, 145, 102, 253, 
            203, 80, 190, 37, 19, 136, 127, 228, 210, 73, 
            149, 14, 56, 163, 84, 207, 249, 98, 140, 23, 
            33, 186, 77, 214, 224, 123
        };
        
        private static int CRC8_LOOKUP_TABLE_BD[] = {
            0, 94, 188, 226, 97, 63, 221, 131, 194, 156, 
            126, 32, 163, 253, 31, 65, 157, 195, 33, 127, 
            252, 162, 64, 30, 95, 1, 227, 189, 62, 96, 
            130, 220, 35, 125, 159, 193, 66, 28, 254, 160, 
            225, 191, 93, 3, 128, 222, 60, 98, 190, 224, 
            2, 92, 223, 129, 99, 61, 124, 34, 192, 158, 
            29, 67, 161, 255, 70, 24, 250, 164, 39, 121, 
            155, 197, 132, 218, 56, 102, 229, 187, 89, 7, 
            219, 133, 103, 57, 186, 228, 6, 88, 25, 71, 
            165, 251, 120, 38, 196, 154, 101, 59, 217, 135, 
            4, 90, 184, 230, 167, 249, 27, 69, 198, 152, 
            122, 36, 248, 166, 68, 26, 153, 199, 37, 123, 
            58, 100, 134, 216, 91, 5, 231, 185, 140, 210, 
            48, 110, 237, 179, 81, 15, 78, 16, 242, 172, 
            47, 113, 147, 205, 17, 79, 173, 243, 112, 46, 
            204, 146, 211, 141, 111, 49, 178, 236, 14, 80, 
            175, 241, 19, 77, 206, 144, 114, 44, 109, 51, 
            209, 143, 12, 82, 176, 238, 50, 108, 142, 208, 
            83, 13, 239, 177, 240, 174, 76, 18, 145, 207, 
            45, 115, 202, 148, 118, 40, 171, 245, 23, 73, 
            8, 86, 180, 234, 105, 55, 213, 139, 87, 9, 
            235, 181, 54, 104, 138, 212, 149, 203, 41, 119, 
            244, 170, 72, 22, 233, 183, 85, 11, 136, 214, 
            52, 106, 43, 117, 151, 201, 74, 20, 246, 168, 
            116, 42, 200, 150, 21, 75, 169, 247, 182, 232, 
            10, 84, 215, 137, 107, 53
        };
        
        private static int CRC16_CCITT_LOOKUP_TABLE[] = {
            0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161, 
            41290, 45419, 49548, 53677, 57806, 61935, 4657, 528, 12915, 8786, 
            21173, 17044, 29431, 25302, 37689, 33560, 45947, 41818, 54205, 50076, 
            62463, 58334, 9314, 13379, 1056, 5121, 25830, 29895, 17572, 21637, 
            42346, 46411, 34088, 38153, 58862, 62927, 50604, 54669, 13907, 9842, 
            5649, 1584, 30423, 26358, 22165, 18100, 46939, 42874, 38681, 34616, 
            63455, 59390, 55197, 51132, 18628, 22757, 26758, 30887, 2112, 6241, 
            10242, 14371, 51660, 55789, 59790, 63919, 35144, 39273, 43274, 47403, 
            23285, 19156, 31415, 27286, 6769, 2640, 14899, 10770, 56317, 52188, 
            64447, 60318, 39801, 35672, 47931, 43802, 27814, 31879, 19684, 23749, 
            11298, 15363, 3168, 7233, 60846, 64911, 52716, 56781, 44330, 48395, 
            36200, 40265, 32407, 28342, 24277, 20212, 15891, 11826, 7761, 3696, 
            65439, 61374, 57309, 53244, 48923, 44858, 40793, 36728, 37256, 33193, 
            45514, 41451, 53516, 49453, 61774, 57711, 4224, 161, 12482, 8419, 
            20484, 16421, 28742, 24679, 33721, 37784, 41979, 46042, 49981, 54044, 
            58239, 62302, 689, 4752, 8947, 13010, 16949, 21012, 25207, 29270, 
            46570, 42443, 38312, 34185, 62830, 58703, 54572, 50445, 13538, 9411, 
            5280, 1153, 29798, 25671, 21540, 17413, 42971, 47098, 34713, 38840, 
            59231, 63358, 50973, 55100, 9939, 14066, 1681, 5808, 26199, 30326, 
            17941, 22068, 55628, 51565, 63758, 59695, 39368, 35305, 47498, 43435, 
            22596, 18533, 30726, 26663, 6336, 2273, 14466, 10403, 52093, 56156, 
            60223, 64286, 35833, 39896, 43963, 48026, 19061, 23124, 27191, 31254, 
            2801, 6864, 10931, 14994, 64814, 60687, 56684, 52557, 48554, 44427, 
            40424, 36297, 31782, 27655, 23652, 19525, 15522, 11395, 7392, 3265, 
            61215, 65342, 53085, 57212, 44955, 49082, 36825, 40952, 28183, 32310, 
            20053, 24180, 11923, 16050, 3793, 7920
        };
        
        private static int CRC7_LOOKUP_TABLE[] = {
            0, 26, 52, 46, 104, 114, 92, 70, 93, 71, 
            105, 115, 53, 47, 1, 27, 55, 45, 3, 25, 
            95, 69, 107, 113, 106, 112, 94, 68, 2, 24, 
            54, 44, 110, 116, 90, 64, 6, 28, 50, 40, 
            51, 41, 7, 29, 91, 65, 111, 117, 89, 67, 
            109, 119, 49, 43, 5, 31, 4, 30, 48, 42, 
            108, 118, 88, 66, 81, 75, 101, 127, 57, 35, 
            13, 23, 12, 22, 56, 34, 100, 126, 80, 74, 
            102, 124, 82, 72, 14, 20, 58, 32, 59, 33, 
            15, 21, 83, 73, 103, 125, 63, 37, 11, 17, 
            87, 77, 99, 121, 98, 120, 86, 76, 10, 16, 
            62, 36, 8, 18, 60, 38, 96, 122, 84, 78, 
            85, 79, 97, 123, 61, 39, 9, 19, 47, 53, 
            27, 1, 71, 93, 115, 105, 114, 104, 70, 92, 
            26, 0, 46, 52, 24, 2, 44, 54, 112, 106, 
            68, 94, 69, 95, 113, 107, 45, 55, 25, 3, 
            65, 91, 117, 111, 41, 51, 29, 7, 28, 6, 
            40, 50, 116, 110, 64, 90, 118, 108, 66, 88, 
            30, 4, 42, 48, 43, 49, 31, 5, 67, 89, 
            119, 109, 126, 100, 74, 80, 22, 12, 34, 56, 
            35, 57, 23, 13, 75, 81, 127, 101, 73, 83, 
            125, 103, 33, 59, 21, 15, 20, 14, 32, 58, 
            124, 102, 72, 82, 16, 10, 36, 62, 120, 98, 
            76, 86, 77, 87, 121, 99, 37, 63, 17, 11, 
            39, 61, 19, 9, 79, 85, 123, 97, 122, 96, 
            78, 84, 18, 8, 38, 60
        };
    
    
    public int computeCRC7(int ai[], int i, int j)
    {
        int k = 127;
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= i + j, "data array is too small.");
        for(int l = 0; l < j; l++)
            k = CRCUtils.CRC7_LOOKUP_TABLE[k] ^ ai[l + i];

        return k;
    }

    public int computeCRC8(int ai[], int i, int j)
    {
        int k = 0;
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= i + j, "data array is too small.");
        for(int l = 0; l < j; l++)
            k = CRCUtils.CRC8_LOOKUP_TABLE[(k ^ ai[l + i]) & 0xff];

        return k;
    }

    public int computeCRC8BD(int ai[], int i, int j)
    {
        int k = 0;
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= i + j, "data array is too small.");
        for(int l = 0; l < j; l++)
            k = CRCUtils.CRC8_LOOKUP_TABLE_BD[(k ^ ai[l + i]) & 0xff];

        return k;
    }

    public int computeCRC8E1381(int ai[])
    {
        int i = 0;
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= 2, "data array is too small.");
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        int j = 0;
        for(int k = 0; k < ai.length && !flag2; k++)
            switch(ai[k])
            {
            case 2: // '\002'
                flag = true;
                break;

            case 23: // '\027'
                flag1 = true;
                j = ai[k];
                break;

            case 3: // '\003'
                flag1 = true;
                j = ai[k];
                break;

            default:
                if(flag && !flag1)
                {
                    i += ai[k];
                    i &= 0xff;
                    break;
                }
                if(flag && flag1)
                {
                    i += j;
                    i &= 0xff;
                    flag2 = true;
                }
                break;
            }

        return i;
    }

    public int computeCRC16CCITT(int ai[], int i, int j)
    {
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= i + j, "data array is too small.");
        int k = 65535;
        for(int i1 = i; i1 < i + j; i1++)
        {
            int l = ai[i1] ^ k >> 8;
            k = (CRCUtils.CRC16_CCITT_LOOKUP_TABLE[l] ^ k << 8) & 0xffff;
        }

        return k;
    }

    public int computeCRC16sum(int ai[], int i, int j)
    {
        //Contract.pre(ai != null, "data is null.");
        //Contract.pre(ai.length >= i + j, "data array is too small.");
        int k = 0;
        for(int l = i; l < i + j; l++)
            k = k + ai[l] & 0xffff;

        return k;
    }

    public int computeCRC8XOR(int i, String s, int j, int k)
    {
        //Contract.preNonNull(s, "data");
        //Contract.pre(s.length() >= j + k, "data is too small.");
        int l = i;
        for(int i1 = j; i1 < j + k; i1++)
            l = (l ^ s.charAt(i1)) & 0xff;

        return l;
    }

    public boolean isCRC8E1381Valid(String s)
    {
        boolean flag = false;
        //Contract.pre(s != null, "reply is null.");
        //Contract.pre(s.length() >= 2, "reply is too small.");
        String s1;
        try
        {
            s1 = remainderOf(s, String.valueOf('\003'));
        }
        catch(Exception baddevicevalueexception)
        {
            try
            {
                s1 = remainderOf(s, String.valueOf('\027'));
            }
            catch(Exception baddevicevalueexception1)
            {
                return flag;
            }
        }
        if(s1.length() > 1)
        {
            char c = s1.charAt(0);
            char c1 = s1.charAt(1);
            int i = computeCRC8E1381(makeIntArray(s));
            int j = convertHexToASCII(getHighNibble(i));
            int k = convertHexToASCII(getLowNibble(i));
            if(j == c && k == c1)
                flag = true;
        }
        return flag;
    }
    
    
    public String remainderOf(String s, String s1) throws Exception
    {
        int i = s.indexOf(s1);
        if(i != -1)
        {
            int j = i + s1.length();
            if(s.length() > j)
                return s.substring(j);
            else
                throw new Exception("Nothing following '" + s1 + "' in string '" + s + "'");
        } 
        else
        {
            throw new Exception("Can't find '" + s1 + "' in string '" + s + "'");
        }
    }
    
    public int getLowNibble(int i)
    {
        //Contract.pre(i >= 0 && i <= 255, "oneByte value of " + i + " is out of expected range 0.." + 255);
        return i & 0xf;
    }

    public int getHighNibble(int i)
    {
        //Contract.pre(i >= 0 && i <= 255, "oneByte value of " + i + " is out of expected range 0.." + 255);
        return i >> 4 & 0xf;
    }
    
    
    
    public int convertHexToASCII(int i)
    {
        //Contract.pre(i >= 0 && i <= 15, "hexValue value of " + i + " is out of expected range 0.." + 15);
        return i >= 10 ? (i - 10) + 65 : i + 48;
    }
    
    
    
}
