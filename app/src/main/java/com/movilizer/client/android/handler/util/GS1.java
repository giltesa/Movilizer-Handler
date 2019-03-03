package com.movilizer.client.android.handler.util;


import java.util.HashMap;
import java.util.Map;



/**
 * Created by Alberto Gil Tesa on 12/04/2018.
 * https://www.gs1.org/docs/barcodes/GS1_General_Specifications.pdf
 */
public class GS1
{
    private static String                 fnc1 = "\u001D";
    private static Map< String, Integer > ai   = new HashMap<>();


    /**
     * Length / Value:
     * Variable = 0 and FNC1
     * Fixed + Variable = 0 and FNC1
     * Fixed > 0
     * Fixed + decimals < 0 (The negative sign indicates that it has decimals and it is necessary to take one more character to know in what position the comma goes, the number indicates the fixed length of the number)
     */
    static
    {
        ai.put("00", 18);
        ai.put("01", 14);
        ai.put("02", 14);
        ai.put("10", 0); //20
        ai.put("11", 6);
        ai.put("12", 6);
        ai.put("13", 6);
        ai.put("15", 6);
        ai.put("16", 6);
        ai.put("17", 6);
        ai.put("20", 2);
        ai.put("21", 0); //20
        ai.put("22", 0); //20
        ai.put("240", 0); //30
        ai.put("241", 0); //30
        ai.put("242", 0); //6
        ai.put("243", 0); //20
        ai.put("250", 0); //30
        ai.put("251", 0); //30
        ai.put("253", 0); //N13+X..17
        ai.put("254", 0); //20
        ai.put("255", 0); //N13+N..12
        ai.put("30", 0); //8
        ai.put("310", -6);
        ai.put("311", -6);
        ai.put("312", -6);
        ai.put("313", -6);
        ai.put("314", -6);
        ai.put("315", -6);
        ai.put("316", -6);
        ai.put("320", -6);
        ai.put("321", -6);
        ai.put("322", -6);
        ai.put("323", -6);
        ai.put("324", -6);
        ai.put("325", -6);
        ai.put("326", -6);
        ai.put("327", -6);
        ai.put("328", -6);
        ai.put("329", -6);
        ai.put("330", -6);
        ai.put("331", -6);
        ai.put("332", -6);
        ai.put("333", -6);
        ai.put("334", -6);
        ai.put("335", -6);
        ai.put("336", -6);
        ai.put("337", -6);
        ai.put("340", -6);
        ai.put("341", -6);
        ai.put("342", -6);
        ai.put("343", -6);
        ai.put("344", -6);
        ai.put("345", -6);
        ai.put("346", -6);
        ai.put("347", -6);
        ai.put("348", -6);
        ai.put("349", -6);
        ai.put("350", -6);
        ai.put("351", -6);
        ai.put("352", -6);
        ai.put("353", -6);
        ai.put("354", -6);
        ai.put("355", -6);
        ai.put("356", -6);
        ai.put("357", -6);
        ai.put("360", -6);
        ai.put("361", -6);
        ai.put("362", -6);
        ai.put("363", -6);
        ai.put("364", -6);
        ai.put("365", -6);
        ai.put("366", -6);
        ai.put("367", -6);
        ai.put("368", -6);
        ai.put("369", -6);
        ai.put("37", 0); //8
        ai.put("390", Integer.MIN_VALUE); //15
        ai.put("391", Integer.MIN_VALUE); //N3+N..15
        ai.put("392", Integer.MIN_VALUE); //15
        ai.put("393", Integer.MIN_VALUE); //N3+N..15
        ai.put("394", -4);
        ai.put("400", 0); //30
        ai.put("401", 0); //30
        ai.put("402", 17);
        ai.put("403", 0); //30
        ai.put("410", 13);
        ai.put("411", 13);
        ai.put("412", 13);
        ai.put("413", 13);
        ai.put("414", 13);
        ai.put("415", 13);
        ai.put("416", 13);
        ai.put("420", 0); //20
        ai.put("421", 0); //N3+X..9
        ai.put("422", 3);
        ai.put("423", 0); //N3+N..12
        ai.put("424", 3);
        ai.put("425", 0); //N3+N..12
        ai.put("426", 3);
        ai.put("427", 0); //3
        ai.put("7001", 13);
        ai.put("7002", 0); //30
        ai.put("7003", 10);
        ai.put("7004", 0); //4
        ai.put("7005", 0); //12
        ai.put("7006", 6);
        ai.put("7007", 0); //N6..12
        ai.put("7008", 0); //3
        ai.put("7009", 0); //10
        ai.put("7010", 0); //2
        ai.put("7020", 0); //20
        ai.put("7021", 0); //20
        ai.put("7022", 0); //20
        ai.put("7023", 0); //30
        //ai.put("703s", 0); //N3+X..27 //Not implemented
        ai.put("710", 0); //20
        ai.put("711", 0); //20
        ai.put("712", 0); //20
        ai.put("713", 0); //20
        ai.put("714", 0); //20
        ai.put("8001", 14);
        ai.put("8002", 0); //20
        ai.put("8003", 0); //N14+X..16
        ai.put("8004", 0); //30
        ai.put("8005", 6);
        ai.put("8006", 0); //N14+N2+N2
        ai.put("8007", 0); //34
        ai.put("8008", 0); //N8+N..4
        ai.put("8010", 0); //30
        ai.put("8011", 0); //12
        ai.put("8012", 0); //20
        ai.put("8013", 0); //30
        ai.put("8017", 18);
        ai.put("8018", 18);
        ai.put("8019", 0); //10
        ai.put("8020", 0); //25
        ai.put("8110", 0); //70
        ai.put("8111", 4);
        ai.put("8112", 0); //70
        ai.put("8200", 0); //70
        ai.put("90", 0); //30
        ai.put("91", 0); //90
        ai.put("92", 0); //90
        ai.put("93", 0); //90
        ai.put("94", 0); //90
        ai.put("95", 0); //90
        ai.put("96", 0); //90
        ai.put("97", 0); //90
        ai.put("98", 0); //90
        ai.put("99", 0); //90
    }


    private GS1()
    {
    }


    /**
     * @param rawBarcode
     * @return
     * @throws Exception
     */
    static public Map< String, Object > split( String rawBarcode ) throws Exception
    {
        Map< String, Object > result = new HashMap<>();


        if( rawBarcode != null && rawBarcode.length() > 0 )
        {
            String barcode = rawBarcode + fnc1;

            int length = barcode.length();
            int index  = 0;

            while( index < length )
            {
                String  keyAI;
                Integer lengthAI, decAI;
                Object  value;


                if( barcode.substring(index, index + 1).equals(fnc1) )
                {
                    index++;
                    continue;
                }


                if( ai.containsKey(barcode.substring(index, index + 2)) )
                {
                    keyAI = barcode.substring(index, index + 2);
                    index += 2;
                }
                else if( ai.containsKey(barcode.substring(index, index + 3)) )
                {
                    keyAI = barcode.substring(index, index + 3);
                    index += 3;
                }
                else if( ai.containsKey(barcode.substring(index, index + 4)) )
                {
                    keyAI = barcode.substring(index, index + 4);
                    index += 4;
                }
                else
                {
                    throw new Exception("Error: The barcode has an incorrect format");
                }


                lengthAI = ai.get(keyAI);

                if( lengthAI > 0 ) //Fixed
                {
                    value = barcode.substring(index, index + lengthAI);
                    index += lengthAI;
                }
                else if( lengthAI == 0 ) //Variable
                {
                    value = barcode.substring(index, barcode.indexOf(fnc1, index));
                    index += ((String)value).length() + 1;
                }
                else //With decimals
                {
                    decAI = Integer.parseInt(barcode.substring(index, index + 1));
                    index += 1;

                    if( lengthAI == Integer.MIN_VALUE )
                    {
                        value = barcode.substring(index, barcode.indexOf(fnc1, index)); //Decimal with variable length
                        index += ((String)value).length() + 1;
                    }
                    else
                    {
                        lengthAI *= -1;
                        value = barcode.substring(index, index + lengthAI);
                        index += lengthAI;
                    }

                    value = Double.parseDouble((String)value) / Math.pow(10, decAI);
                }

                result.put(keyAI, value);
            }

        }

        return result;
    }


}















