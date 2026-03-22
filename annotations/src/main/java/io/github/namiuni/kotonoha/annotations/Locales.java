/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2026 Namiu (うにたろう)
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

import java.util.Locale;
import org.jspecify.annotations.NullMarked;

/**
 * Supported locales for resource bundle generation.
 *
 * @since 0.2.0
 */
@NullMarked
@SuppressWarnings("unused")
public enum Locales {

    /** ROOT locale. */
    ROOT("", Locale.ROOT),

    // A-B
    /** Afrikaans. */
    AF_ZA("af_ZA", Locale.of("af", "ZA")),
    /** Arabic. */
    AR_SA("ar_SA", Locale.of("ar", "SA")),
    /** Asturian. */
    AST_ES("ast_ES", Locale.of("ast", "ES")),
    /** Azerbaijani. */
    AZ_AZ("az_AZ", Locale.of("az", "AZ")),
    /** Bashkir. */
    BA_RU("ba_RU", Locale.of("ba", "RU")),
    /** Bavarian. */
    BAR("bar", Locale.of("bar")),
    /** Belarusian. */
    BE_BY("be_BY", Locale.of("be", "BY")),
    /** Bulgarian. */
    BG_BG("bg_BG", Locale.of("bg", "BG")),
    /** Breton. */
    BR_FR("br_FR", Locale.of("br", "FR")),
    /** Barbadian. */
    BRB("brb", Locale.of("brb")),
    /** Bosnian. */
    BS_BA("bs_BA", Locale.of("bs", "BA")),

    // C-D
    /** Catalan. */
    CA_ES("ca_ES", Locale.of("ca", "ES")),
    /** Czech. */
    CS_CZ("cs_CZ", Locale.of("cs", "CZ")),
    /** Welsh. */
    CY_GB("cy_GB", Locale.of("cy", "GB")),
    /** Danish. */
    DA_DK("da_DK", Locale.of("da", "DK")),
    /** German (Austria). */
    DE_AT("de_AT", Locale.of("de", "AT")),
    /** German (Switzerland). */
    DE_CH("de_CH", Locale.of("de", "CH")),
    /** German (Germany). */
    DE_DE("de_DE", Locale.of("de", "DE")),

    // E-F
    /** Greek. */
    EL_GR("el_GR", Locale.of("el", "GR")),
    /** English (Australia). */
    EN_AU("en_AU", Locale.of("en", "AU")),
    /** English (Canada). */
    EN_CA("en_CA", Locale.of("en", "CA")),
    /** English (United Kingdom). */
    EN_GB("en_GB", Locale.of("en", "GB")),
    /** English (New Zealand). */
    EN_NZ("en_NZ", Locale.of("en", "NZ")),
    /** English (Pirate). */
    EN_PT("en_PT", Locale.of("en", "PT")),
    /** English (Upside Down). */
    EN_UD("en_UD", Locale.of("en", "UD")),
    /** English (United States). */
    EN_US("en_US", Locale.of("en", "US")),
    /** English (Pirate). */
    ENP("enp", Locale.of("enp")),
    /** Esperanto. */
    EO_UY("eo_UY", Locale.of("eo", "UY")),
    /** Spanish (Argentina). */
    ES_AR("es_AR", Locale.of("es", "AR")),
    /** Spanish (Chile). */
    ES_CL("es_CL", Locale.of("es", "CL")),
    /** Spanish (Ecuador). */
    ES_EC("es_EC", Locale.of("es", "EC")),
    /** Spanish (Spain). */
    ES_ES("es_ES", Locale.of("es", "ES")),
    /** Spanish (Mexico). */
    ES_MX("es_MX", Locale.of("es", "MX")),
    /** Spanish (Uruguay). */
    ES_UY("es_UY", Locale.of("es", "UY")),
    /** Spanish (Venezuela). */
    ES_VE("es_VE", Locale.of("es", "VE")),
    /** Estonian. */
    ET_EE("et_EE", Locale.of("et", "EE")),
    /** Basque. */
    EU_ES("eu_ES", Locale.of("eu", "ES")),
    /** Persian. */
    FA_IR("fa_IR", Locale.of("fa", "IR")),
    /** Finnish. */
    FI_FI("fi_FI", Locale.of("fi", "FI")),
    /** Filipino. */
    FIL_PH("fil_PH", Locale.of("fil", "PH")),
    /** Faroese. */
    FO_FO("fo_FO", Locale.of("fo", "FO")),
    /** French (Canada). */
    FR_CA("fr_CA", Locale.of("fr", "CA")),
    /** French (France). */
    FR_FR("fr_FR", Locale.of("fr", "FR")),
    /** Franconian. */
    FRA_DE("fra_DE", Locale.of("fra", "DE")),
    /** Friulian. */
    FUR_IT("fur_IT", Locale.of("fur", "IT")),
    /** Frisian. */
    FY_NL("fy_NL", Locale.of("fy", "NL")),

    // G-I
    /** Irish. */
    GA_IE("ga_IE", Locale.of("ga", "IE")),
    /** Scottish Gaelic. */
    GD_GB("gd_GB", Locale.of("gd", "GB")),
    /** Galician. */
    GL_ES("gl_ES", Locale.of("gl", "ES")),
    /** Gothic. */
    GOT_SE("got_SE", Locale.of("got", "SE")),
    /** Manx. */
    GV_IM("gv_IM", Locale.of("gv", "IM")),
    /** Hawaiian. */
    HAW_US("haw_US", Locale.of("haw", "US")),
    /** Hebrew. */
    HE_IL("he_IL", Locale.of("he", "IL")),
    /** Hindi. */
    HI_IN("hi_IN", Locale.of("hi", "IN")),
    /** Croatian. */
    HR_HR("hr_HR", Locale.of("hr", "HR")),
    /** Hungarian. */
    HU_HU("hu_HU", Locale.of("hu", "HU")),
    /** Armenian. */
    HY_AM("hy_AM", Locale.of("hy", "AM")),
    /** Indonesian. */
    ID_ID("id_ID", Locale.of("id", "ID")),
    /** Igbo. */
    IG_NG("ig_NG", Locale.of("ig", "NG")),
    /** Ido. */
    IO_EN("io_EN", Locale.of("io", "EN")),
    /** Icelandic. */
    IS_IS("is_IS", Locale.of("is", "IS")),
    /** Interslavic. */
    ISV("isv", Locale.of("isv")),
    /** Italian (Italy). */
    IT_IT("it_IT", Locale.of("it", "IT")),

    // J-L
    /** Japanese (Japan). */
    JA_JP("ja_JP", Locale.of("ja", "JP")),
    /** Lojban. */
    JBO_EN("jbo_EN", Locale.of("jbo", "EN")),
    /** Georgian. */
    KA_GE("ka_GE", Locale.of("ka", "GE")),
    /** Kazakh. */
    KK_KZ("kk_KZ", Locale.of("kk", "KZ")),
    /** Kannada. */
    KN_IN("kn_IN", Locale.of("kn", "IN")),
    /** Korean (South Korea). */
    KO_KR("ko_KR", Locale.of("ko", "KR")),
    /** Kölsch. */
    KSH("ksh", Locale.of("ksh")),
    /** Cornish. */
    KW_GB("kw_GB", Locale.of("kw", "GB")),
    /** Latin. */
    LA_LA("la_LA", Locale.of("la", "LA")),
    /** Luxembourgish. */
    LB_LU("lb_LU", Locale.of("lb", "LU")),
    /** Limburgish. */
    LI_LI("li_LI", Locale.of("li", "LI")),
    /** Ligurian. */
    LIJ_IT("lij_IT", Locale.of("lij", "IT")),
    /** Lombard. */
    LMO("lmo", Locale.of("lmo")),
    /** LOLCAT. */
    LOL_US("lol_US", Locale.of("lol", "US")),
    /** Lithuanian. */
    LT_LT("lt_LT", Locale.of("lt", "LT")),
    /** Latvian. */
    LV_LV("lv_LV", Locale.of("lv", "LV")),
    /** Literary Chinese. */
    LZH("lzh", Locale.of("lzh")),

    // M-N
    /** Macedonian. */
    MK_MK("mk_MK", Locale.of("mk", "MK")),
    /** Mongolian. */
    MN_MN("mn_MN", Locale.of("mn", "MN")),
    /** Malay. */
    MS_MY("ms_MY", Locale.of("ms", "MY")),
    /** Maltese. */
    MT_MT("mt_MT", Locale.of("mt", "MT")),
    /** Neapolitan. */
    NAP("nap", Locale.of("nap")),
    /** Norwegian Bokmål. */
    NB_NO("nb_NO", Locale.of("nb", "NO")),
    /** Low German. */
    NDS_DE("nds_DE", Locale.of("nds", "DE")),
    /** Dutch (Belgium). */
    NL_BE("nl_BE", Locale.of("nl", "BE")),
    /** Dutch (Netherlands). */
    NL_NL("nl_NL", Locale.of("nl", "NL")),
    /** Norwegian Nynorsk. */
    NN_NO("nn_NO", Locale.of("nn", "NO")),
    /** N'Ko. */
    NU_NG("nu_NG", Locale.of("nu", "NG")),

    // O-R
    /** Occitan. */
    OC_FR("oc_FR", Locale.of("oc", "FR")),
    /** Elfdalian. */
    OVD("ovd", Locale.of("ovd")),
    /** Polish. */
    PL_PL("pl_PL", Locale.of("pl", "PL")),
    /** Piedmontese. */
    PMS("pms", Locale.of("pms")),
    /** Portuguese (Brazil). */
    PT_BR("pt_BR", Locale.of("pt", "BR")),
    /** Portuguese (Portugal). */
    PT_PT("pt_PT", Locale.of("pt", "PT")),
    /** Quenya. */
    QYA_AA("qya_AA", Locale.of("qya", "AA")),
    /** Romanian. */
    RO_RO("ro_RO", Locale.of("ro", "RO")),
    /** Russian (Pre-revolutionary). */
    RPR("rpr", Locale.of("rpr")),
    /** Russian. */
    RU_RU("ru_RU", Locale.of("ru", "RU")),

    // S-T
    /** Northern Sami. */
    SE_NO("se_NO", Locale.of("se", "NO")),
    /** Slovak. */
    SK_SK("sk_SK", Locale.of("sk", "SK")),
    /** Slovenian. */
    SL_SI("sl_SI", Locale.of("sl", "SI")),
    /** Somali. */
    SO_SO("so_SO", Locale.of("so", "SO")),
    /** Albanian. */
    SQ_AL("sq_AL", Locale.of("sq", "AL")),
    /** Serbian. */
    SR_SP("sr_SP", Locale.of("sr", "SP")),
    /** Swedish. */
    SV_SE("sv_SE", Locale.of("sv", "SE")),
    /** Swabian. */
    SWG("swg", Locale.of("swg")),
    /** Silesian. */
    SZL("szl", Locale.of("szl")),

    // T-Z
    /** Tamil. */
    TA_IN("ta_IN", Locale.of("ta", "IN")),
    /** Thai. */
    TH_TH("th_TH", Locale.of("th", "TH")),
    /** Tagalog. */
    TL_PH("tl_PH", Locale.of("tl", "PH")),
    /** Klingon. */
    TLH_AA("tlh_AA", Locale.of("tlh", "AA")),
    /** Turkish. */
    TR_TR("tr_TR", Locale.of("tr", "TR")),
    /** Tatar. */
    TT_RU("tt_RU", Locale.of("tt", "RU")),
    /** Ukrainian. */
    UK_UA("uk_UA", Locale.of("uk", "UA")),
    /** Valencian. */
    VAL_ES("val_ES", Locale.of("val", "ES")),
    /** Venetian. */
    VEC_IT("vec_IT", Locale.of("vec", "IT")),
    /** Vietnamese. */
    VI_VN("vi_VN", Locale.of("vi", "VN")),
    /** Viossa. */
    VP_VL("vp_VL", Locale.of("vp", "VL")),
    /** Yiddish. */
    YI_DE("yi_DE", Locale.of("yi", "DE")),
    /** Yoruba. */
    YO_NG("yo_NG", Locale.of("yo", "NG")),
    /** Chinese (Simplified, China). */
    ZH_CN("zh_CN", Locale.of("zh", "CN")),
    /** Chinese (Traditional, Hong Kong). */
    ZH_HK("zh_HK", Locale.of("zh", "HK")),
    /** Chinese (Traditional, Taiwan). */
    ZH_TW("zh_TW", Locale.of("zh", "TW")),
    /** Malay (Jawi). */
    ZLM_ARAB("zlm_ARAB", Locale.of("zlm", "ARAB"));

    private final String tag;
    private final Locale locale;

    Locales(final String tag, final Locale locale) {
        this.tag = tag;
        this.locale = locale;
    }

    /**
     * Returns the locale tag string (e.g., {@code "en_US"}).
     *
     * @return the locale tag
     * @since 0.1.0
     */
    public String tag() {
        return this.tag;
    }

    /**
     * Returns the {@link Locale} corresponding to this locale constant.
     *
     * @return the locale
     * @since 0.1.0
     */
    public Locale asLocale() {
        return this.locale;
    }
}
