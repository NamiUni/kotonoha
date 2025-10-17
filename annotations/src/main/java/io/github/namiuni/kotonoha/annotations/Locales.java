/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2025 Namiu (Unitarou)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.namiuni.kotonoha.annotations;

import org.jspecify.annotations.NullMarked;

/**
 * Utility class containing supported locale code strings for resource bundle generation.
 *
 * <p>Each constant represents a locale identifier in the format "language_COUNTRY"
 * or "language" (for locales without a specific country).</p>
 *
 * @since 0.1.0
 */
@NullMarked
@SuppressWarnings("unused")
public final class Locales {

    // Root locale
    public static final String ROOT = "";

    // A-B
    public static final String AF_ZA = "af_ZA"; // Afrikaans
    public static final String AR_SA = "ar_SA"; // Arabic
    public static final String AST_ES = "ast_ES"; // Asturian
    public static final String AZ_AZ = "az_AZ"; // Azerbaijani
    public static final String BA_RU = "ba_RU"; // Bashkir
    public static final String BAR = "bar"; // Bavarian
    public static final String BE_BY = "be_BY"; // Belarusian
    public static final String BG_BG = "bg_BG"; // Bulgarian
    public static final String BR_FR = "br_FR"; // Breton
    public static final String BRB = "brb"; // Barbadian
    public static final String BS_BA = "bs_BA"; // Bosnian

    // C-D
    public static final String CA_ES = "ca_ES"; // Catalan
    public static final String CS_CZ = "cs_CZ"; // Czech
    public static final String CY_GB = "cy_GB"; // Welsh
    public static final String DA_DK = "da_DK"; // Danish
    public static final String DE_AT = "de_AT"; // German (Austria)
    public static final String DE_CH = "de_CH"; // German (Switzerland)
    public static final String DE_DE = "de_DE"; // German (Germany)

    // E-F
    public static final String EL_GR = "el_GR"; // Greek
    public static final String EN_AU = "en_AU"; // English (Australia)
    public static final String EN_CA = "en_CA"; // English (Canada)
    public static final String EN_GB = "en_GB"; // English (United Kingdom)
    public static final String EN_NZ = "en_NZ"; // English (New Zealand)
    public static final String EN_PT = "en_PT"; // English (Pirate)
    public static final String EN_UD = "en_UD"; // English (Upside Down)
    public static final String EN_US = "en_US"; // English (United States)
    public static final String ENP = "enp"; // English (Pirate)
    public static final String EO_UY = "eo_UY"; // Esperanto
    public static final String ES_AR = "es_AR"; // Spanish (Argentina)
    public static final String ES_CL = "es_CL"; // Spanish (Chile)
    public static final String ES_EC = "es_EC"; // Spanish (Ecuador)
    public static final String ES_ES = "es_ES"; // Spanish (Spain)
    public static final String ES_MX = "es_MX"; // Spanish (Mexico)
    public static final String ES_UY = "es_UY"; // Spanish (Uruguay)
    public static final String ES_VE = "es_VE"; // Spanish (Venezuela)
    public static final String ET_EE = "et_EE"; // Estonian
    public static final String EU_ES = "eu_ES"; // Basque
    public static final String FA_IR = "fa_IR"; // Persian
    public static final String FI_FI = "fi_FI"; // Finnish
    public static final String FIL_PH = "fil_PH"; // Filipino
    public static final String FO_FO = "fo_FO"; // Faroese
    public static final String FR_CA = "fr_CA"; // French (Canada)
    public static final String FR_FR = "fr_FR"; // French (France)
    public static final String FRA_DE = "fra_DE"; // Franconian
    public static final String FUR_IT = "fur_IT"; // Friulian
    public static final String FY_NL = "fy_NL"; // Frisian

    // G-I
    public static final String GA_IE = "ga_IE"; // Irish
    public static final String GD_GB = "gd_GB"; // Scottish Gaelic
    public static final String GL_ES = "gl_ES"; // Galician
    public static final String GOT_SE = "got_SE"; // Gothic
    public static final String GV_IM = "gv_IM"; // Manx
    public static final String HAW_US = "haw_US"; // Hawaiian
    public static final String HE_IL = "he_IL"; // Hebrew
    public static final String HI_IN = "hi_IN"; // Hindi
    public static final String HR_HR = "hr_HR"; // Croatian
    public static final String HU_HU = "hu_HU"; // Hungarian
    public static final String HY_AM = "hy_AM"; // Armenian
    public static final String ID_ID = "id_ID"; // Indonesian
    public static final String IG_NG = "ig_NG"; // Igbo
    public static final String IO_EN = "io_EN"; // Ido
    public static final String IS_IS = "is_IS"; // Icelandic
    public static final String ISV = "isv"; // Interslavic
    public static final String IT_IT = "it_IT"; // Italian (Italy)

    // J-L
    public static final String JA_JP = "ja_JP"; // Japanese (Japan)
    public static final String JBO_EN = "jbo_EN"; // Lojban
    public static final String KA_GE = "ka_GE"; // Georgian
    public static final String KK_KZ = "kk_KZ"; // Kazakh
    public static final String KN_IN = "kn_IN"; // Kannada
    public static final String KO_KR = "ko_KR"; // Korean (South Korea)
    public static final String KSH = "ksh"; // Kölsch
    public static final String KW_GB = "kw_GB"; // Cornish
    public static final String LA_LA = "la_LA"; // Latin
    public static final String LB_LU = "lb_LU"; // Luxembourgish
    public static final String LI_LI = "li_LI"; // Limburgish
    public static final String LIJ_IT = "lij_IT"; // Ligurian
    public static final String LMO = "lmo"; // Lombard
    public static final String LOL_US = "lol_US"; // LOLCAT
    public static final String LT_LT = "lt_LT"; // Lithuanian
    public static final String LV_LV = "lv_LV"; // Latvian
    public static final String LZH = "lzh"; // Literary Chinese

    // M-N
    public static final String MK_MK = "mk_MK"; // Macedonian
    public static final String MN_MN = "mn_MN"; // Mongolian
    public static final String MS_MY = "ms_MY"; // Malay
    public static final String MT_MT = "mt_MT"; // Maltese
    public static final String NAP = "nap"; // Neapolitan
    public static final String NB_NO = "nb_NO"; // Norwegian Bokmål
    public static final String NDS_DE = "nds_DE"; // Low German
    public static final String NL_BE = "nl_BE"; // Dutch (Belgium)
    public static final String NL_NL = "nl_NL"; // Dutch (Netherlands)
    public static final String NN_NO = "nn_NO"; // Norwegian Nynorsk
    public static final String NU_NG = "nu_NG"; // N'Ko

    // O-R
    public static final String OC_FR = "oc_FR"; // Occitan
    public static final String OVD = "ovd"; // Elfdalian
    public static final String PL_PL = "pl_PL"; // Polish
    public static final String PMS = "pms"; // Piedmontese
    public static final String PT_BR = "pt_BR"; // Portuguese (Brazil)
    public static final String PT_PT = "pt_PT"; // Portuguese (Portugal)
    public static final String QYA_AA = "qya_AA"; // Quenya
    public static final String RO_RO = "ro_RO"; // Romanian
    public static final String RPR = "rpr"; // Russian (Pre-revolutionary)
    public static final String RU_RU = "ru_RU"; // Russian

    // S-T
    public static final String SE_NO = "se_NO"; // Northern Sami
    public static final String SK_SK = "sk_SK"; // Slovak
    public static final String SL_SI = "sl_SI"; // Slovenian
    public static final String SO_SO = "so_SO"; // Somali
    public static final String SQ_AL = "sq_AL"; // Albanian
    public static final String SR_SP = "sr_SP"; // Serbian
    public static final String SV_SE = "sv_SE"; // Swedish
    public static final String SWG = "swg"; // Swabian
    public static final String SZL = "szl"; // Silesian

    // T-Z
    public static final String TA_IN = "ta_IN"; // Tamil
    public static final String TH_TH = "th_TH"; // Thai
    public static final String TL_PH = "tl_PH"; // Tagalog
    public static final String TLH_AA = "tlh_AA"; // Klingon
    public static final String TR_TR = "tr_TR"; // Turkish
    public static final String TT_RU = "tt_RU"; // Tatar
    public static final String UK_UA = "uk_UA"; // Ukrainian
    public static final String VAL_ES = "val_ES"; // Valencian
    public static final String VEC_IT = "vec_IT"; // Venetian
    public static final String VI_VN = "vi_VN"; // Vietnamese
    public static final String VP_VL = "vp_VL"; // Viossa
    public static final String YI_DE = "yi_DE"; // Yiddish
    public static final String YO_NG = "yo_NG"; // Yoruba
    public static final String ZH_CN = "zh_CN"; // Chinese (Simplified, China)
    public static final String ZH_HK = "zh_HK"; // Chinese (Traditional, Hong Kong)
    public static final String ZH_TW = "zh_TW"; // Chinese (Traditional, Taiwan)
    public static final String ZLM_ARAB = "zlm_ARAB"; // Malay (Jawi)

    private Locales() {
    }
}
