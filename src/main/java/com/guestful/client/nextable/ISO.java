/**
 * Copyright (C) 2013 Guestful (info@guestful.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guestful.client.nextable;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class ISO {

    private static final Map<String, String> ISO_3166_1 = new TreeMap<String, String>() {{
        put("AFG", "AF");
        put("ALA", "AX");
        put("ALB", "AL");
        put("DZA", "DZ");
        put("ASM", "AS");
        put("AND", "AD");
        put("AGO", "AO");
        put("AIA", "AI");
        put("ATA", "AQ");
        put("ATG", "AG");
        put("ARG", "AR");
        put("ARM", "AM");
        put("ABW", "AW");
        put("AUS", "AU");
        put("AUT", "AT");
        put("AZE", "AZ");
        put("BHS", "BS");
        put("BHR", "BH");
        put("BGD", "BD");
        put("BRB", "BB");
        put("BLR", "BY");
        put("BEL", "BE");
        put("BLZ", "BZ");
        put("BEN", "BJ");
        put("BMU", "BM");
        put("BTN", "BT");
        put("BOL", "BO");
        put("BES", "BQ");
        put("BIH", "BA");
        put("BWA", "BW");
        put("BVT", "BV");
        put("BRA", "BR");
        put("IOT", "IO");
        put("BRN", "BN");
        put("BGR", "BG");
        put("BFA", "BF");
        put("BDI", "BI");
        put("KHM", "KH");
        put("CMR", "CM");
        put("CAN", "CA");
        put("CPV", "CV");
        put("CYM", "KY");
        put("CAF", "CF");
        put("TCD", "TD");
        put("CHL", "CL");
        put("CHN", "CN");
        put("CXR", "CX");
        put("CCK", "CC");
        put("COL", "CO");
        put("COM", "KM");
        put("COG", "CG");
        put("COD", "CD");
        put("COK", "CK");
        put("CRI", "CR");
        put("CIV", "CI");
        put("HRV", "HR");
        put("CUB", "CU");
        put("CUW", "CW");
        put("CYP", "CY");
        put("CZE", "CZ");
        put("DNK", "DK");
        put("DJI", "DJ");
        put("DMA", "DM");
        put("DOM", "DO");
        put("ECU", "EC");
        put("EGY", "EG");
        put("SLV", "SV");
        put("GNQ", "GQ");
        put("ERI", "ER");
        put("EST", "EE");
        put("ETH", "ET");
        put("FLK", "FK");
        put("FRO", "FO");
        put("FJI", "FJ");
        put("FIN", "FI");
        put("FRA", "FR");
        put("GUF", "GF");
        put("PYF", "PF");
        put("ATF", "TF");
        put("GAB", "GA");
        put("GMB", "GM");
        put("GEO", "GE");
        put("DEU", "DE");
        put("GHA", "GH");
        put("GIB", "GI");
        put("GRC", "GR");
        put("GRL", "GL");
        put("GRD", "GD");
        put("GLP", "GP");
        put("GUM", "GU");
        put("GTM", "GT");
        put("GGY", "GG");
        put("GIN", "GN");
        put("GNB", "GW");
        put("GUY", "GY");
        put("HTI", "HT");
        put("HMD", "HM");
        put("VAT", "VA");
        put("HND", "HN");
        put("HKG", "HK");
        put("HUN", "HU");
        put("ISL", "IS");
        put("IND", "IN");
        put("IDN", "ID");
        put("IRN", "IR");
        put("IRQ", "IQ");
        put("IRL", "IE");
        put("IMN", "IM");
        put("ISR", "IL");
        put("ITA", "IT");
        put("JAM", "JM");
        put("JPN", "JP");
        put("JEY", "JE");
        put("JOR", "JO");
        put("KAZ", "KZ");
        put("KEN", "KE");
        put("KIR", "KI");
        put("PRK", "KP");
        put("KOR", "KR");
        put("KWT", "KW");
        put("KGZ", "KG");
        put("LAO", "LA");
        put("LVA", "LV");
        put("LBN", "LB");
        put("LSO", "LS");
        put("LBR", "LR");
        put("LBY", "LY");
        put("LIE", "LI");
        put("LTU", "LT");
        put("LUX", "LU");
        put("MAC", "MO");
        put("MKD", "MK");
        put("MDG", "MG");
        put("MWI", "MW");
        put("MYS", "MY");
        put("MDV", "MV");
        put("MLI", "ML");
        put("MLT", "MT");
        put("MHL", "MH");
        put("MTQ", "MQ");
        put("MRT", "MR");
        put("MUS", "MU");
        put("MYT", "YT");
        put("MEX", "MX");
        put("FSM", "FM");
        put("MDA", "MD");
        put("MCO", "MC");
        put("MNG", "MN");
        put("MNE", "ME");
        put("MSR", "MS");
        put("MAR", "MA");
        put("MOZ", "MZ");
        put("MMR", "MM");
        put("NAM", "NA");
        put("NRU", "NR");
        put("NPL", "NP");
        put("NLD", "NL");
        put("NCL", "NC");
        put("NZL", "NZ");
        put("NIC", "NI");
        put("NER", "NE");
        put("NGA", "NG");
        put("NIU", "NU");
        put("NFK", "NF");
        put("MNP", "MP");
        put("NOR", "NO");
        put("OMN", "OM");
        put("PAK", "PK");
        put("PLW", "PW");
        put("PSE", "PS");
        put("PAN", "PA");
        put("PNG", "PG");
        put("PRY", "PY");
        put("PER", "PE");
        put("PHL", "PH");
        put("PCN", "PN");
        put("POL", "PL");
        put("PRT", "PT");
        put("PRI", "PR");
        put("QAT", "QA");
        put("REU", "RE");
        put("ROU", "RO");
        put("RUS", "RU");
        put("RWA", "RW");
        put("BLM", "BL");
        put("SHN", "SH");
        put("KNA", "KN");
        put("LCA", "LC");
        put("MAF", "MF");
        put("SPM", "PM");
        put("VCT", "VC");
        put("WSM", "WS");
        put("SMR", "SM");
        put("STP", "ST");
        put("SAU", "SA");
        put("SEN", "SN");
        put("SRB", "RS");
        put("SYC", "SC");
        put("SLE", "SL");
        put("SGP", "SG");
        put("SXM", "SX");
        put("SVK", "SK");
        put("SVN", "SI");
        put("SLB", "SB");
        put("SOM", "SO");
        put("ZAF", "ZA");
        put("SGS", "GS");
        put("SSD", "SS");
        put("ESP", "ES");
        put("LKA", "LK");
        put("SDN", "SD");
        put("SUR", "SR");
        put("SJM", "SJ");
        put("SWZ", "SZ");
        put("SWE", "SE");
        put("CHE", "CH");
        put("SYR", "SY");
        put("TWN", "TW");
        put("TJK", "TJ");
        put("TZA", "TZ");
        put("THA", "TH");
        put("TLS", "TL");
        put("TGO", "TG");
        put("TKL", "TK");
        put("TON", "TO");
        put("TTO", "TT");
        put("TUN", "TN");
        put("TUR", "TR");
        put("TKM", "TM");
        put("TCA", "TC");
        put("TUV", "TV");
        put("UGA", "UG");
        put("UKR", "UA");
        put("ARE", "AE");
        put("GBR", "GB");
        put("USA", "US");
        put("UMI", "UM");
        put("URY", "UY");
        put("UZB", "UZ");
        put("VUT", "VU");
        put("VEN", "VE");
        put("VNM", "VN");
        put("VGB", "VG");
        put("VIR", "VI");
        put("WLF", "WF");
        put("ESH", "EH");
        put("YEM", "YE");
        put("ZMB", "ZM");
        put("ZWE", "ZW");
    }};

    static String iso_3166_1_alpha_3_toalpha_2(String alpha3) {
        if (alpha3 == null) {
            throw new NullPointerException();
        }
        if (!ISO_3166_1.containsKey(alpha3.toUpperCase())) {
            throw new IllegalArgumentException(alpha3);
        }
        return ISO_3166_1.get(alpha3);
    }

}
