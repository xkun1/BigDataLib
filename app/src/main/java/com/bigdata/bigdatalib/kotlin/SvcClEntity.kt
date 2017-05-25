package com.bigdata.bigdatalib.kotlin

/**
 * user:kun
 * Date:2017/5/19 or 上午11:27
 * email:hekun@gamil.com
 * Desc:
 */

internal class SvcClEntity {
    /**
     * v : null
     * resp : 00
     * msg :
     * params : {"cl_cont":"贝贝财金APP由上海贝襄信息科技有限公司开发。\n贝贝财金APP栏目中的文章仅代表作者本人观点，与公司立场无关，公司不承担相应的法律责任。\n贝贝财金APP所刊登的来源于已公开的资料的各种报告的信息，上海贝襄信息科技有限公司对这些信息的准确性及完整性不作任何保证，不保证该信息未经任何更新，也不保证上海贝襄信息科技有限公司做出的任何建议不会发生任何变更。在任何情况下，上海贝襄信息科技有限公司不就我公司发布的信息中的任何内容对任何投资做出任何形式的担保。\n贝贝财金APP中所展示的股票组合资讯，不具备投资建议。对于股票组合的涨跌盈亏，贝襄信息科技有限公司不负任何对应责任。\n如遇自然灾害、突发事件等不可抗力等因素，致使公司不能提供正常的相关网络及资讯服务，上海贝襄信息科技有限公司不承担任何法律责任。                                                                                                                                                                                                                                                                                                                                            ","cl_sub_tit":"资讯及网络服务免责条款","cl_tit":"贝贝财金APP","cl_lk":"上海贝襄信息科技有限公司"}
     * signature :
     * tokenkey :
     * userid :
     */

    var v: Any? = null
    var resp: String? = null
    var msg: String? = null
    var params: ParamsBean? = null
    var signature: String? = null
    var tokenkey: String? = null
    var userid: String? = null

    class ParamsBean {
        /**
         * cl_cont : 贝贝财金APP由上海贝襄信息科技有限公司开发。
         * 贝贝财金APP栏目中的文章仅代表作者本人观点，与公司立场无关，公司不承担相应的法律责任。
         * 贝贝财金APP所刊登的来源于已公开的资料的各种报告的信息，上海贝襄信息科技有限公司对这些信息的准确性及完整性不作任何保证，不保证该信息未经任何更新，也不保证上海贝襄信息科技有限公司做出的任何建议不会发生任何变更。在任何情况下，上海贝襄信息科技有限公司不就我公司发布的信息中的任何内容对任何投资做出任何形式的担保。
         * 贝贝财金APP中所展示的股票组合资讯，不具备投资建议。对于股票组合的涨跌盈亏，贝襄信息科技有限公司不负任何对应责任。
         * 如遇自然灾害、突发事件等不可抗力等因素，致使公司不能提供正常的相关网络及资讯服务，上海贝襄信息科技有限公司不承担任何法律责任。
         * cl_sub_tit : 资讯及网络服务免责条款
         * cl_tit : 贝贝财金APP
         * cl_lk : 上海贝襄信息科技有限公司
         */

        var cl_cont: String? = null
        var cl_sub_tit: String? = null
        var cl_tit: String? = null
        var cl_lk: String? = null
    }
}
